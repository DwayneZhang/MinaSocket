package com.dwayne.com.minasocket.socket;

import android.content.Context;
import android.util.Log;

import com.dwayne.com.minasocket.Constants;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class MessageHandler extends IoHandlerAdapter {

    private final String TAG = "MessageHandler";

    private Context mContext;

    public MessageHandler(Context context) {
        this.mContext = context;

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        Log.d(TAG,
                Constants.stringNowTime() + " : 客户端调用exceptionCaught" + cause.getMessage());
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        Log.i(TAG, "接收到服务器端消息：" + message.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
//        Log.d(TAG, Constants.stringNowTime() + " : 客户端调用messageSent");
        //        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log.d(TAG, Constants.stringNowTime() + " : 客户端调用sessionClosed");

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Log.d(TAG, Constants.stringNowTime() + " : 客户端调用sessionCreated");


    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        Log.d(TAG, Constants.stringNowTime() + " : 客户端调用sessionIdle");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log.d(TAG, Constants.stringNowTime() + " : 客户端调用sessionOpened");
    }
}
