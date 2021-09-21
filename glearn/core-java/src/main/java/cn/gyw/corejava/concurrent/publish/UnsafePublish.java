package cn.gyw.corejava.concurrent.publish;

import cn.gyw.platform.annotations.NotThreadSafe;

import java.util.Arrays;

@NotThreadSafe
public class UnsafePublish {

    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        System.out.println(Arrays.asList(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        System.out.println(Arrays.asList(unsafePublish.getStates()));
    }
}
