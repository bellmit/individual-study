package cn.gyw.platform.crawler.model;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;

import cn.gyw.platform.crawler.jna.WindowsUtils;

public class ExPointer {

    private Pointer pointer;
    private HANDLE handle;

    public ExPointer() {
    }

    public ExPointer(HANDLE handle, int address) {
        this.handle = handle;
        pointer = Pointer.createConstant(address);
    }

    public ExPointer(Pointer pointer, HANDLE handle) {
        this.pointer = pointer;
        this.handle = handle;
    }

    public static ExPointer of(HANDLE handle, int address) {
        return new ExPointer(handle, address);
    }

    public ExPointer offset(int offset) {
        int value = WindowsUtils.readIntOfExPointer(this);
        int newAddress = value + offset;
        return new ExPointer(handle, newAddress);
    }


    /**
     * 获取指针的地址
     * @return  地址
     */
    public int getAddress() {
        return (int) Pointer.nativeValue(pointer);
    }

    /**
     * 读取指针指向的值
     * @return  int
     */
    public int readInt() {
        return WindowsUtils.readIntOfExPointer(this);
    }

    public void writeInt(int value) {
        WindowsUtils.writeValueToExPointer(this, value);
    }


    public double readDouble() {
        return WindowsUtils.readDoubleOfExPointer(this);
    }

	public Pointer getPointer() {
		return pointer;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public HANDLE getHandle() {
		return handle;
	}

	public void setHandle(HANDLE handle) {
		this.handle = handle;
	}

}