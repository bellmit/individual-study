package cn.gyw.thirdpart.date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @date 2021/10/29 14:30
 */
class DateValidatorTest {

    private static final Logger log = LoggerFactory.getLogger(DateValidatorTest.class);

    /**
     * 空数组校验
     */
    @Test
    void verifyNullArray() {
        Request request = new Request();
        request.setDateStr("");
        DateValidator.validate(request, "dateStr");
        request.setDateStr(null);
        DateValidator.validate(request, "dateStr");
        // 数组字段 == null
        Optional.ofNullable(request.getOtherObjs()).ifPresent(otherObjs ->
                Stream.of(request.getOtherObjs()).forEach(otherObj ->
                        DateValidator.validate(otherObj, "otherDate")));
        // 空数组
        OtherObj[] otherObjArray1 = new OtherObj[0];
        request.setOtherObjs(otherObjArray1);
        Optional.ofNullable(request.getOtherObjs()).ifPresent(otherObjs ->
                Stream.of(request.getOtherObjs()).forEach(otherObj ->
                        DateValidator.validate(otherObj, "otherDate")));

        // 数组有值，格式正确
        OtherObj otherObj1 = new OtherObj();
        otherObj1.setOtherDate("20211103");
        OtherObj[] otherObjArray2 = new OtherObj[]{otherObj1};
        request.setOtherObjs(otherObjArray2);
        Optional.ofNullable(request.getOtherObjs()).ifPresent(otherObjs ->
                Stream.of(request.getOtherObjs()).forEach(otherObj ->
                        DateValidator.validate(otherObj, "otherDate")));
        // 数组有值，格式不正确
        OtherObj otherObj2 = new OtherObj();
        otherObj2.setOtherDate("2021113");
        OtherObj otherObj3 = new OtherObj();
        otherObj3.setOtherDate("20211103");
        OtherObj[] otherObjArray3 = new OtherObj[]{otherObj2, otherObj3};
        request.setOtherObjs(otherObjArray3);
        Optional.ofNullable(request.getOtherObjs()).ifPresent(otherObjs ->
                Stream.of(request.getOtherObjs()).forEach(otherObj ->
                        DateValidator.validate(otherObj, "otherDate")));
    }

    /**
     * 验证长度
     */
    @Test
    void verifyLength() {
        Request request = new Request();
        request.setDateStr("");
        DateValidator.validate(request, "dateStr");
        request.setDateStr(null);
        DateValidator.validate(request, "dateStr");

        assertThrows(RuntimeException.class, () -> {
            request.setDateStr("111");
            System.out.println("len=3>>" + request.getDateStr());
            DateValidator.validate(request, "dateStr");
        });

        assertThrows(RuntimeException.class, () -> {
            request.setDateStr("202101011");
            System.out.println("len=9>>" + request.getDateStr());
            DateValidator.validate(request, "dateStr");
        });
    }

    /**
     * 验证日期有效性
     */
    @Test
    void verifyValidDate() {
        Request request = new Request();
        request.setDateStr("20211031");
        System.out.println("正确日期>>" + request.getDateStr());
        DateValidator.validate(request, "dateStr");

        assertThrows(RuntimeException.class, () -> {
            request.setDateStr("20211032");
            System.out.println("不存在的日期>>" + request.getDateStr());
            DateValidator.validate(request, "dateStr");
        });

        assertThrows(RuntimeException.class, () -> {
            request.setDateStr("20210229");
            System.out.println("平年2月29日>>" + request.getDateStr());
            DateValidator.validate(request, "dateStr");
        });

        assertThrows(RuntimeException.class, () -> {
            request.setDateStr("aaaaaaaa");
            System.out.println("非法8位字符>>" + request.getDateStr());
            DateValidator.validate(request, "dateStr");
        });
    }

    /**
     * 普通验证，是否修改了原值
     */
    @Test
    void normal() {
        Request request = new Request();
        request.setDateStr("20211101");
        InnerObj innerObj = new InnerObj();
        innerObj.setInnerDate("20220901");
        request.setInnerObj(innerObj);

        List<InnerObj> innerObjList = new ArrayList<>();
        InnerObj innerObj2 = new InnerObj();
        innerObj2.setInnerDate("20221101");
        innerObjList.add(innerObj2);
        request.setInnerObjList(innerObjList);

        DateValidator.validate(request, "dateStr");
        request.getInnerObjList().forEach(obj -> DateValidator.validate(obj, "innerDate"));

        // Stream.of(null).forEach(obj -> DateValidator.validate(obj, "innerDate"));

        Optional.ofNullable(request.getInnerObjList()).ifPresent(innerObjs ->
                innerObjs.forEach(obj -> DateValidator.validate(obj, "innerDate")));
    }

    /**
     * 验证五年的所有日期格式
     */
    @Test
    void tenYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDay = LocalDate.parse("20000101", formatter);
        LocalDate endDay = LocalDate.parse("20210101", formatter);
        String curDate = null;
        Request request = new Request();
        while (startDay.isBefore(endDay)) {
            curDate = formatter.format(startDay);
            request.setDateStr(curDate);
            log.info("{} >> 是否平年：{}", curDate, startDay.isLeapYear());
            DateValidator.validate(request, "dateStr");
            startDay = startDay.plusDays(1);
        }
    }

    public class Request {
        String dateStr;

        InnerObj innerObj;

        List<InnerObj> innerObjList;

        OtherObj[] otherObjs;

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public InnerObj getInnerObj() {
            return innerObj;
        }

        public void setInnerObj(InnerObj innerObj) {
            this.innerObj = innerObj;
        }

        public List<InnerObj> getInnerObjList() {
            return innerObjList;
        }

        public void setInnerObjList(List<InnerObj> innerObjList) {
            this.innerObjList = innerObjList;
        }


        public OtherObj[] getOtherObjs() {
            return otherObjs;
        }

        public void setOtherObjs(OtherObj[] otherObjs) {
            this.otherObjs = otherObjs;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "dateStr='" + dateStr + '\'' +
                    ", innerObj=" + innerObj +
                    ", innerObjList=" + innerObjList +
                    ", otherObjs=" + Arrays.toString(otherObjs) +
                    '}';
        }
    }

    public class InnerObj {
        String innerDate;


        public String getInnerDate() {
            return innerDate;
        }

        public void setInnerDate(String innerDate) {
            this.innerDate = innerDate;
        }

        @Override
        public String toString() {
            return "InnerObj{" +
                    "innerDate='" + innerDate + '\'' +
                    '}';
        }
    }

    public class OtherObj {
        String otherDate;

        public String getOtherDate() {
            return otherDate;
        }

        public void setOtherDate(String otherDate) {
            this.otherDate = otherDate;
        }

        @Override
        public String toString() {
            return "OtherObj{" +
                    "otherDate='" + otherDate + '\'' +
                    '}';
        }
    }
}