package cn.gyw.corejava.effective.sixth;

/**
 * 接口模拟可伸缩性
 */
public enum RunState implements RunProcesse {

    STARTING {
        @Override
        public void showStatus() {
            System.out.println("start");
        }
    },
    END {
        @Override
        public void showStatus() {
            System.out.println("end");
        }
    },
}
