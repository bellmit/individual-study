package cn.gyw.corejava.model;

/**
 * Java 重写方法与初始化的隐患
 * <p>
 * Created by guanyw on 2018/11/19.
 */
public class SuperClass {

	private int mSuperX;

	public SuperClass() {
		setX(99);
	}

	public void setX(int x) {
		mSuperX = x;
	}

}

/*
Compiled from "SuperClass.java"
public class com.gyw.model.pojo.SuperClass {
  public com.gyw.model.pojo.SuperClass();
    Code:
       0: aload_0 // 局部变量表第一个元素加载入栈，即将当前对象的引用入栈
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V，调用Object对象的构造函数
       4: aload_0 // 将局部变量表第一个元素入栈，每个方法的开始和结束，对应一个栈帧
       5: bipush        99 // 压入 99
       7: invokevirtual #2                  // Method setX:(I)V // 执行set方法，被子类重写，调用子类方法
      10: return

  public void setX(int);
    Code:
       0: aload_0
       1: iload_1
       2: putfield      #3                  // Field mSuperX:I
       5: return
}*/
