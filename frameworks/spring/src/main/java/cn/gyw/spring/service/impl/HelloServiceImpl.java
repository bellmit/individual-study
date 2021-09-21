package cn.gyw.spring.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.gyw.spring.service.HelloService;

//@Primary
@Service(value = "helloService")
public class HelloServiceImpl implements HelloService {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HelloServiceImpl(String label) {
        this.label = label;
    }

    public HelloServiceImpl() {
    }

    @Override
    public String toString() {
        return "HelloServiceImpl{" +
                "label='" + label + '\'' +
                '}';
    }
}
