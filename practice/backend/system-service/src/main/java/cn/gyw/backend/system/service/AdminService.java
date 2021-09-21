package cn.gyw.backend.system.service;

import cn.gyw.backend.system.bo.AdminUserDetails;
import cn.gyw.backend.system.entity.Admin;
import cn.gyw.backend.system.dao.AdminMapper;
import cn.gyw.backend.system.entity.Resource;
import cn.gyw.components.web.base.mgb.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService extends BaseService<Admin> {

    @Autowired
    private AdminMapper adminMapper;

    public UserDetails loadUserByUsername(String username) {
        Admin admin = new Admin();
        List<Resource> resourceList = new ArrayList<>();
        return new AdminUserDetails(admin, resourceList);
    }
}
