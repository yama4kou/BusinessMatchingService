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

	@GetMapping("/SearchUser")
	public String userSearch() {

		// go to admin page
		return "user_search";
	}

	@PostMapping("/Search")
	public String userSearchResult(@ModelAttribute("user") UserModel userModel, HttpServletRequest request,
			Model model) {

		String companyName = request.getParameter("companyName");
		String name = request.getParameter("name");
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
		return "user_search";
	}

	@PostMapping("/SearchUserEdit")
	public String userSearchEdit(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		String userId = request.getParameter("userId");

		UserModel user = UserDAO.getUserById(Integer.valueOf(userId));

		model.addAttribute("user", user);
		List<String> roleList = UserDAO.getDisRoles();
		model.addAttribute("roleList", roleList);

		return "searched_user_edit";
	}

	@PostMapping("/SearchUserEditComplete")
	public String userEditComplete(@ModelAttribute("user") UserModel newUser, BindingResult bindingResult, Model model,
			HttpServletRequest request) {

		// エラーチェック
		if (bindingResult.hasErrors()) {
			return "userEditForm";
		}

		UserModel user = UserDAO.getUserById(newUser.getUserId());
		// ユーザー情報をデータベースに保存
		int roleId = UserDAO.getRoleId(newUser.getUserRole());
		newUser.setUserPassword(user.getUserPassword());
		newUser.setUserRegistrationDate(user.getUserRegistrationDate());
		newUser.setUserDeleteFlag(user.getUserDeleteFlag());
		UserDAO.updateUser(newUser, roleId);
		this.userDetailsManager.deleteUser(newUser.getUserEmail());
		UserDetails customer = User.withDefaultPasswordEncoder()
				.username(newUser.getUserEmail())
				.password(newUser.getUserPassword())
				.roles(newUser.getUserRole())
				.build();
		this.userDetailsManager.createUser(customer);

		model.addAttribute("user", newUser);

		return "user_edit_complete";
	}

	@PostMapping("/SearchUserDelete")
	public String searchUserDelete(@ModelAttribute("user") UserModel user, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		user.setUserDeleteFlag(1);
		UserDAO.updateUser(user);
		this.userDetailsManager.deleteUser(user.getUserEmail());
		return "user_delete_complete";
	}

	@PostMapping("/SearchUserDeleteConfirm")
	public String searchUserDeleteConfirm(@ModelAttribute("user") UserModel user, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		model.addAttribute("user", user);
		return "searched_user_delete_confirm";
	}
}