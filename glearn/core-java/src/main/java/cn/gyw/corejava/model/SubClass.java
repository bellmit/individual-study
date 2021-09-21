package cn.gyw.corejava.model;

/**
 * Created by guanyw on 2018/11/19.
 */
public class SubClass extends SuperClass {

//	private int mSubX = 1;
	private int mSubX;

	public SubClass() {
	}

	@Override
	public void setX(int x) {
		super.setX(x);
		mSubX = x;
		System.out.println("SubX is assigned " + x);
	}

	public void printX() {
		System.out.println("SubX = " + mSubX);
	}
}

/*Compiled from "SubClass.java"
public class com.gyw.model.pojo.SubClass extends com.gyw.model.pojo.SuperClass {

  public com.gyw.model.pojo.SubClass();
    Code:
       0: aload_0 // 将局部变量表中下标为0的元素入栈, 其实就是Java中的this, 结合invokespecial #1, 是在调用父类的构造函数, 也就是我们常见的super()
       1: invokespecial #1                  // Method com/gyw/model/pojo/SuperClass."<init>":()V，
       4: aload_0
       5: iconst_1
       6: putfield      #2                  // Field mSubX:I
       9: return

  public void setX(int);
    Code:
       0: aload_0
       1: iload_1
       2: invokespecial #3                  // Method com/gyw/model/pojo/SuperClass.setX:(I)V
       5: aload_0
       6: iload_1
       7: putfield      #2                  // Field mSubX:I
      10: getstatic     #4                  // Field java/lang/System.out:Ljava/
io/PrintStream;
      13: new           #5                  // class java/lang/StringBuilder
      16: dup
      17: invokespecial #6                  // Method java/lang/StringBuilder."<
init>":()V
      20: ldc           #7                  // String SubX is assigned
      22: invokevirtual #8                  // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      25: iload_1
      26: invokevirtual #9                  // Method java/lang/StringBuilder.ap
pend:(I)Ljava/lang/StringBuilder;
      29: invokevirtual #10                 // Method java/lang/StringBuilder.to
String:()Ljava/lang/String;
      32: invokevirtual #11                 // Method java/io/PrintStream.printl
n:(Ljava/lang/String;)V
      35: return

  public void printX();
    Code:
       0: getstatic     #4                  // Field java/lang/System.out:Ljava/
io/PrintStream;
       3: new           #5                  // class java/lang/StringBuilder
       6: dup
       7: invokespecial #6                  // Method java/lang/StringBuilder."<
init>":()V
      10: ldc           #12                 // String SubX =
      12: invokevirtual #8                  // Method java/lang/StringBuilder.ap
pend:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      15: aload_0
      16: getfield      #2                  // Field mSubX:I
      19: invokevirtual #9                  // Method java/lang/StringBuilder.ap
pend:(I)Ljava/lang/StringBuilder;
      22: invokevirtual #10                 // Method java/lang/StringBuilder.to
String:()Ljava/lang/String;
      25: invokevirtual #11                 // Method java/io/PrintStream.printl
n:(Ljava/lang/String;)V
      28: return
}*/
