package cn.gyw.glearn.algorithm.queue;


import cn.gyw.glearn.algorithm.model.Cat;
import cn.gyw.glearn.algorithm.model.Dog;
import cn.gyw.glearn.algorithm.model.Pet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自己实现的猫狗队列
 */
public class MyDogCatQueue implements DogCatQueue {

    private LinkedBlockingQueue<Pet> petQueue = new LinkedBlockingQueue<>();
    private List<Pet> tmp = new ArrayList<>();

    @Override
    public boolean add(Pet pet) {
        return petQueue.add(pet);
    }

    @Override
    public Pet poll() {
        return petQueue.poll();
    }

    @Override
    public Dog pollDog() {
        Dog dog = null;
        while (petQueue.size() > 0) {
            Pet pet = petQueue.poll();
            if (pet instanceof Dog) {
                dog = (Dog)pet;
            } else {
                tmp.add(pet);
            }
        }
        tmp.forEach(e -> petQueue.add(e));
        return dog;
    }

    @Override
    public Cat pollCat() {
        Cat cat = null;
        while (petQueue.size() > 0) {
            Pet pet = petQueue.poll();
            if (pet instanceof Cat) {
                cat = (Cat)pet;
            } else {
                tmp.add(pet);
            }
        }
        tmp.forEach(e -> petQueue.add(e));
        return cat;
    }

    /**
     * 检查队列中是否有Dog或cat的实例
     * @return
     */
    @Override
    public boolean isEmpty() {
        return petQueue.isEmpty();
    }

    /**
     * 检查队列中是否有Dog的实例
     * @return
     */
    @Override
    public boolean isDogEmpty() {
        Iterator<Pet> iterator = petQueue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof  Dog) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查队列中是否有Cat的实例
     * @return
     */
    @Override
    public boolean isCatEmpty() {
        Iterator<Pet> iterator = petQueue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof  Cat) {
                return false;
            }
        }
        return true;
    }
}
