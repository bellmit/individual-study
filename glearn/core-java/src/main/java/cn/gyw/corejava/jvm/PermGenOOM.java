package cn.gyw.corejava.jvm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 永久代内存溢出
 * 1.7
 * 永久代设置：
 * -XX:PermSize=50M -XX:MaxPermSize=50M
 *
 * 1.8
 * 元空间设置（本地内存）：
 * -XX:MetaspaceSize=10M
 *
 * Created by guanyw on 2019/1/18.
 */
public class PermGenOOM {

    public static void main(String[] args) {
        URL url = null;
        List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
        try {
        	// TODO: 非指定目录
            url = new File("E:\\Workspace_My\\Workspace_idea\\GTools\\target\\classes\\com\\gyw\\jvm").toURI().toURL();
            URL[] urls = {url};
            while (true){
                ClassLoader loader = new URLClassLoader(urls);
                classLoaderList.add(loader);
                loader.loadClass("com.gtools.jvm.PermGenOOM");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
