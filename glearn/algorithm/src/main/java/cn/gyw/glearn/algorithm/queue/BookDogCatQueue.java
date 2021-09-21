package cn.gyw.glearn.algorithm.queue;

import cn.gyw.glearn.algorithm.model.Cat;
import cn.gyw.glearn.algorithm.model.Dog;
import cn.gyw.glearn.algorithm.model.Pet;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列 实现
 */
public class BookDogCatQueue implements DogCatQueue {

    /**
     * Dog 队列
     */
    private Queue<PetEnterQueue> dogQ;

    /**
     * Cat 队列
     */
    private Queue<PetEnterQueue> catQ;

    private long count;

    public BookDogCatQueue() {
        this.dogQ = new LinkedList<>();
        this.catQ = new LinkedList<>();
        this.count = 0;
    }

    @Override
    public boolean add(Pet pet) {
        if (pet.getType().equalsIgnoreCase("dog")) {
            dogQ.add(new PetEnterQueue(pet, this.count++));
        } else if (pet.getType().equalsIgnoreCase("cat")) {
            catQ.add(new PetEnterQueue(pet, this.count++));
        } else {
            throw new IllegalArgumentException("err, not cat or dog");
        }
        return true;
    }

    @Override
    public Pet poll() {
        if (!dogQ.isEmpty() && !catQ.isEmpty()) {
            if (dogQ.peek().getCount() < catQ.poll().getCount()) {
                return dogQ.poll().getPet();
            }
            return catQ.poll().getPet();
        } else if (!dogQ.isEmpty() && catQ.isEmpty()) {
            return dogQ.poll().getPet();
        } else if (dogQ.isEmpty() && !catQ.isEmpty()) {
            return catQ.poll().getPet();
        } else {
            throw new NullPointerException("this queue is empty");
        }
    }

    @Override
    public Dog pollDog() {
        if (this.isDogEmpty()) {
            throw new NullPointerException("Dog queue is empty");
        }
        return (Dog) dogQ.poll().getPet();
    }

    @Override
    public Cat pollCat() {
        if (this.isCatEmpty()) {
            throw new NullPointerException("Cat queue is empty");
        }
        return (Cat) catQ.poll().getPet();
    }

    @Override
    public boolean isEmpty() {
        return dogQ.isEmpty() && catQ.isEmpty();
    }

    @Override
    public boolean isDogEmpty() {
        return dogQ.isEmpty();
    }

    @Override
    public boolean isCatEmpty() {
        return catQ.isEmpty();
    }

    /**
     * 给不同实例盖上时间戳，而不修改原有类
     */
    private class PetEnterQueue {
        // 入队对象
        private Pet pet;
        // 入队时间戳
        private long count;

        public PetEnterQueue(Pet pet, long count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return pet;
        }

        public long getCount() {
            return count;
        }
    }
}
