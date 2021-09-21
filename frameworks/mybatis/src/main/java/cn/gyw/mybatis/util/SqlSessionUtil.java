package cn.gyw.mybatis.util;

import cn.gyw.mybatis.MybatisConfig;
import cn.gyw.mybatis.mapper.PhoneMapper;
import org.apache.ibatis.session.SqlSession;

import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class SqlSessionUtil {

    public static SqlSession openSqlSession() {
        //从刚刚创建的 sqlSessionFactory 中获取 session
        SqlSession sqlSession = MybatisConfig.getSqlSessionFactory().openSession();
        //创建一个MapperHelper
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        //主键自增回写方法,默认值MYSQL,详细说明请看文档
        config.setIDENTITY("MYSQL");
        //支持getter和setter方法上的注解
        config.setEnableMethodAnnotation(true);
        //设置 insert 和 update 中，是否判断字符串类型!=''
        config.setNotEmpty(true);
        //校验Example中的类型和最终调用时Mapper的泛型是否一致
        config.setCheckExampleEntityClass(true);
        //启用简单类型
        config.setUseSimpleType(true);
        //枚举按简单类型处理
        config.setEnumAsSimpleType(true);
        //自动处理关键字 - mysql
        config.setWrapKeyword("`{0}`");
        //键名类型
        config.setStyle(Style.normal);
        //设置配置
        mapperHelper.setConfig(config);
        //注册通用接口，和其他集成方式中的 mappers 参数作用相同
        //4.0 之后的版本，如果类似 Mapper.class 这样的基础接口带有 @RegisterMapper 注解，就不必在这里注册
//        mapperHelper.registerMapper(PhoneMapper.class);
        //配置 mapperHelper 后，执行下面的操作
        mapperHelper.processConfiguration(sqlSession.getConfiguration());
        return sqlSession;
    }

    public static void closeSqlSession(SqlSession sqlSession){
        sqlSession.commit();
        sqlSession.close();
        sqlSession=null;
    }
}
