package com.dwayne.com.minasocket.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:35
 * @change
 * @chang time
 * @class 编码类
 */

public class FrameDecoder extends CumulativeProtocolDecoder {


    private final static Charset charset = Charset.forName("UTF-8");

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer,
                               ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

        //数据粘包，断包处理
        int startPosition = ioBuffer.position();
        while(ioBuffer.hasRemaining()) {
            byte b = ioBuffer.get();
            if(b == '\n') {//读取到\n时候认为一行已经读取完毕
                int currentPosition = ioBuffer.position();
                int limit = ioBuffer.limit();
                ioBuffer.position(startPosition);
                ioBuffer.limit(limit);
                IoBuffer buffer = ioBuffer.slice();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                String message = new String(bytes, charset);
                protocolDecoderOutput.write(message);
                ioBuffer.position(currentPosition);
                ioBuffer.limit(limit);
                return true;
            }
        }
        ioBuffer.position(startPosition);
        return false;

    }
}
