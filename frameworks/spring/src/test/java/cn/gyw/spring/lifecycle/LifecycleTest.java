package cn.gyw.spring.lifecycle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LifecycleConfig.class})
public class LifecycleTest {

	@Autowired
	private Product product;

	/**
	 * 查看Spring 组件生命周期
	 */
	@Test
	public void showStartOrder() {
		System.out.println("showStartOrder");
	}
	
	@Test
	public void test() {
		System.out.println(product);
		System.out.println(product.getName());
	}
}
