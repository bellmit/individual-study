package cn.gyw.middleware.redis.model;

public class User {

    private String uid;
    private String userName;
    private String age;
    private String sex;

    public User() {
    }

    public User(String uid, String userName, String age, String sex) {
        this.uid = uid;
        this.userName = userName;
        this.age = age;
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
