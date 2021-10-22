package cn.gyw.components.web.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController<T> {

    protected static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected String entityClassFullName;
    protected String entityClassSimpleName;
    protected Class<T> entityClass;
    protected String serviceClassSimpleName;

    protected Map<String, Object> fillVariablesMapWithIncomingRequestParameters(WebRequest webRequest) {
        return this.fillVariablesMapWithIncomingRequestParameters(webRequest.getParameterMap());
    }

    protected Map<String, Object> fillVariablesMapWithIncomingRequestParameters(Map<String, String[]> requestParams) {
        Map<String, Object> variableMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            if (!entry.getKey().startsWith("javax")) {
                variableMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return variableMap;
    }

    protected Date parseDate(String dateStr) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateSdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化方法
     */
    public void init() throws IllegalAccessException {
        Type genericInterfaces = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genericInterfaces).getActualTypeArguments();
        entityClass = (Class<T>) params[2];
        entityClassFullName = entityClass.getName();
        entityClassSimpleName = entityClass.getSimpleName();

        StringBuilder serviceBuilder = new StringBuilder();
        serviceBuilder.append(entityClassSimpleName.substring(0, 1).toLowerCase())
                .append(entityClassSimpleName.substring(1)).append("Service");
        log.info("base service name:{}", serviceBuilder.toString());
        serviceClassSimpleName = serviceBuilder.toString();
    }
}
