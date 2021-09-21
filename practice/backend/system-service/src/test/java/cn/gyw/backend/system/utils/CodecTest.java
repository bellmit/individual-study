package cn.gyw.backend.system.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class CodecTest {

    @Test
    public void byct() {
        // $2a$10$zhIL0dElgj24Ebqhpy.JpercLl9vKSNVqQapTM8kMZ5dpLCa1kNZ.
        String password = "123123";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));

        System.out.println(passwordEncoder.matches(password, "$2a$10$zhIL0dElgj24Ebqhpy.JpercLl9vKSNVqQapTM8kMZ5dpLCa1kNZ."));
    }

    @Test
    public void base64Test() throws UnsupportedEncodingException {
        String base64Str = "MTIzNDU2";
        byte[] oriPasswd = Base64.getDecoder().decode(base64Str);
        System.out.println(new String(oriPasswd, "UTF-8"));
        System.out.println(DigestUtils.md5DigestAsHex(oriPasswd));
    }

    @Test
    public void md5Test() {
//        e10adc3949ba59abbe56e057f20f883e
        for (int i = 0; i < 10; i++) {
            System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
        }
    }
}
