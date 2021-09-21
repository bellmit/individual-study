package cn.gyw.corejava.io.bio.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

//File 类
public class DirList {
	public static void main(String[] args) {
		File path = new File("E:\\webservice");
		String[] list;
//		if(args.length == 0){
//			System.out.println("1-");
//			list = path.list();
//		}else{
//			System.out.println("2-");
//		}
		list = path.list(new DirFilter("\\w+(.){1}(txt){1}"));
		Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
		for(String dirItem : list)
			System.out.println(dirItem);
	}
}

class DirFilter implements FilenameFilter {
	private Pattern pattern;
	public DirFilter(String regex){
		pattern = Pattern.compile(regex);
	}
	@Override
	public boolean accept(File dir, String name) {
//		System.out.println(dir.getName());
//		System.out.println(name);
//		System.out.println(pattern.matcher(name).matches());
		return pattern.matcher(name).matches();
	}

}
