package cn.gyw.glearn.spi.main;

import java.util.ServiceLoader;

import cn.gyw.glearn.spi.api.SayHello;

/**
 * SPI test
 */
public class SpiMainApplication {
	
	public static void main(String[] args) {
		ServiceLoader<SayHello> shouts = ServiceLoader.load(SayHello.class);
        for (SayHello say : shouts) {
            say.say();
        }
	}
}
