package cn.gyw.glearn.design.creational.factory.nomal;

import cn.gyw.glearn.design.creational.entities.MailSender;
import cn.gyw.glearn.design.creational.entities.Sender;
import cn.gyw.glearn.design.creational.entities.ShortMessageSender;

/**
 * 静态工厂模式
 * 违反了开闭原则，每次修改都要修改源代码
 * @author guanyw
 *
 */
public class StaticFactory {

	public static Sender produceMailSender() {
		return new MailSender();
	}

	public static Sender produceShortMessageSender() {
		return new ShortMessageSender();
	}
}
