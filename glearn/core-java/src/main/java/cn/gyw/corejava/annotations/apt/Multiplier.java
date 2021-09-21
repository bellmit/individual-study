package cn.gyw.corejava.annotations.apt;

@ExtractInterface("IMultiplier")
public class Multiplier {
	public int multiply(int x,int y){
		int total = 0;
		for(int i = 0;i<x;i++)
			total = add(total,y);
		return total;
	}
	private int add(int x,int y){  //私有方法，因此不将其作为接口的一部分，注解给出了值IMultiplier，就是要生成的接口的名字
		return x+y;
	}

	public static void main(String args[]){
		Multiplier m = new Multiplier();
		System.out.println("11*16 = "+ m.multiply(11, 16));
	}
}
