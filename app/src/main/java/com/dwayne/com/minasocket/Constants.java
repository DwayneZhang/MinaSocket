package com.dwayne.com.minasocket;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class Constants {

    public static final int REPEAT_TIME = 9;//重连次数
    public static final String HOST = "192.168.100.7";//IP地址
    public static final int PORT = 21;//端口
    public static final int IDLE_TIME = 10;//客户端10s内没有向服务端发送数据
    public static final int TIMEOUT = 5;//设置连接超时时间,超过5s还没连接上便抛出异常

    /**
     * 心跳包 ping message
     */
    public static final String PING_MESSAGE = "Heart";
    /**
     * 心跳包 pong message
     */
    public static final String PONG_MESSAGE = "Heart\r\n";

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

}
