package cn.gyw.glearn.design.behaviour.state;

/**
 * 状态接口
 * @author guanyw
 *
 */
public interface State {

	void init(Context context);

	void handle();
}
