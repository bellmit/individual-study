package cn.gyw.glearn.design.behaviour.interpreter.example1;

/**
 * 解释器模式
 * <p>
 * 例子：
 * O表示音阶，O 1 表示低音阶
 */
public class PlayMusicClient {

    public static void main(String[] args) {
        PlayContent playContent = new PlayContent();
        System.out.println("上海滩...");
        playContent.setText("O 2 E 0.5 G 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 O 3 C 1 O 2 ");
        MusicExpression expression = null;

        while (playContent.getText().length() > 0) {
            String str = playContent.getText().substring(0, 1);
            switch (str) {
                case "O":
                    expression = new Scale(); // 首字母是O，表示音阶
                    break;
                case "C":
                case "D":
                case "E":
                case "F":
                case "G":
                case "A":
                case "B":
                case "P":
                    expression = new Note(); // 音符
                    break;
            }
            expression.interpret(playContent);
        }
    }
}
