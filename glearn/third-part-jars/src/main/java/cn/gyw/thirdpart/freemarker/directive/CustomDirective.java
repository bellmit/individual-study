package cn.gyw.thirdpart.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义标签解析类
 * 需当参数放入模板map中
 * Created by guanyw on 2018/9/29.
 */
public class CustomDirective implements TemplateDirectiveModel {

	private static final String PARAM_NAME = "name";
    private static final String PARAM_AGE = "age";

	@Override
	public void execute(Environment environment,
						Map map,
						TemplateModel[] templateModels,
						TemplateDirectiveBody templateDirectiveBody)
			throws TemplateException, IOException {
		if (templateDirectiveBody == null) {
			throw new TemplateModelException("null body");
		} else {

		}

	}
}
