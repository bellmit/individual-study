package cn.gyw.glearn.algorithm.queue;

import cn.gyw.glearn.algorithm.model.Cat;
import cn.gyw.glearn.algorithm.model.Dog;
import cn.gyw.glearn.algorithm.model.Pet;

/**
 * 实现一种猫狗队列的结构
 *
 * 题目要求：
 * 1.用户可以调用add方法将cat类或dog类的实例放入队列中
 * 2.用户可以调用poll方法，将队列中的所有实例按照进队的先后顺序依次弹出
 * 3.调用pollDog方法，将队列中dog类的实例按照进队的先后顺序依次弹出
 * 4.调用pollCat方法，将队列中cat类的实例按照进队的先后顺序依次弹出
 * 5.调用isEmpty方法，检查队列中是否有Dog或cat的实例
 * 6.调用isDogEmpty方法，检查队列中是否有Dog的实例
 * 7.调用isCatEmpty方法，检查队列中是否有Cat的实例
 */
public interface DogCatQueue {

    boolean add(Pet pet);

    Pet poll();

    Dog pollDog();

    Cat pollCat();

    boolean isEmpty();

    boolean isDogEmpty();

    boolean isCatEmpty();
}
