package cn.gyw.corejava.annotations;

import java.util.List;

import cn.gyw.corejava.annotations.unit.UseCase;

public class PasswordUtils {

	@UseCase(id=47,description="Passwords must contain at least one number")
	public boolean validatePassword(String password) {
		return (password.matches("\\w*\\d\\w*"));
	}

	@UseCase(id=48)
	public String encryptPassword(String password) {
		return new StringBuilder(password).reverse().toString();
	}

	@UseCase(id=49,description="New passwords can not equal previously used ones")
	public boolean checkForNewPassword(List<String> prevPasswords,String password){
		return !prevPasswords.contains(password);
	}
}
