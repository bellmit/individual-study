# Mybatis-generator gradle 插件

## 一、依赖
1. TkMybatis
2. Mybatis generator

## 二、mybatis-generator 插件
1. 实现Plugin 接口
2. 插件定义  
resources/META-INF/gradle-plugins/{pluginName}.properties  
ps: 插件名是properties文件的文件名
3. 插件扩展Extension  
目的：用来把需要的参数传回插件  
使用方法：project.extensions.add('myextension',MyExtension)

## 三、使用方式
- 加入环境
```groovy
buildscript {
  repositories {
    mavenLocal()
  }
  dependencies {
    classpath "cn.gyw.platform:mbg-gradle-plugin:1.0.0"
  }
}

apply plugin: 'cn.gyw.platform.plugin.mbg'
```
- 参数配置

|参数|类型|说明|默认值|
|---|---|---|---|
|overwrite | boolean | 是否覆盖已经生成的xml或者代码  | true |
|generatorFile | String | mbg的配置文件位置 | generatorConfig.xml |
|sqlScript | String | 要在生成代码之前运行的 SQL 脚本文件的位置 | null |
|consoleable | boolean | 如果指定该参数，执行过程会输出到控制台 | false |
|skip | boolean | 是否跳过生成代码的mbg的任务 | false |
|contexts | String | 如果指定了该参数，逗号隔开的这些context会被执行 | null |
|tableNames | String | 如果指定了该参数，逗号隔开的这个表会被运行 | null |
|allTable|boolean|是否为当前库所有表做代码生成|false|

|参数|类型|说明|默认值|
|---|---|---|---|
|jdbc | driver | String | jdbc的驱动类.不能为空 | null |
|url | String | jdbc的数据库url.不能为空 | null |
|username | String | jdbc的数据库用户名.不能为空 | root |
|password | String | jdbc的数据库密码.不能为空 | null |
|xml | javaProject | String | 生成java文件所在的目录. | src/main/java |
|resourcesProject | String | 生成xml配置文件mapper所在的目录. | src/main/resources |
|mapperPackage | String | mapper配置,生成的Mapper(dao)所在的包所在的位置.不能为空 | null |
|modelPackage | String | model配置,生成的实体类所在的包所在的位置.不能为空 | null |
|xmlPackage | String | mapper的xml配置,生成的mapper的xml所在的包的位置.不能为空 | null |
|mapperPlugin | Class | 插件信息,xml中插件的类名.不能为空 | tk.mybatis.mapper.generator.MapperPlugin.class|
|mapperMapper | String | mapper配置,生成的Mapper方法的父类.不能为空 |tk.mybatis.mapper.common.Mapper|
|tableName | String | mybatis生成对应的数据库表名.不能为空 | null|
|objectName | String | mybatis生成对应的实体类名.不能为空 | null|        
|mapperSuffix | String | mybatis生成Mapper的后缀 | Mapper|
|ftl||模板|
         



## 参考
1. [mybatis_generator_gradle_plugin](https://github.com/cuisongliu/mybatis_generator_gradle_plugin)