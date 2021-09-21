package cn.gyw.glearn.spi.impl;

import cn.gyw.glearn.spi.api.SayHello;

public class SayHelloImplB implements SayHello {

	@Override
	public String say() {
		System.out.println("this is SayHelloImplB");
		return "SayHelloImplB";
	}

}
