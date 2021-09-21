package cn.gyw.springboot.redis;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import cn.gyw.springboot.redis.model.IPerson;
import cn.gyw.springboot.redis.model.PersonAdapter;
import cn.gyw.springboot.redis.model.PersonFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisApplicationTest {

	private static final String PREFIX = "TEST:";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void showRedisTemplate() {
		System.out.println(redisTemplate);
	}
	
	@Test
	public void setValue() {
		String key = PREFIX + "666";
		redisTemplate.opsForValue().set(key, "1234");
	}
	
	@Test
	public void setAndGetObject() {
		IPerson person = PersonFactory.createWorker("test1", 29);
		PersonAdapter personAdapter = new PersonAdapter(person);
		System.out.println("1>>" + personAdapter);
		System.out.println("1>>" + personAdapter.getName() + ":" + personAdapter.getAge());
		
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		String key = PREFIX + person.getName();
		valueOperations.set(key, personAdapter);
		
		PersonAdapter personAdapter2 = (PersonAdapter) valueOperations.get(key);
		System.out.println("2>>" + personAdapter2);
		System.out.println("2>>" + personAdapter2.getName() + ":" + personAdapter2.getAge());
	}
	
	@Test
	public void printAllKeys() {
		Set<String> keys = redisTemplate.keys(PREFIX + "*");
		keys.forEach(System.out::println);
	}
}
