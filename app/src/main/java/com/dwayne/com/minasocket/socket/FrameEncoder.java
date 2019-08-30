package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

import java.nio.charset.Charset;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class 解码类
 */

public class FrameEncoder implements ProtocolEncoder {
    private final static Charset charset = Charset.forName("UTF-8");

    @Override
    public void encode(IoSession ioSession, Object message,
                       ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);
        buff.putString(message.toString(), charset.newEncoder());
        // put 当前系统默认换行符 WINDOWS：\r\n, Linux:\n
        buff.putString(LineDelimiter.WINDOWS.getValue(), charset.newEncoder());
        // 为下一次读取数据做准备
        buff.flip();
        protocolEncoderOutput.write(buff);
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
