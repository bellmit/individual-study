package cn.gyw.glearn.design.behaviour.interpreter.example1;

/**
 * 终端表达式
 * <p>
 * 音符
 */
public class Note extends MusicExpression {
    @Override
    void execute(String playKey, double playValue) {
        String note = "";
        switch (playKey) {
            case "C":
                note = "1";
                break;
            case "D":
                note = "2";
                break;
            case "E":
                note = "3";
                break;
            case "F":
                note = "4";
                break;
            case "G":
                note = "5";
                break;
            case "A":
                note = "6";
                break;
            case "B":
                note = "7";
                break;
        }
        System.out.print(note + " ");
    }
}
