package cn.gyw.corejava.io.bio;

import java.util.prefs.Preferences;

public class PreferencesDemo {
	public static void main(String[] args) throws Exception {
		Preferences prefs = Preferences.userNodeForPackage(PreferencesDemo.class);
		prefs.put("Location", "Qz");
		prefs.put("Footwear", "Ruby Slippers");
		prefs.putInt("Companions", 4);
		prefs.putBoolean("Are you ? ", true);
		int usageCount = prefs.getInt("UsageCount", 0);
		usageCount++;
		prefs.putInt("UsageCount", usageCount);
		for(String key : prefs.keys())
			System.out.println( key + " : "+prefs.get(key, null));
		System.out.println(prefs.getInt("Companions", 0));
	}
}
