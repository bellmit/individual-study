package cn.gyw.backend.service.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 用户登录参数
 */
@Getter
@Setter
public class AdminDto {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String icon;
    @Email
    private String email;
    private String nickName;
    private String note;
    private Date createTime;
}