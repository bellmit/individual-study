package cn.gyw.backend.web.utils;

import cn.gyw.components.web.utils.JwtTokenUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JwtTokenUtilTest {

	@Test
	public void testGetToken() {
		System.out.println(JwtTokenUtil.getToken("1001"));
		System.out.println(JwtTokenUtil.getToken("1002"));
	}

	@Test
	public void testGetUserId() {
		assertEquals("1001", JwtTokenUtil.getUserId(
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAxIn0.mfd9Q4HRQMRr36aGHlIOn4wV6934_rU24gjRMRrSnTM"));
		assertEquals("1002", JwtTokenUtil.getUserId(
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDAyIn0.vjp1rJb6jwxoyg1dqArEQfUSyo9nbtttPYsxJfGYf4c"));
	}

}
