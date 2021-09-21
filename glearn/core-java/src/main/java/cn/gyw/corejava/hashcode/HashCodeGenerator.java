package cn.gyw.corejava.hashcode;

/*
hashCode指导：
1）给int变量result赋予某个非零值常量
2）为对象内每个有意义的域f，即每个可以做equals操作的域计算出一个int散列码c：
boolean > c=(f?0:1);byte,char,short,int > c=(int)f;long > (int)(f^(f>>>32));
float > c=Float.floatToIntBits(f);
double > long l = Double.doubleToLongBits(f),c=(int)(l^(l>>>32));
Object，其equals调用这个域的equals > c=f.hashCode();
数组 > 对每个元素应用上述规则。
3）合并并计算得到的散列码
result=37*result+c;
4) 返回result
5) 检查hashCode最后生成的结果，确保相同的对象有相同的散列码。
 */
public class HashCodeGenerator {
	
	private final int defaultConst = 7;
	
	// 直接定址法
	public int directAddressing(Object value) {
		return value.hashCode() + defaultConst;
	}
	
}
