package cn.gyw.components.web.base.mgb;

import cn.gyw.components.web.base.AbstractController;
import cn.gyw.components.web.enums.CommonRespEnum;
import cn.gyw.components.web.model.BaseRequest;
import cn.gyw.components.web.model.PageData;
import cn.gyw.components.web.utils.PageHelperUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通用Controller
 */
public abstract class BaseController<Q extends BaseRequest<DTO>, T, DTO> extends AbstractController<T> {

    private IBaseService<T> baseService;

    @ApiParam(name = "请求参数")
    @ApiOperation(value = "查询", notes = "基本查询")
    @GetMapping
    public List<T> query(Q request) throws Exception {
        log.debug("query() request:{}", request);
        T condition = entityClass.newInstance();
        if (Objects.nonNull(request.getData())) {
            BeanUtils.copyProperties(request.getData(), condition);
        }
        log.debug("query condition bean :{}", condition);
        List<T> data = baseService.query(condition);
        return data;
    }

    /**
     * 分页查询
     * <p>
     * keyword: 关键字查询，需要模糊查询的字段需要在T 中声明 KEYWORD=字段，暂时只支持一个字段模糊
     *
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
    public int add(@RequestBody Q request) throws IllegalAccessException, InstantiationException {
        log.info("add data：{}", request);
        T bean = entityClass.newInstance();
        BeanUtils.copyProperties(request.getData(), bean);
        return baseService.save(bean);
    }

    /**
     * 修改
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int update(@RequestBody Q request) throws IllegalAccessException, InstantiationException {
        log.info("update data：{}", request);
        T bean = entityClass.newInstance();
        BeanUtils.copyProperties(request.getData(), bean);
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

    @PostConstruct
    @SuppressWarnings("unchecked")
    @Override
    public void init() throws IllegalAccessException {
        super.init();
        // forceAccess: 访问非public 属性
        baseService = (IBaseService<T>) FieldUtils.readField(this, serviceClassSimpleName, true);
    }
}
