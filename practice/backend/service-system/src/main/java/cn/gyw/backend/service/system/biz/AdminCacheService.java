package cn.gyw.backend.service.system.biz;

import cn.gyw.backend.service.system.biz.interfaces.IAdminCacheService;
import cn.gyw.backend.service.system.model.po.Admin;
import cn.gyw.backend.service.system.model.po.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCacheService implements IAdminCacheService {

    @Override
    public void delAdmin(Long adminId) {

    }

    @Override
    public void delResourceList(Long adminId) {

    }

    @Override
    public void delResourceListByRole(Long roleId) {

    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {

    }

    @Override
    public void delResourceListByResource(Long resourceId) {

    }

    @Override
    public Admin getAdmin(String username) {
        return null;
    }

    @Override
    public void setAdmin(Admin admin) {

    }

    @Override
    public List<Resource> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public void setResourceList(Long adminId, List<Resource> resourceList) {

    }
}
