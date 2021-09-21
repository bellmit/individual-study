package cn.gyw.corejava.clz.generator;

public class CoffeeGeneratorTest {
    public static void main(String[] args) {
        CoffeeGenerator gen = new CoffeeGenerator();
        for(int i =0;i<5;i++){
            System.out.println(gen.next());
        }

        for(Coffee c : new CoffeeGenerator(5))
            System.out.println(c);
    }
}
