package cn.gyw.glearn.design.creational;

import cn.gyw.glearn.design.creational.prototype.Resume;
import org.junit.Test;

/**
 * 原型模式测试
 */
public class PrototypeTest {

    /**
     * 简历打印
     */
    @Test
    public void superficialCopyPrintResume() {
        Resume a = new Resume("大鸟");
        a.setPersonalInfo("男", "29");
        a.setWorkExperience("2016-2017", "XX公司");

        Resume b = new Resume("大鸟");
        b.setPersonalInfo("男", "29");
        b.setWorkExperience("2016-2017", "XX公司");

        Resume c = new Resume("大鸟");
        c.setPersonalInfo("男", "29");
        c.setWorkExperience("2016-2017", "XX公司");

        a.display();
        b.display();
        c.display();
    }

    /**
     * 简历打印
     */
    @Test
    public void printResume() {
        Resume a = new Resume("大鸟");
        a.setPersonalInfo("男", "29");
        a.setWorkExperience("2016-2017", "XX公司");

        Resume b = new Resume("大鸟");
        b.setPersonalInfo("男", "29");
        b.setWorkExperience("2016-2017", "XX公司");

        Resume c = new Resume("大鸟");
        c.setPersonalInfo("男", "29");
        c.setWorkExperience("2016-2017", "XX公司");

        a.display();
        b.display();
        c.display();
    }

}
