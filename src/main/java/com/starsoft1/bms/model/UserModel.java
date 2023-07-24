package com.starsoft1.bms.model;

import java.sql.Timestamp;

public class UserModel {
	private int userId;
	private String userCompanyName;
	private String userDepartmentName;
	private String userLastName;
	private String userFirstName;
	private String userLastNameKana;
	private String userFirstNameKana;
	private String userConfirmName;
	private String userEmail;
	private String userPassword;
	private String userConfirmPassword;
	private Timestamp userRegistrationDate;
	private String userRole;
	private int userDeleteFlag;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserCompanyName() {
		return userCompanyName;
	}
	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}
	public String getUserDepartmentName() {
		return userDepartmentName;
	}
	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastNameKana() {
		return userLastNameKana;
	}
	public void setUserLastNameKana(String userLastNameKana) {
		this.userLastNameKana = userLastNameKana;
	}
	public String getUserFirstNameKana() {
		return userFirstNameKana;
	}
	public void setUserFirstNameKana(String userFirstNameKana) {
		this.userFirstNameKana = userFirstNameKana;
	}
	public String getUserConfirmName() {
		return userConfirmName;
	}
	public void setUserConfirmName(String userConfirmName) {
		this.userConfirmName = userConfirmName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		// パスワードがnullの場合にデフォルトの値を返すように修正
		if (userPassword == null) {
			return ""; // または他のデフォルトの値を返す
		}
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserConfirmPassword() {
		return userConfirmPassword;
	}
	public void setUserConfirmPassword(String userConfirmPassword) {
		this.userConfirmPassword = userConfirmPassword;
	}
	public Timestamp getUserRegistrationDate() {
		return userRegistrationDate;
	}
	public void setUserRegistrationDate(Timestamp userRegistrationDate) {
		this.userRegistrationDate = userRegistrationDate;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public int getUserDeleteFlag() {
		return userDeleteFlag;
	}
	public void setUserDeleteFlag(int userDeleteFlag) {
		this.userDeleteFlag = userDeleteFlag;
	}

	
}
