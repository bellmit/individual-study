package cn.gyw.components.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description TODO
 * @createdTime 2021/10/20 15:26
 */
public class BaseRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;

    private LocalDateTime currentTime = LocalDateTime.now();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }
}
