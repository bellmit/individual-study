package cn.gyw.corejava.concurrent.task;


import cn.gyw.corejava.model.Person;

/**
 * say hello 任务
 *
 * Created by guanyw on 2019/1/23.
 */
public class PersonSayTask implements Task {

    private String name;

    private Person person = new Person("tom");

    private int counter = 0;

    /**
     * 线程私有
     */
    private ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public PersonSayTask() {
    }

    public PersonSayTask(String name) {
        this.name = name;
    }

    public void set() {
        String randName = "random";
        System.out.println(Thread.currentThread().getName() + ", 随机名：" + randName);
        person = new Person(randName);
        threadLocal.set(person);
    }

    @Override
    public void execute() {
        set();
        try {
            long sleepMills = 1000;
            System.out.println(Thread.currentThread().getName() + "休眠：" + sleepMills + " ms");
            Thread.sleep(sleepMills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", after sleep person name :" + threadLocal.get().getName());
    }

}
