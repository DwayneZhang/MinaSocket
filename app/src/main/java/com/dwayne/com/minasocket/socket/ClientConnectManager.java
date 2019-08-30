package com.dwayne.com.minasocket.socket;

import android.content.Context;

import com.dwayne.com.minasocket.Constants;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class ClientConnectManager {

    private static ClientConnectManager instance;
    private Context context;

    private ClientConnectManager() {

    }

    public static ClientConnectManager getInstance() {
        if(null == instance) {
            instance = new ClientConnectManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public void connect(final Context context) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                NioSocketConnector mSocketConnector = new NioSocketConnector();

//                mSocketConnector.setConnectTimeoutMillis(Constants.TIMEOUT);

                //设置协议封装解析处理
                mSocketConnector.getFilterChain().addLast("protocol",
                        new ProtocolCodecFilter(new FrameCodecFactory()));
                // 设置日志输出工厂
                mSocketConnector.getFilterChain().addLast("logger", new LoggingFilter());

                //设置心跳包
                /*KeepAliveFilter heartFilter = new KeepAliveFilter(new
                HeartBeatMessageFactory());
                //每 1 分钟发送一个心跳包
                heartFilter.setRequestInterval(1 * 60);
                //心跳包超时时间 10s
                heartFilter.setRequestTimeout(10);
//                heartFilter.setRequestTimeoutHandler(new HeartBeatTimeoutHandler());
                mSocketConnector.getFilterChain().addLast("heartbeat", heartFilter);*/

                //设置 handler 处理业务逻辑
                mSocketConnector.setHandler(new MessageHandler(context));
                mSocketConnector.addListener(new MessageListener(mSocketConnector));

                // 设置接收和发送缓冲区大小
                mSocketConnector.getSessionConfig().setReceiveBufferSize(1024);
//                mSocketConnector.getSessionConfig().setSendBufferSize(1024);
                // 设置读取空闲时间：单位为s
                mSocketConnector.getSessionConfig().setReaderIdleTime(60);


                //配置服务器地址
                InetSocketAddress mSocketAddress = new InetSocketAddress(Constants.HOST
                        , Constants.PORT);
                //发起连接
                ConnectFuture mFuture = mSocketConnector.connect(mSocketAddress);
                mFuture.awaitUninterruptibly();
                IoSession mSession = mFuture.getSession();
                e.onNext(mSession);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                IoSession mSession = (IoSession) o;
                SessionManager.getInstance().setSeesion(mSession);
                SessionManager.getInstance().writeToServer("This Message is form Client");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void rePeatConnect() {
        final boolean[] isRepeat = {false};
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                // 执行到这里表示Session会话关闭了，需要进行重连操作
                int count = 0;// 记录尝试重连的次数
                NioSocketConnector mSocketConnector = null;
                while(!isRepeat[0] && count < 10) {
                    try {
                        count++;
                        if(mSocketConnector == null) {
                            mSocketConnector = new NioSocketConnector();
                        }

//                        mSocketConnector.setConnectTimeoutMillis(Constants.TIMEOUT);

                        if(!mSocketConnector.getFilterChain().contains("protocol")) {
                            //设置协议封装解析处理
                            mSocketConnector.getFilterChain().addLast("protocol",
                                    new ProtocolCodecFilter(new FrameCodecFactory()));
                        }

                        if(!mSocketConnector.getFilterChain().contains("logger")) {
                            // 设置日志输出工厂
                            mSocketConnector.getFilterChain().addLast("logger",
                                    new LoggingFilter());
                        }

                        /*if (!mSocketConnector.getFilterChain().contains("heartbeat")) {
                            //设置心跳包
                            KeepAliveFilter heartFilter = new KeepAliveFilter(new
                            HeartBeatMessageFactory());
                            //每 1 分钟发送一个心跳包
                            heartFilter.setRequestInterval(1 * 60);
                            //心跳包超时时间 10s
                            heartFilter.setRequestTimeout(10);
//                            heartFilter.setRequestTimeoutHandler(new
HeartBeatTimeoutHandler());
                            mSocketConnector.getFilterChain().addLast("heartbeat",
                            heartFilter);
                        }*/

                        //设置 handler 处理业务逻辑
                        mSocketConnector.setHandler(new MessageHandler(context));
                        mSocketConnector.addListener(new MessageListener(mSocketConnector));

                        // 设置接收和发送缓冲区大小
                        mSocketConnector.getSessionConfig().setReceiveBufferSize(1024);
//                        mSocketConnector.getSessionConfig().setSendBufferSize(1024);
                        // 设置读取空闲时间：单位为s
                        mSocketConnector.getSessionConfig().setReaderIdleTime(60);

                        //配置服务器地址
                        InetSocketAddress mSocketAddress =
                                new InetSocketAddress(Constants.HOST, Constants.PORT);
                        //发起连接
                        ConnectFuture mFuture = mSocketConnector.connect(mSocketAddress);
                        mFuture.awaitUninterruptibly();
                        IoSession mSession = mFuture.getSession();
                        if(mSession.isConnected()) {
                            isRepeat[0] = true;
                            e.onNext(mSession);
                            e.onComplete();
                            break;
                        }
                    } catch(Exception e1) {
                        e1.printStackTrace();
                        if(count == Constants.REPEAT_TIME) {
                            System.out.println(Constants.stringNowTime() + " : 断线重连"
                                    + Constants.REPEAT_TIME + "次之后仍然未成功,结束重连.....");
                            break;
                        } else {
                            System.out.println(Constants.stringNowTime() + " : " +
                                    "本次断线重连失败,5s后进行第" + (count + 1) + "次重连.....");
                            try {
                                Thread.sleep(5000);
                                System.out.println(Constants.stringNowTime() + " : 开始第" + (count + 1) + "次重连.....");
                            } catch(InterruptedException e12) {
                            }
                        }
                    }

                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                IoSession mSession = (IoSession) o;
                SessionManager.getInstance().setSeesion(mSession);
                SessionManager.getInstance().writeToServer("This Message is form Client");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
