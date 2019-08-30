package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
 * @author HI
 * @email dev1024@foxmail.com
 * @time 2019/5/5 9:54
 * @change
 * @chang time
 * @class 心跳超时处理Handler
 */

public class HeartBeatTimeoutHandler implements KeepAliveRequestTimeoutHandler {

    @Override
    public void keepAliveRequestTimedOut(KeepAliveFilter keepAliveFilter,
                                         IoSession ioSession) throws Exception {

    }
}
