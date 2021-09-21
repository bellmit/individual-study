package cn.gyw.backend.im.ext;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import cn.gyw.backend.im.model.message.ImMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class MessageEncoder implements Encoder.Text<ImMessage> {

    private final static Logger log = LoggerFactory.getLogger(MessageEncoder.class);

    @Override
    public String encode(ImMessage object) throws EncodeException {
        return JSONObject.toJSONString(object);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.info("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        log.info("MessageEncoder - init method called");
    }
}
