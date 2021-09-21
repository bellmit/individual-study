package cn.gyw.glearn.design.creational.simplefactory;

class Calculator {

    /**
     * 实现：封装、无继承、多态，增加逻辑就得修改Calculator类
     * @param operation
     * @param arg1
     * @param arg2
     * @return
     */
    public int calc1(String operation, int arg1, int arg2) {
        switch (operation) {
            case "+":
                return arg1 + arg2;
            case "-":
                return arg1 - arg2;
            case "*":
                return arg1 * arg2;
            case "/":
                if (arg2 == 0) {
                    throw new RuntimeException("arg2 is zero");
                }
                return arg1 / arg2;
            default:
                throw new RuntimeException("not support opperation :" + operation);
        }
    }

    /**
     * 简单工厂方法模式
     * @param operation
     * @param arg1
     * @param arg2
     * @return
     */
    public static Operation getOperation(String operation, int arg1, int arg2) {
        switch (operation) {
            case "+":
                return new OperationAdd(arg1, arg2);
            case "-":
                return new OperationSub(arg1, arg2);
            case "*":
                return new OperationMul(arg1, arg2);
            case "/":
                return new OperationDiv(arg1, arg2);
            default:
                throw new RuntimeException("not support opperation :" + operation);
        }
    }
}
