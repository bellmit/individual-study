package cn.gyw.backend.system.service;

import cn.gyw.backend.system.model.entity.Resource;
import cn.gyw.backend.system.dao.ResourceMapper;
import cn.gyw.components.web.base.mgb.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService extends BaseService<Resource> {

	@Autowired
    private ResourceMapper resourceMapper;
}
