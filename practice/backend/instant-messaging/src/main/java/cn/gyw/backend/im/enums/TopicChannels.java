package cn.gyw.backend.im.enums;

/**
 * redis 订阅主题
 */
public enum TopicChannels {
    IM_MESSAGE_CHANNEL("incomming:messages"),
    TEST("test"),
    ;

    private String channelName;

    TopicChannels(String name) {
        this.channelName = name;
    }

    public String getChannelName() {
        return channelName;
    }
}
