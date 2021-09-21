package cn.gyw.corejava.effective.sixth;

import java.util.Set;

public enum Operation {

    PLUS("+", CalcType.SIMPLE) {
        @Override
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-", CalcType.SIMPLE) {
        @Override
        double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("x", CalcType.COMPLEX) {
        @Override
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/", CalcType.COMPLEX) {
        @Override
        double apply(double x, double y) {
            return x / y;
        }
    };

    private static final String NAME = "Operation";
    // 特定的数据域
    private final String symbol;
    private final CalcType calcType;
    // 位域
    private static final int STYLE_ONE = 1 << 0; // 1
    private static final int STYLE_TWO = 1 << 1; // 2
    private static final int STYLE_THREE = 1 << 2; // 4
    private static final int STYLE_FIVE = 1 << 3; // 8

    Operation(String symbol, CalcType calcType) {
        this.symbol = symbol;
        this.calcType = calcType;
        System.out.println(NAME);
    }

    // 所有枚举共享的方法
    @Override
    public String toString() {
        return this.symbol;
    }

    // 特定行为的方法
    abstract double apply(double x, double y);

    // 位运算，集合运算
//    public void calc(STYLE_ONE | STYLE_TWO)

    public void calc(Set<Operation> set) {
        
    }

    private enum CalcType {
        SIMPLE {
            @Override
            void show() {
                System.out.println("简单计算");
            }
        },
        COMPLEX {
            @Override
            void show() {
                System.out.println("复杂计算");
            }
        };

        abstract void show();
    }
}
