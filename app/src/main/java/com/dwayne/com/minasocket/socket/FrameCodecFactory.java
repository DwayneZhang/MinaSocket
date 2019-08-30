package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class describe
 */

public class FrameCodecFactory implements ProtocolCodecFactory {
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return new FrameEncoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return new FrameDecoder();
    }
}
