package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.session.IoSession;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class SessionManager {

    private static SessionManager instance;
    private volatile static Object bytes = new Object();
    private IoSession mSession;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if(null == instance) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setSeesion(IoSession session) {
        this.mSession = session;
    }


    public void writeToServer(Object msg) {
        if(mSession != null) {
            mSession.write(msg);
        }
    }

    public void closeSession() {
        if(mSession != null) {
            mSession.closeOnFlush();
        }
    }

    public void removeSession() {
        this.mSession = null;
    }
}
