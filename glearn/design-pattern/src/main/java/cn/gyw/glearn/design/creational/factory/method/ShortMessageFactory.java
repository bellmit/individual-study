package cn.gyw.glearn.design.creational.factory.method;

import cn.gyw.glearn.design.creational.entities.Sender;
import cn.gyw.glearn.design.creational.entities.ShortMessageSender;

public class ShortMessageFactory implements Provider {

	@Override
	public Sender produce() {
		return new ShortMessageSender();
	}
}
