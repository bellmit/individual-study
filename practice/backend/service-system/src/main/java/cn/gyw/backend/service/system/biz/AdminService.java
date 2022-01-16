package cn.gyw.backend.service.system.biz;

import cn.gyw.backend.components.common.base.mgb.BaseService;
import cn.gyw.backend.components.common.utils.RequestUtil;
import cn.gyw.backend.components.security.util.JwtTokenUtil;
import cn.gyw.backend.service.system.dao.AdminLoginLogMapper;
import cn.gyw.backend.service.system.dao.AdminMapper;
import cn.gyw.backend.service.system.dao.AdminRoleRelationMapper;
import cn.gyw.backend.service.system.enums.SystemRespEnum;
import cn.gyw.backend.service.system.model.dto.AdminDto;
import cn.gyw.backend.service.system.model.po.*;
import cn.gyw.backend.service.system.model.vo.AdminUserDetails;
import cn.hutool.core.collection.CollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminService extends BaseService<Admin> {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;

    public Admin getAdminByUsername(String username) {
//        Admin admin = adminCacheService.getAdmin(username);
        Admin admin = null;
        if (admin != null) return admin;
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo(Admin.KEY_USERNAME, username);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
//            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    public Admin register(AdminDto adminDto) {
        Admin umsAdmin = new Admin();
        BeanUtils.copyProperties(adminDto, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo(Admin.KEY_USERNAME, umsAdmin.getUsername());
        List<Admin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            SystemRespEnum.PASSWORD_ERROR.assertTrue(passwordEncoder.matches(password, userDetails.getPassword()));
            SystemRespEnum.USER_DISABLE.assertTrue(userDetails.isEnabled());
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        Admin admin = getAdminByUsername(username);
        if (admin == null) return;
        AdminLoginLog loginLog = new AdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        adminLoginLogMapper.insert(loginLog);
    }

    public List<Resource> getResourceList(Long adminId) {
//        List<Resource> resourceList = adminCacheService.getResourceList(adminId);
        List<Resource> resourceList = new ArrayList<>();
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = adminRoleRelationMapper.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            // 缓存资源列表
//            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    public List<Role> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleList(adminId);
    }

    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        Admin admin = getAdminByUsername(username);
        if (admin != null) {
            List<Resource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public int updateRole(Long id, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        Example example = new Example(AdminRoleRelation.class);
        example.createCriteria().andEqualTo(AdminRoleRelation.KEY_ID, id);
        adminRoleRelationMapper.deleteByExample(example);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AdminRoleRelation roleRelation = new AdminRoleRelation();
                roleRelation.setAdminId(id);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationMapper.insertList(list);
        }
        // adminCacheService.delResourceList(adminId);
        return count;
    }
}
