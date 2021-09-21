package cn.gyw.glearn.design.creational.factory.method;

import cn.gyw.glearn.design.creational.entities.MailSender;
import cn.gyw.glearn.design.creational.entities.Sender;

public class MailFactory implements Provider {

    @Override
    public Sender produce() {
        return new MailSender();
    }

}
