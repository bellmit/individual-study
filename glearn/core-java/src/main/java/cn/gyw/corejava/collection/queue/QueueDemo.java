package cn.gyw.corejava.collection.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class QueueDemo {
    public static void main(String[] args) {
        new QueueDemo().testQueue();
    }

    public void testQueue(){
        Queue<Integer> queue = new LinkedList<Integer>();
        Random rand = new Random(47);
        for(int i= 0 ; i < 10 ; i++){
            queue.offer(rand.nextInt(i + 10));
        }
        printQueue(queue);

        Queue<Character> qc = new LinkedList<Character>();
        for(char c : "Bourehuersre".toCharArray()){
            qc.offer(c);
        }
        printQueue(qc);
    }


    public void printQueue(Queue queue){
        while(queue.peek()!=null){
            System.out.print(queue.remove() + "   ");
        }
        System.out.println();
    }
}
