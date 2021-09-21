package cn.gyw.glearn.design.creational.factory.nomal;

import cn.gyw.glearn.design.creational.entities.MailSender;
import cn.gyw.glearn.design.creational.entities.Sender;
import cn.gyw.glearn.design.creational.entities.ShortMessageSender;

/**
 * 普通工厂方法模式
 * 违反了开闭原则
 * @author guanyw
 *
 */
public class NomalFactory {

	public Sender produceSender(String type) {
		Sender sender = null;
		switch(type) {
		case "mail":
			sender = new MailSender();
			break;
		case "shortmessage":
			sender = new ShortMessageSender();
			break;
		default:
			sender = null;
		}
		return sender;
	}

}
