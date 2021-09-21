package cn.gyw.glearn.design.creational.entities;

public class MailSender implements Sender {

	@Override
	public void send() {
		System.out.println("Send mail ....");
	}

}
