package cn.gyw.corejava.clz;

import java.util.ArrayList;
import java.util.List;

public class FiledList<T> {

    private Class<T> type;
    public FiledList(Class<T> type){
        this.type = type;
    }
    public List<T> create(int nElement){
        List<T> result = new ArrayList<T>();
        try{
            for(int i=0 ;i<nElement;i++){
                result.add(type.newInstance());
            }
        }catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }

    public static void main(String[] args) {
        FiledList<CountedInteger> fl =
                new FiledList<CountedInteger>(CountedInteger.class);
        System.out.println(fl.create(15));
    }
}

class CountedInteger{
    private static long counter;
    private final long id = counter++;
    public String toString(){
        return Long.toString(id);
    }
}