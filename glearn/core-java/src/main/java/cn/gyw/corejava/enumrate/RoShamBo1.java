package cn.gyw.corejava.enumrate;

import java.util.Random;

/**
 * 多路分发实现方式一
 * @author guanyw
 *
 */
enum Outcome {
	WIN,LOSE,DRAW;
}
interface Item {
	public Outcome complete(Item it);
	public Outcome eval(Paper it);
	public Outcome eval(Scissors it);
	public Outcome eval(Rock it);
}

class Paper implements Item {

	@Override
	public Outcome complete(Item it) {
		return it.eval(this);
	}

	@Override
	public Outcome eval(Paper it) {
		return Outcome.DRAW;
	}

	@Override
	public Outcome eval(Scissors it) {
		return Outcome.WIN;
	}

	@Override
	public Outcome eval(Rock it) {
		return Outcome.LOSE;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

class Scissors implements Item {

	@Override
	public Outcome complete(Item it) {
		return it.eval(this);
	}

	@Override
	public Outcome eval(Paper it) {
		return Outcome.WIN;
	}

	@Override
	public Outcome eval(Scissors it) {
		return Outcome.DRAW;
	}

	@Override
	public Outcome eval(Rock it) {
		return Outcome.LOSE;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

class Rock implements Item {

	@Override
	public Outcome complete(Item it) {
		return it.eval(this);
	}

	@Override
	public Outcome eval(Paper it) {
		return Outcome.LOSE;
	}

	@Override
	public Outcome eval(Scissors it) {
		return Outcome.WIN;
	}

	@Override
	public Outcome eval(Rock it) {
		return Outcome.DRAW;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

public class RoShamBo1 {
	private static final int size =20;
	private static Random rand = new Random(47);
	public static Item newItem(){
		int i = rand.nextInt(3);
		switch(i){
		default:
		case 0:return new Scissors();
		case 1:return new Rock();
		case 2:return new Paper();
		}
	}

	static void complete (Item it1 , Item it2) {
		System.out.println(it1 + " vs "+ it2 + " result is :"+ it1.complete(it2));
	}

	public static void main(String[] args) {

//		Random rand = new Random(47);
//		for(int i=0;i<size;i++)
//		System.out.println(rand.nextInt(3));

		for(int i=0;i<size;i++){
			complete(newItem(),newItem());
		}
	}
}
