package cn.gyw.glearn.design.behaviour.templatemethod;

/**
 * 模板方法模式
 * 在父类中定义通用的方法部分，具体的某些特定逻辑由子类实现
 * 以银行业务为例：
 *
 * 去银行办业务，银行给我们提供了一个模板就是：先取号，排对，办理业务（核心部分我们子类完成），
 * 给客服人员评分，完毕
 * Created by guanyw on 2018/7/10.
 */
public abstract class BankTemplateMethod {

	//模板方法中其他业务逻辑
    //1.取号排队
    public void takeNumber(){
        System.out.println("取号排队");
    }
    //2.办理具体的业务：这里留给子类来实现！！！
    public abstract void transact();

    //3.给客服评分
    public void evaluate(){
        System.out.println("反馈评分");
    }
    //模板方法
    public final void process(){
        this.takeNumber();
        this.transact();//这里则是具体的模板方法函数
        this.evaluate();
    }

}
