package cn.gyw.frameworks.easypoi;

/**
 * 应用入口
 */
public class EasypoiApplication {

    public static void main(String[] args) {
        showDesc();


        CalcService calcService = new CalcService();
        calcService.calc();
    }

    private static void showDesc() {
        System.out.println("参数说明：");
        System.out.println("同目录下新建配置文件[config.properties]");
        System.out.println("原始数据文件配置：excel.input.file");
        System.out.println("输出文件配置：excel.out.path");
        System.out.println("规则配置：rule*={条目}|{数字}");
    }
}
