package cn.gyw.glearn.design.behaviour.interpreter.example1;

// 音乐表达式
public abstract class MusicExpression {

    void interpret(PlayContent content) {
        if (content.getText().length() == 0) {
            return;
        } else {
            String playKey = content.getText().substring(0, 1);
            content.setText(content.getText().substring(2));
            double playValue = Double.parseDouble(content.getText().substring(0, content.getText().indexOf(" ")));
            content.setText(content.getText().substring(content.getText().indexOf(" ") + 1));

            execute(playKey, playValue);
        }
    }

    // 执行，抽象，不同的文法类，有不同的执行处理
    abstract void execute(String playKey, double playValue);
}
