package com.starsoft1.bms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.starsoft1.bms.dao.UserDAO;
import com.starsoft1.bms.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;

/*
 * main class
 */
@Controller
public class AdminController {
	@Autowired
	private UserDAO UserDAO;

	@Autowired
	private UserDetailsManager userDetailsManager;

	// constructor no need
	public AdminController(UserDAO UserDAO, UserDetailsManager userDetailsManager ) {
		this.UserDAO = UserDAO;
		this.userDetailsManager = userDetailsManager;
	}

	@GetMapping("/userSearch")
	public String userSearch() {

		// go to admin page
		return "userSearch";
	}

	@PostMapping("/userSearch")
	public String userSearchResult(@ModelAttribute("user") UserModel userModel, HttpServletRequest request,
			Model model) {

		String companyName = request.getParameter("companyName");
		//String name = request.getParameter("name");
		String lastName = "";
		String firstName = "";
		lastName = request.getParameter("lastName");
		firstName = request.getParameter("firstName");
		//        if (StringUtils.isEmpty(companyName)) companyName = "";
		//  if (StringUtils.isEmpty(companyName))
		//   companyName = "";
		//  if (!StringUtils.isEmpty(name)) {
		//   String[] splitStr = name.split("\\s+");
		//   lastName = splitStr[0];
		//   if (splitStr.length > 1)
		//    firstName = splitStr[1];
		//  }
		List<UserModel> users = null;
		users = UserDAO.getUsersByNameAndCompany(companyName, lastName, firstName);

		model.addAttribute("users", users);
		model.addAttribute("company", companyName);
		// model.addAttribute("name", name);
		model.addAttribute("lastName", lastName);
		model.addAttribute("firstName", firstName);
		// go to admin page
		return "userSearch";
	}

	@PostMapping("/searchedUserEdit")
	public String userSearchEdit(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		String userId = request.getParameter("userId");

		UserModel user = com.starsoft1.bms.dao.UserDAO.getUserById(Integer.valueOf(userId));

		model.addAttribute("user", user);
		List<String> roleList = UserDAO.getDisRoles();
		model.addAttribute("roleList", roleList);

		return "searchedUserEdit";
	}

	@PostMapping("/SearchUserEditComplete")
	public String userEditComplete(@ModelAttribute("user") UserModel newUser, BindingResult bindingResult, Model model,
			HttpServletRequest request) {

		// エラーチェック
		if (bindingResult.hasErrors()) {
			return "userEditForm";
		}

		UserModel user = com.starsoft1.bms.dao.UserDAO.getUserById(newUser.getUserId());
		// ユーザー情報をデータベースに保存
		int roleId = UserDAO.getRoleId(newUser.getUserRole());
		newUser.setUserPassword(user.getUserPassword());
		newUser.setUserRegistrationDate(user.getUserRegistrationDate());
		newUser.setUserDeleteFlag(user.getUserDeleteFlag());
		UserDAO.updateUser(newUser, roleId);
		this.userDetailsManager.deleteUser(newUser.getUserEmail());
		@SuppressWarnings("deprecation")
		UserDetails customer = User.withDefaultPasswordEncoder()
				.username(newUser.getUserEmail())
				.password(newUser.getUserPassword())
				.roles(newUser.getUserRole())
				.build();
		this.userDetailsManager.createUser(customer);

		model.addAttribute("user", newUser);

		return "searchedUserEditComplete";
	}

	@PostMapping("/searchedUserDeleteComplete")
	public String searchUserDelete(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		UserModel user = UserDAO.getUserById(userModel.getUserId());
		user.setUserDeleteFlag(1);
		System.out.println("ここまで");
		System.out.println(user.getUserDeleteFlag());
		UserDAO.updateUser(user);
		this.userDetailsManager.deleteUser(user.getUserEmail());
		return "searchedUserDeleteComplete";
	}

	@PostMapping("/searchedUserDeleteConfirm")
	public String searchUserDeleteConfirm(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		model.addAttribute("user", userModel);
		String userId = request.getParameter("userId");
		UserModel user = com.starsoft1.bms.dao.UserDAO.getUserById(Integer.valueOf(userId));
		model.addAttribute("user", user);
		return "searchedUserDeleteConfirm";
	}
}