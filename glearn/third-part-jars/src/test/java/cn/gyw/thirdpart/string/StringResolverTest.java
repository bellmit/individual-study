package cn.gyw.thirdpart.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @date 2021/10/29 13:30
 */
class StringResolverTest {

    /**
     * 首尾空格空格
     */
    @Test
    void testBlank() {
        Request request = new Request();
        request.setAddr("       ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("", request.getAddr());

        request.setAddr(null);
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertNull(request.getAddr());

        request.setAddr("");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("", request.getAddr());

        request.setAddr(" 1234");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("1234", request.getAddr());
        request.setAddr("1234 ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("1234", request.getAddr());
        request.setAddr(" 1234 ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("1234", request.getAddr());
        request.setAddr(" 中文测试");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("中文测试", request.getAddr());
        request.setAddr("中文测试 ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("中文测试", request.getAddr());
        request.setAddr(" 中文测试 ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("中文测试", request.getAddr());
    }

    /**
     * 换行符
     */
    @Test
    void trimAndRmLineSeparator() {
        Request request = new Request();
        request.setAddr("hello，中文测试");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，中文测试", request.getAddr());

        request.setAddr("hello，\n" +
                "中文测试\n" +
                "this is test\n" +
                "!!!");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，中文测试this is test!!!", request.getAddr());

        request.setAddr("  \n  hello，\n" +
                "中文测试 \n" +
                " this is test\n" +
                "!!!");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，中文测试  this is test!!!", request.getAddr());

        request.setAddr("  \r  hello，\r" +
                "中文测试 \r" +
                " this is test\r" +
                "!!!");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，中文测试  this is test!!!", request.getAddr());

        request.setAddr("  \r\n  hello，\r\n" +
                "中文测试 \r\n" +
                " this is test\r\n" +
                "!!!   \r\n  ");
        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，中文测试  this is test!!!", request.getAddr());
    }

    /**
     * 内部对象处理
     */
    @Test
    void innerObjectHandler() {
        Request request = new Request();
        request.setAddr(" hello，   中文测试 \r\n  ");
        InnerObj innerObj = new InnerObj();
        innerObj.setInnerAddr(" hello，中文测试1 \r\n  ");
        request.setInnerObj(innerObj);
        List<InnerObj> innerObjList = new ArrayList<>();
        InnerObj innerObj2 = new InnerObj();
        innerObj2.setInnerAddr(" hello，中文测试2 \r\n  ");
        innerObjList.add(innerObj2);
        request.setInnerObjList(innerObjList);

        StringResolver.trimAndRemoveLineSeparator(request, "addr");
        assertEquals("hello，   中文测试", request.getAddr());

        Optional.ofNullable(request.getInnerObj()).ifPresent(obj -> {
            StringResolver.trimAndRemoveLineSeparator(obj, "innerAddr");
        });
        assertEquals("hello，中文测试1", request.getInnerObj().getInnerAddr());

        Optional.ofNullable(request.getInnerObjList()).ifPresent(objList -> {
            objList.forEach(obj -> {
                StringResolver.trimAndRemoveLineSeparator(obj, "innerAddr");
            });
        });
        System.out.println(">>" + Arrays.toString(request.getInnerObjList().toArray(new InnerObj[0])));

        System.out.println(">>" + request);
    }
    public class Request {
        String addr;
        InnerObj innerObj;
        List<InnerObj> innerObjList;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
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

        @Override
        public String toString() {
            return "Request{" +
                    "addr='" + addr + '\'' +
                    ", innerObj=" + innerObj +
                    ", innerObjList=" + Arrays.toString(innerObjList.toArray()) +
                    '}';
        }
    }

    public class InnerObj {
        String innerAddr;

        public String getInnerAddr() {
            return innerAddr;
        }

        public void setInnerAddr(String innerAddr) {
            this.innerAddr = innerAddr;
        }

        @Override
        public String toString() {
            return "InnerObj{" +
                    "innerAddr='" + innerAddr + '\'' +
                    '}';
        }
    }
}