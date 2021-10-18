package cn.gyw.corejava.tuple;

import java.util.Objects;

public class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TwoTuple<?, ?> twoTuple = (TwoTuple<?, ?>) o;
        return Objects.equals(first, twoTuple.first) &&
                Objects.equals(second, twoTuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "type1:" + first + ">type2:" + second;
    }
}
