package cn.gyw.backend.im.constants;

/**
 * 通道keys
 */
public interface ImKeys {

    // 用户token
    String USER_TOKEN = "user:token:";
    // 用户token列表
    String USER_TOKEN_SET = "token:set";

    // 已有联系人列表
    String IM_CONNECT_USER = "connected:";
    // session 历史消息
    String SESSION_MESSAGE_HIS = "his:message:";
    // session 离线消息
    String SESSION_OFFLINE_MESSAGE = "offline:message:";
    // 用户信息
    String USER_INFO = "user:info:";
    // 用户离线消息数
    String USER_OFFLINE_MSG_CNT = "user:offline:message:count:";
    // 在线用户列表
    String USER_ONLINE_LIST = "user:online:list";
}
