package cn.gyw.backend.im.ext;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import cn.gyw.backend.im.model.message.ImMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * ImMessage 对象解码
 */
public class MessageDecoder implements Decoder.Text<ImMessage> {

    private final static Logger log = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    public ImMessage decode(String s) throws DecodeException {
        return JSONObject.parseObject(s, ImMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        try {
            JSONObject.parse(s);
            return true;
        } catch (Exception e) {
            log.info("Decode ImMessage error!");
            return false;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.info("MessageDecoder - init method called");
    }

    @Override
    public void destroy() {
        log.info("MessageDecoder - destroy method called");
    }
}
