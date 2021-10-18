package cn.gyw.corejava.tuple;

public class FiveTuple<A,B,C,D,E> extends FourTuple<A, B, C, D> {
    public final E fifth;
    public FiveTuple(A first,B second,C third,D fourth,E fifth){
        super(first, second, third, fourth);
        this.fifth = fifth;
    }

    @Override
    public String toString(){
        return "type1:"+first+">type2:"+second+">type3:"+third
                +">type4:"+fourth + ">type5:"+fifth;
    }
}
