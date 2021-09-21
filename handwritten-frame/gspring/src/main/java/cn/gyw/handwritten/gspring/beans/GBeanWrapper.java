package cn.gyw.handwritten.gspring.beans;

/**
 * BeanWrapper 顶层设计
 */
public class GBeanWrapper {

    Object wrappedObject;

    public GBeanWrapper() {
    }

    public GBeanWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    /**
     * Return the bean instance wrapped by this object.
     */
    public Object getWrappedInstance() {
        return wrappedObject;
    }

    /**
     * Return the type of the wrapped bean instance.
     */
    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }
}
