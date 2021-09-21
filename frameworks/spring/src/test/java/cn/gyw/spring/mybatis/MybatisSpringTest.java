package cn.gyw.spring.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gyw.spring.mybatis.dao.PhoneDao;
import cn.gyw.spring.mybatis.mapper.PhoneMapper;
import cn.gyw.spring.mybatis.model.Phone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MybatisConfig.class)
public class MybatisSpringTest {

	@Autowired
	private SqlSessionFactoryBean sqlSessionFactoryBean;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private PhoneDao phoneDao;

	@Autowired(required = false)
	private PhoneMapper phoneMapper;

	/**
	 * 从IOC 容器中获取 mapper
	 */
	@Test
	public void useMapperFromIOC() {
		List<Phone> list = phoneMapper.queryAll();
		System.out.println(list);
	}

	@Test
	public void useGetMapper() {
		try {
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

			SqlSession sqlSession = sqlSessionFactory.openSession();
			PhoneMapper phoneMapper = sqlSession.getMapper(PhoneMapper.class);
			List<Phone> list = phoneMapper.queryAll();
			System.out.println(list);

			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void useSqlSessionTemplate() {
		List<Phone> list = 
				sqlSessionTemplate.selectList("cn.gyw.spring.mybatis.mapper.PhoneMapper.queryAll");
		System.out.println(list);
	}
	
	@Test
	public void useSqlSessionDaoSupportImpl() {
		phoneDao.getSqlSessionTemplate();
	}

	@Test
	public void useStatementId() {
		try {
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

			SqlSession sqlSession = sqlSessionFactory.openSession();
			List<Phone> list = sqlSession.selectList("cn.gyw.spring.mybatis.mapper.PhoneMapper.queryAll");
			System.out.println(list);

			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
