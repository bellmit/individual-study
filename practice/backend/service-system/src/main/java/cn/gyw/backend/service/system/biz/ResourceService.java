package cn.gyw.backend.service.system.biz;

import cn.gyw.backend.components.common.base.mgb.BaseService;
import cn.gyw.backend.service.system.dao.ResourceMapper;
import cn.gyw.backend.service.system.model.po.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService extends BaseService<Resource> {

	@Autowired
    private ResourceMapper resourceMapper;
}
