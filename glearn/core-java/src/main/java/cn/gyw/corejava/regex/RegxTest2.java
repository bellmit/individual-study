package cn.gyw.corejava.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxTest2 {
	public static void main(String[] args) {
		String txt = readConfig();
        Pattern pattern;
        pattern = Pattern.compile("[^\\{]+\\{[^\\}]*\\}");
        Matcher matcher = pattern.matcher(txt);

        while(matcher.find()){
            System.out.println("#>>" + matcher.group());
        }

	}

	public static String readConfig() {
        String txt = null;
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("model.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            txt = bufferedReader.readLine();
            String line;
            while((line = bufferedReader.readLine()) != null){
				if(line.trim().startsWith("#")) {
					continue;
				}
                txt+="\n"+line;
            }
        } catch (IOException e) {
        }
        return txt;
    }
}
