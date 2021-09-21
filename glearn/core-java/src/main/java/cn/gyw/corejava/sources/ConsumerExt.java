package cn.gyw.corejava.sources;

/**
 * JDK 8 Consumer 接口andThen JDK7 实现
 * 理解其原理
 *
 * Created by guanyw on 2018/9/14.
 */
public interface ConsumerExt<T> {

 	void accept(T t);

	default public ConsumerExt<T> adnThen(ConsumerExt<? super T> after) {
		ConsumerExt<T> consumerExt = this;
		ConsumerExt<T> afterConsumerExt = new ConsumerExt<T>() {

			@Override
			public void accept(T t) {
				consumerExt.accept(t);
				after.accept(t);
			}

		};
		return afterConsumerExt;
	}
}
