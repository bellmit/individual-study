package cn.gyw.springboot.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

@Service
public class I18nService implements MessageSourceAware {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
    }

    public void showMessage() {
        String app = messageSource.getMessage("0000", null, Locale.ENGLISH); // en_exception
        System.out.println(">> Locale.ENGLISH 0000:" + app);
        app = messageSource.getMessage("0001", null, Locale.ENGLISH); // base_exception
        System.out.println(">> Locale.ENGLISH 0001:" + app);
        app = messageSource.getMessage("0000", null, Locale.CHINESE); // zh_exception
        System.out.println(">> Locale.CHINESE 0000:" + app);
        app = messageSource.getMessage("0000", null, Locale.JAPAN); // zh_exception
        System.out.println(">> Locale.JAPAN 0000:" + app);
    }
}
