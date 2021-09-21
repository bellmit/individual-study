package cn.gyw.backend.im.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -1676895821714945592L;

    private String idCard;
    private String name;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String idCard, String name) {
        this.idCard = idCard;
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
