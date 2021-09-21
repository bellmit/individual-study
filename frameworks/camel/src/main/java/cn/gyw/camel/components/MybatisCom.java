package cn.gyw.camel.components;

import java.io.IOException;
import java.io.Reader;

import org.apache.camel.component.mybatis.MyBatisComponent;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisCom {

	public static MyBatisComponent build() throws IOException {
		
		String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
		MyBatisComponent mybatis = new MyBatisComponent();
		mybatis.setSqlSessionFactory(sqlSessionFactory);
		
		return mybatis;
	}
	
	
}
