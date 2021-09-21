package cn.gyw.backend.system.service;

import cn.gyw.backend.system.entity.Role;
import cn.gyw.backend.system.dao.RoleMapper;
import cn.gyw.components.web.base.mgb.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role> {

	@Autowired
    private RoleMapper roleMapper;
}
