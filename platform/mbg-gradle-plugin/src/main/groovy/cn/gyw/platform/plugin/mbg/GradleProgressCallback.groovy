package cn.gyw.platform.plugin.mbg

import org.mybatis.generator.internal.NullProgressCallback

class GradleProgressCallback extends NullProgressCallback {

	/**
	 *  是否需要console输出
	 */
	private boolean consoleable

	GradleProgressCallback(boolean consoleable) {
		super
		this.consoleable = consoleable
	}

	@Override
	void startTask(String subTaskName) {
		if (consoleable) {
			println subTaskName
		}
	}
}
