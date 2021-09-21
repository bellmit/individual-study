package cn.gyw.corejava.effective.eleventh;

import java.util.concurrent.atomic.AtomicReference;

// 没有 Serializable 的状态类允许子类序列化
public abstract class AbstractFoo {

    private int x, y;

    private enum State {
        NEW, INITIALIZING, INITIALIZED;
    }

    private final AtomicReference<State> init = new AtomicReference<>(State.NEW);

    public AbstractFoo(int x, int y) {
        initialize(x, y);
    }

    protected AbstractFoo() {}
    protected final void initialize(int x, int y) {
        if (!init.compareAndSet(State.NEW, State.INITIALIZING)) {
            throw new IllegalStateException("Already initialized");
        }
        this.x = x;
        this.y = y;
        init.set(State.INITIALIZED);
    }

    protected final int getX() {
        checkInit();
        return x;
    }

    protected final int getY() {
        checkInit();
        return y;
    }

    // must call from all public and protected instance methods
    private void checkInit() {
        if (init.get() != State.INITIALIZED) {
            throw new IllegalStateException("Uninitialized");
        }
    }
}
