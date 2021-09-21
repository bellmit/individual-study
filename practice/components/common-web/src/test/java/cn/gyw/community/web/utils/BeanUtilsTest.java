package cn.gyw.backend.web.utils;

import java.util.HashMap;
import java.util.Map;

import cn.gyw.components.web.utils.BeanMapUtil;
import org.junit.Test;

public class BeanUtilsTest {

	@Test
	public void map2Bean() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("productAttributeCategoryId", "0");
		map.put("type", "1");

		OneBean bean = BeanMapUtil.mapToBean(map, OneBean.class);

		System.out.println(bean);
	}

	public static class OneBean {

		private Long productAttributeCategoryId;
		private Integer type;

		public Long getProductAttributeCategoryId() {
			return productAttributeCategoryId;
		}

		public void setProductAttributeCategoryId(Long productAttributeCategoryId) {
			this.productAttributeCategoryId = productAttributeCategoryId;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "OneBean [productAttributeCategoryId=" + productAttributeCategoryId + ", type=" + type + "]";
		}

	}
}
