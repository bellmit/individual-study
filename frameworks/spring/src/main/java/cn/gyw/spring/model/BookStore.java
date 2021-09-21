package cn.gyw.spring.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class BookStore implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private String name;

//    @Autowired
    private Book book;

    public BookStore() {
    }

    @Autowired
    public BookStore(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @Autowired
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "book=" + book +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name >" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("容器中的apc ： " + applicationContext);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        name = stringValueResolver.resolveStringValue("${person.name}");
        System.out.println(">> Bookstore.name :" + name);
    }
}
