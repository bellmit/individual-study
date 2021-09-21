package cn.gyw.platform.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 加密、解密工具
 */
public final class EncryptUtil {

    private final static Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 获取MD5 密码
     * @param base64Passwd Base64位密码
     * @return
     */
    public static String passwordByMD5(final String base64Passwd) {
        byte[] oriPasswd = Base64.getDecoder().decode(base64Passwd);
        try {
            log.debug("原密码为：{}", new String(oriPasswd, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return DigestUtils.md5Hex(oriPasswd);
    }

    private EncryptUtil() {}
}
