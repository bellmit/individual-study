package cn.gyw.corejava.clz;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassDemo {

    interface A {
    }

    interface B {
    }

    interface C {
    }

    class Toy {
        Toy() {
        }

        Toy(int i) {
        }
    }

    class FancyToy extends Toy implements
            A, B, C {
        public FancyToy() {
            super(1);
        }
    }

    public static void main(String[] args) {
        Class cl = null;
        try{
            cl = Class.forName("com.clz.FancyToy");
        }catch(ClassNotFoundException e){
            System.out.println("Can not find FancyToy");
            System.exit(1);
        }
        printInfo(cl);

        for(Class face : cl.getInterfaces()){
            printInfo(face);
        }

        Class up = cl.getSuperclass();
        Object obj = null;
        try{
            obj = up.newInstance();
        }catch(InstantiationException e){
            System.out.println("Can not Instance...");
            System.exit(1);
        }catch(IllegalAccessException e){

            System.exit(1);
        }
        printInfo(obj.getClass());
    }

    static void printInfo(Class cc){
        System.out.println("Class name : " + cc.getName() +
                "  >> is interface ? [" + cc.isInterface()+"]");
        System.out.println("Simple name : " + cc.getSimpleName());
        System.out.println("Canonical name : " + cc.getCanonicalName());
        System.out.println(cc.getTypeParameters());
        for (Method method : cc.getDeclaredMethods()) {
            System.out.println(Arrays.toString(method.getParameterTypes()));
        }
    }
}