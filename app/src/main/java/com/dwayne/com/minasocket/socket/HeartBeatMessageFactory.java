package com.dwayne.com.minasocket.socket;

import com.dwayne.com.minasocket.Constants;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class HeartBeatMessageFactory implements KeepAliveMessageFactory {

    @Override
    public boolean isRequest(IoSession ioSession, Object message) {
        //如果是客户端主动向服务器发起的心跳包, return true, 该框架会发送 getRequest() 方法返回的心跳包内容.

        if(message instanceof String && message.equals(Constants.PING_MESSAGE)) {
            return true;
        }
        return false;

    }

    @Override
    public boolean isResponse(IoSession ioSession, Object message) {
        //如果是服务器发送过来的心跳包, return true后会在 getResponse() 方法中处理心跳包.

        if(message instanceof String && message.equals(Constants.PONG_MESSAGE)) {
            return true;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession ioSession) {
        //自定义向服务器发送的心跳包内容.
        return Constants.PING_MESSAGE;
    }

    @Override
    public Object getResponse(IoSession ioSession, Object message) {
        //自定义解析服务器发送过来的心跳包.
        return Constants.PONG_MESSAGE;
    }
}
