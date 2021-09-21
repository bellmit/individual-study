package cn.gyw.glearn.design.behaviour.interpreter.example1;

/**
 * 音符类
 */
public class Scale extends MusicExpression {
    @Override
    void execute(String playKey, double playValue) {
        String scale = "";
        switch ((int)playValue) {
            case 1:
                scale = "低音";
                break;
            case 2:
                scale = "中音";
                break;
            case 3:
                scale = "高音";
                break;
        }
        System.out.print(scale + " ");
    }
}
