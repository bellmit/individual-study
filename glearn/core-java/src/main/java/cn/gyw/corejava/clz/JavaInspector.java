package cn.gyw.corejava.clz;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guanyw on 2017/9/12.
 * java 内省机制
 */
public class JavaInspector {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "gyw");
        params.put("aPoint", "上海");
        params.put("zPoint", "南京");
        Bean bean = new JavaInspector.Bean();
        try {
            //1.获取JavaBean的描述信息
            BeanInfo info = Introspector.getBeanInfo(Bean.class);
            //2.获取JavaBean的属性信息
            PropertyDescriptor[] prop = info.getPropertyDescriptors();
            //3.遍历属性信息
            for (PropertyDescriptor pd : prop) {
                String value = params.get(pd.getName());
                pd.getWriteMethod().invoke(bean, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bean);
    }

    static class Bean {
        private int id;
        private String name;
        private String aPoint;
        private String zPoint;
        private String tStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getaPoint() {
            return aPoint;
        }

        public void setaPoint(String aPoint) {
            this.aPoint = aPoint;
        }

        public String getzPoint() {
            return zPoint;
        }

        public void setzPoint(String zPoint) {
            this.zPoint = zPoint;
        }

        public String gettStatus() {
            return tStatus;
        }

        public void settStatus(String tStatus) {
            this.tStatus = tStatus;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", aPoint='" + aPoint + '\'' +
                    ", zPoint='" + zPoint + '\'' +
                    ", tStatus='" + tStatus + '\'' +
                    '}';
        }
    }
}
