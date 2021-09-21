package cn.gyw.glearn.algorithm;

import static org.junit.Assert.*;

import cn.gyw.glearn.algorithm.model.Cat;
import cn.gyw.glearn.algorithm.model.Dog;
import cn.gyw.glearn.algorithm.queue.BookDogCatQueue;
import cn.gyw.glearn.algorithm.queue.DogCatQueue;
import cn.gyw.glearn.algorithm.queue.MyDogCatQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 猫狗队列问题
 */
public class DogCatQueueTest {

    private DogCatQueue dogCatQueue;

    @Before
    public void setUp() {
//        dogCatQueue = new MyDogCatQueue();
        dogCatQueue = new BookDogCatQueue();

        Assert.assertTrue(dogCatQueue.add(new Cat()));
        Assert.assertTrue(dogCatQueue.add(new Cat()));
        Assert.assertTrue(dogCatQueue.add(new Dog()));
        Assert.assertTrue(dogCatQueue.add(new Cat()));

    }
    /**
     * 新增 Pet
     */
    @Test
    public void shouldAddPet() {
        dogCatQueue = new MyDogCatQueue();
        Assert.assertTrue(dogCatQueue.add(new Cat()));

    }

    /**
     * 输出所有的 Pet
     */
    @Test
    public void shouldPollPet() {
        System.out.println(dogCatQueue.poll());
    }

    @Test
    public void shouldPolDogs() {
        while (!dogCatQueue.isDogEmpty()) {
            System.out.println(dogCatQueue.poll());
        }
    }

    /**
     * 判断是否队列为空
     */
    @Test
    public void shouldIsEmpty() {
        Assert.assertFalse(dogCatQueue.isEmpty());
    }

    /**
     * 判断队列Dog是否为空
     */
    @Test
    public void shouldIsDogEmpty() {
        dogCatQueue.pollDog();
        Assert.assertTrue(dogCatQueue.isDogEmpty());
    }

    /**
     * 判断队列Cat是否为空
     */
    @Test
    public void shouldIsCatEmpty() {
        Assert.assertFalse(dogCatQueue.isCatEmpty());
    }
}
