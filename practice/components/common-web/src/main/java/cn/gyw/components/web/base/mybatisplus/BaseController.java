package cn.gyw.components.web.base.mybatisplus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import cn.gyw.components.web.model.DataResponse;
import cn.gyw.components.web.model.PageData;
import cn.gyw.components.web.utils.MBPUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.gyw.components.web.base.AbstractController;

/**
 * 抽象父类控制器
 */
public abstract class BaseController<T, V> extends AbstractController {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    protected String entityClassFullName;
    protected String entityClassSimpleName;
    protected Class<T> entityClass;

    private IBaseService<T> baseService;

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
                .append(entityClassSimpleName.substring(1))
                .append("Service");
        log.info("base service name:{}", serviceBuilder.toString());
        // forceAccess: 访问非public 属性
        baseService = (IBaseService<T>) FieldUtils.readField(this,
                serviceBuilder.toString(), true);
    }

    /**
     * 全部查询
     *
     * @return
     */
    @GetMapping
    public List<T> queryAll() {
        log.info("查询全部");
        Wrapper<T> wrapper = new QueryWrapper<>();
        List<T> datas = baseService.list(wrapper);
        log.info("result :{}", datas);
        return datas;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/")
    public DataResponse<PageData<T>> pageQuery(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "limit", defaultValue = "20") int limit) {
        log.debug("page :{}, limit:{}", page, limit);
        IPage<T> params = new Page<>(page, limit);
        QueryWrapper<T> qw = new QueryWrapper<>();
        Map<String, String> orderCols = setOrderColumns();
        if (!orderCols.isEmpty()) {
            orderCols.forEach((rule, columns) -> {
                if (rule.equalsIgnoreCase("asc")) {
                    qw.orderByAsc(columns.split(","));
                }
                if (rule.equalsIgnoreCase("desc")) {
                    qw.orderByDesc(columns.split(","));
                }
            });
        }
        IPage<T> pageObj = this.baseService.page(params, qw);
        return DataResponse.success(MBPUtil.resetPage(pageObj));
    }

    /**
     * 新增
     *
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean add(@RequestBody V dto) throws IllegalAccessException, InstantiationException {
        log.info("新增：{}", dto);
        T bean = entityClass.newInstance();
        BeanUtils.copyProperties(dto, bean);
        return baseService.save(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean update(@RequestBody V dto) throws IllegalAccessException, InstantiationException {
        log.info("修改：{}", dto);
        T bean = entityClass.newInstance();
        BeanUtils.copyProperties(dto, bean);
        return baseService.updateById(bean);
    }

    /**
     * 删除
     *
     * @return
     */
    @DeleteMapping
    public boolean delete(WebRequest webRequest) throws IllegalAccessException, InstantiationException {
        Map<String, Object> params = fillVariablesMapWithIncomingRequestParameters(webRequest);
        String key = (String) FieldUtils.readField(entityClass.newInstance(), "UID");
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        wrapper.eq(humpToUnderline(key), params.get(key));
        return baseService.remove(wrapper);
    }

    protected Map<String, String> setOrderColumns() {
        return Collections.emptyMap();
    }

    /**
     * 驼峰命名转下划线
     *
     * @param humpName
     * @return
     */
    private static String humpToUnderline(String humpName) {
        //截取下划线分成数组，
        char[] charArray = humpName.toCharArray();
        StringBuffer buffer = new StringBuffer();
        //处理字符串
        for (int i = 0, l = charArray.length; i < l; i++) {
            if (charArray[i] >= 65 && charArray[i] <= 90) {
                buffer.append("_").append(charArray[i] += 32);
            } else {
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }
}
