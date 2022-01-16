package cn.gyw.backend.components.common.i18n;

import org.springframework.stereotype.Component;

/**
 * 统一信息处理
 *
 * 支持国际化
 */
@Component
public class I18nMessageSource {

	/**
	 * 对异常信息国际化处理
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, Object[] args) {
		StringBuilder builder = new StringBuilder(code);
		for (Object arg : args) {
			builder.append(",").append(arg);
		}
		return builder.toString();
	}
}
