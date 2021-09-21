package cn.gyw.corejava.tuple;

public class ThreeTuple <A,B,C> extends TwoTuple<A,B> {
    public final C third;
    public ThreeTuple(A first,B second,C third){
        super(first,second);
        this.third = third;
    }

    public String toString(){
        return "type1:"+first+">type2:"+second+">type3:"+third;
    }
}
