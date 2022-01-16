package cn.gyw.backend.service.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLoginParam {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
