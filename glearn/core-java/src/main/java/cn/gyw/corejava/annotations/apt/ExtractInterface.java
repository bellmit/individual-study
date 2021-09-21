package cn.gyw.corejava.annotations.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)  //从一个使用了该注解的类中抽取出接口之后，没有必要再保留这些注解信息。
public @interface ExtractInterface {
	public String value();
}
