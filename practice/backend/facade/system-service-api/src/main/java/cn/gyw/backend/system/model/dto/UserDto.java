package cn.gyw.backend.system.model.dto;

import java.io.Serializable;

/**
 * 用户
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = 8150090990632620981L;
    private Integer uid;
    private String userName;
    private String userNameCn;
    private Integer roleId;
    private String password;
    private String avatar;
    private int age;
    private String hobby;
    private String createdTime;
    private String token;

    // 角色
    private RoleDto role;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getUserNameCn() {
        return userNameCn;
    }

    public void setUserNameCn(String userNameCn) {
        this.userNameCn = userNameCn;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "uid='" + uid + '\'' +
                ", username='" + userName + '\'' +
                ", userNameCn='" + userNameCn + '\'' +
                ", roleId=" + roleId +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", hobby='" + hobby + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
