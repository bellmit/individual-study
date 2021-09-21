package cn.gyw.components.web.base.mgb;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import cn.gyw.components.web.enums.CommonRespEnum;
import cn.gyw.components.web.model.PageData;
import cn.gyw.components.web.utils.PageHelperUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import cn.gyw.components.web.base.AbstractController;

/**
 * 通用Controller
 * 
 * @param <T>
 * @param <DTO>
 */
public abstract class BaseController<T, DTO> extends AbstractController {

	protected static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected String entityClassFullName;
	protected String entityClassSimpleName;
	protected Class<T> entityClass;

	private IBaseService<T> baseService;

	@GetMapping
	public List<T> query(DTO dto) {
		log.debug("query params:{}", dto);
		try {
			T condition = entityClass.newInstance();
			BeanUtils.copyProperties(dto, condition);
			log.debug("query condition bean :{}", condition);
			List<T> data = baseService.query(condition);
			return data;
		} catch (Exception e) {
			log.error("New instance error :", e);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * keyword: 关键字查询，需要模糊查询的字段需要在T 中声明 KEYWORD=字段，暂时只支持一个字段模糊
	 * @param webRequest
	 * @return
	 */
	@GetMapping("/")
	public PageData<T> queryByPage(WebRequest webRequest) {
		Map<String, Object> params = fillVariablesMapWithIncomingRequestParameters(webRequest.getParameterMap());
		log.debug("queryByPage params:{}", params);
		String pageNum = params.get("pageNum").toString();
		String pageSize = params.get("pageSize").toString();
		CommonRespEnum.PARAM_NULL.assertNotNull(pageNum, "page");
		CommonRespEnum.PARAM_NULL.assertNotNull(pageNum, "limit");
		List<T> data = baseService.query(params, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		return PageHelperUtil.resetPage(data);
	}

	/**
	 * 新增
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public int add(@RequestBody DTO dto) throws IllegalAccessException, InstantiationException {
		log.info("add data：{}", dto);
		T bean = entityClass.newInstance();
		BeanUtils.copyProperties(dto, bean);
		return baseService.save(bean);
	}

	/**
	 * 修改
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public int update(@RequestBody DTO dto) throws IllegalAccessException, InstantiationException {
		log.info("update data：{}", dto);
		T bean = entityClass.newInstance();
		BeanUtils.copyProperties(dto, bean);
		return baseService.update(bean);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{id}")
	public int delete(@PathVariable Object id) throws IllegalAccessException, InstantiationException {
		log.info("delete by id：{}", id);
		return baseService.remove(id);
	}

	/**
	 * 初始化方法
	 */
	@PostConstruct
	@SuppressWarnings("unchecked")
	public void init() throws IllegalAccessException {
		Type genericInterfaces = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genericInterfaces).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
		entityClassFullName = entityClass.getName();
		entityClassSimpleName = entityClass.getSimpleName();
		StringBuilder serviceBuilder = new StringBuilder();
		serviceBuilder.append(entityClassSimpleName.substring(0, 1).toLowerCase())
				.append(entityClassSimpleName.substring(1)).append("Service");
		log.info("base service name:{}", serviceBuilder.toString());
		// forceAccess: 访问非public 属性
		baseService = (IBaseService<T>) FieldUtils.readField(this, serviceBuilder.toString(), true);
	}

}
