package cn.gyw.corejava.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Jdk 1.7 加入的java.lang.invoke包
 * Java语言也可以拥有类似于函数指针或者委托方法别名的工具了
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        /* 无论obj最终是哪个实现类，下面这句都能正确调用到println方法 */
        getPrintlnMH(obj).invokeExact("gyw");

    }

    private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        /*
        * MethodType：代表“方法类型”，包含了方法的返回值（methodType（）第一个参数）
        * 以及具体参数（methodType（）第二个以后的参数）
        * */
        MethodType mt = MethodType.methodType(void.class, String.class);
        /*
        lookup()方法来自于MethodHandles.lookup,这句的作用是在指定类中查找符合给定的方法名称、方法类型，并且符合
        调用权限的方法句柄
        因为这里调用的是一个虚方法，按照java语言的规则，方法第一个参数是隐式的，代表该方法的接收者，也即是this指向的对象，
        这个参数以前是放在参数列表中进行传递的，而现在提供了bindTo()方法来完成这件事
         */
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }
}
