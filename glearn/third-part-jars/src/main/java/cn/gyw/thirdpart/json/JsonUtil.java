package cn.gyw.thirdpart.json;

import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 使用 org.gson 包处理bean和json转换
 * Gson在功能上面无可挑剔，但是性能上面比FastJson有所差距。
 * FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。
 * FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。
 * 在项目选型的时候可以使用Google的Gson和阿里巴巴的FastJson两种并行使用。
 * 如果只是功能要求，没有性能要求，可以使用google的Gson，
 * 如果有性能上面的要求可以使用Gson将bean转换json确保数据的正确，使用FastJson将Json转换Bean
 * @author guanyw
 *
 */
public class JsonUtil {
	private static Gson commonGson = new Gson();

	/**
	 * json字符串转为JavaBean
	 * @param jsonStr
	 * @param clz
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonStr , Class<T> clz) {
		T pojo = commonGson.fromJson(jsonStr, clz);
		return pojo;
	}

	/**
	 * 集合对象转换为json字符串
	 * @param collection
	 * @return
	 */
	public static <T extends Collection<?>> String collectionToJson(T collection) {
		String jsonStr = commonGson.toJson(collection);
		return jsonStr;
	}

	/**
	 * 普通bean对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static <T> String pojoToJson(T obj){
		String jsonStr = commonGson.toJson(obj);
		return jsonStr;
	}
}
