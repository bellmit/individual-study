package cn.gyw.corejava.tuple;

public class TwoTuple <A,B> {
    public final A first;
    public final B second;

    public TwoTuple(A first,B second){
        this.first = first;
        this.second = second;
    }

    public String toString(){
        return "type1:"+first+">type2:"+second;
    }
}
