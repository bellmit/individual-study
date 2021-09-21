package cn.gyw.backend.im.subscriber;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import cn.gyw.backend.im.service.IMWebSocketService;

/**
 * Redis topic test sub
 */
@Component
public class TestTopicSubscriber extends AbstractSubscriber {

    private static final ConcurrentHashMap<String, IMWebSocketService> webSocketMap =
            new ConcurrentHashMap<>();

    @Override
    public void onMessage(Object message) {
        System.out.println(">> test topic :" + message);
    }

    public static ConcurrentHashMap<String, IMWebSocketService> getWebSocketMap() {
        return webSocketMap;
    }


}

