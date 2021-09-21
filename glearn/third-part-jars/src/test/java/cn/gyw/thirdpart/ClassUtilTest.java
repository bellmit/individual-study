package cn.gyw.thirdpart;

import org.junit.Test;

import cn.gyw.thirdpart.model.Person;
import cn.gyw.thirdpart.utils.ClassUtil;

public class ClassUtilTest {

    @Test
    public void shouldCreatePersonByClassName() throws Exception {
        String className = "com.gtools.model.pojo.Person";
        Class<?> cls = ClassUtil.loadClass(className);
        Person person = (Person) cls.newInstance();
        person.sayHello();
    }
}
