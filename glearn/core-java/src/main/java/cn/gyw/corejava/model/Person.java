package cn.gyw.corejava.model;

/**
 * Created by guanyw on 2019/1/22.
 */
public class Person {

    private long id;

    private Long idCard;

    private String name;

    private Integer age;

    private String hobby;

    public void sayHello() {
        System.out.println("hello, my name is " + this.name);
    }

    public void sayHello(String name) {
        System.out.println("hello " + name + ", my name is " + this.name);
    }

    public Person() {
        System.out.println("Person-默认构造函数");
    }

    public Person(Long idCard) {
        System.out.println("Person-有参构造函数");
        this.idCard = idCard;
    }

    public Person(Long idCard, String name, Integer age) {
        System.out.println("Person-有参构造函数");
        this.idCard = idCard;
        this.name = name;
        this.age = age;
    }

    public Person(String name) {
        this.idCard = id++;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "idCard=" + idCard +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
