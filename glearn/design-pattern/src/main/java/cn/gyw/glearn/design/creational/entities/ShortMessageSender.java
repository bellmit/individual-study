package cn.gyw.glearn.design.creational.entities;

public class ShortMessageSender implements Sender {

	@Override
	public void send() {
		System.out.println("Short message send ....");
	}

}
