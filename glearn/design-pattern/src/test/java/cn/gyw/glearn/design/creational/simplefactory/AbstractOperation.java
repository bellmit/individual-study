package cn.gyw.glearn.design.creational.simplefactory;

abstract class AbstractOperation implements Operation {

    protected int arg1;
    protected int arg2;

    protected AbstractOperation(int arg1, int arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

}