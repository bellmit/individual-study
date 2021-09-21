package cn.gyw.corejava.effective.seventh;

import java.util.Date;

/**
 * 不可变的周期
 */
public final class Period {

    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        // 保护性拷贝
//        this.start = start;
//        this.end = end;
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("start after end");
        }
    }

}

