package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class MessageListener implements IoServiceListener {

    public NioSocketConnector connector;

    public MessageListener(NioSocketConnector connector) {
        this.connector = connector;
    }

    @Override
    public void serviceActivated(IoService arg0) throws Exception {
    }

    @Override
    public void serviceDeactivated(IoService arg0) throws Exception {
    }

    @Override
    public void serviceIdle(IoService arg0, IdleStatus arg1) throws Exception {
    }

    @Override
    public void sessionClosed(IoSession arg0) throws Exception {
    }

    @Override
    public void sessionCreated(IoSession arg0) throws Exception {
    }

    @Override
    public void sessionDestroyed(IoSession arg0) {
        ClientConnectManager.getInstance().rePeatConnect();
    }

}
