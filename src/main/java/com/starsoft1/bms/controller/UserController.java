package com.starsoft1.bms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.starsoft1.bms.dao.UserDAO;
import com.starsoft1.bms.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private UserDAO UserDAO;

	public UserController(UserDAO UserDAO) {
		this.UserDAO = UserDAO;
	}

	//▼-----Edit関連-----
	@PostMapping("/userEditForm")
	public String postUserEditForm(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		System.out.println(user.getUserId());
		UserModel originUser = com.starsoft1.bms.dao.UserDAO.getUserById(user.getUserId());

//		System.out.println("postのほうね");
//		System.out.println(originUser.getUserCompanyName() + "+" + user.getUserCompanyName());
//		System.out.println(originUser.getUserDepartmentName() + "+" + user.getUserDepartmentName());
//		System.out.println(originUser.getUserLastName() + "+" + user.getUserLastName());
//		System.out.println(originUser.getUserFirstName() + "+" + user.getUserFirstName());
//		System.out.println(originUser.getUserLastNameKana() + "+" + user.getUserLastNameKana());
//		System.out.println(originUser.getUserFirstNameKana() + "+" + user.getUserFirstNameKana());
//		System.out.println(originUser.getUserEmail() + "+" + user.getUserEmail());	

		//edit→editConfirm→edit→dashboardのときの処理
		if (!originUser.getUserCompanyName().equals(user.getUserCompanyName())
				||!originUser.getUserDepartmentName().equals(user.getUserDepartmentName())
				||!originUser.getUserLastName().equals(user.getUserLastName())
				||!originUser.getUserFirstName().equals(user.getUserFirstName())
				||!originUser.getUserLastNameKana().equals(user.getUserLastNameKana())
				||!originUser.getUserFirstNameKana().equals(user.getUserFirstNameKana())
				||!originUser.getUserEmail().equals(user.getUserEmail())
				) {
			System.out.println("userのオブジェクトもどしたよ");
			user = originUser;
		}else {
			System.out.println("userのオブジェクトもどす必要ないよ");
		}

		user.setUserPassword(""); // 初期値を空文字列に設定する
		//		user.setUser_confirmPassword(""); // 初期値を空文字列に設定する

		model.addAttribute("user", user);

		return "userEditForm";
	}

	@GetMapping("/userEditForm")
	public String getUserEditForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user != null) {
			UserModel originUser = com.starsoft1.bms.dao.UserDAO.getUserById(user.getUserId());

			//model.addAttribute("user", user);
			//System.out.println("sessionあるけどuserないからuserとってきたよー");

			System.out.println("getのほうね");
			System.out.println(originUser.getUserCompanyName() + "+" + user.getUserDepartmentName());
			System.out.println(originUser.getUserDepartmentName() + "+" + user.getUserDepartmentName());
			System.out.println(originUser.getUserLastName() + "+" + user.getUserLastName());
			System.out.println(originUser.getUserFirstName() + "+" + user.getUserFirstName());
			System.out.println(originUser.getUserLastNameKana() + "+" + user.getUserLastNameKana());
			System.out.println(originUser.getUserFirstNameKana() + "+" + user.getUserFirstNameKana());
			System.out.println(originUser.getUserEmail() + "+" + user.getUserEmail());

			//			//edit→editConfirm→edit→dashboardのときの処理
			//			if (!originUser.getCompanyName().equals(user.getCompanyName())
			//					||!originUser.getDepartmentName().equals(user.getDepartmentName())
			//					||!originUser.getLastName().equals(user.getLastName())
			//					||!originUser.getFirstName().equals(user.getFirstName())
			//					||!originUser.getLastNameKana().equals(user.getLastNameKana())
			//					||!originUser.getFirstNameKana().equals(user.getFirstNameKana())
			//					||!originUser.getEmail().equals(user.getEmail())
			//					) {
			//				System.out.println("userのオブジェクトもどしたよ");
			//				user = originUser;
			//			}else {
			//				System.out.println("userのオブジェクトもどす必要ないよ");
			//			}

			user.setUserPassword(""); // 初期値を空文字列に設定する
			System.out.println("今のuser.passwordは" + user.getUserPassword());
			//user.setUser_confirmPassword(""); // 初期値を空文字列に設定する

			model.addAttribute("user", user);
			session.setAttribute("user", originUser);

			return "userEditForm";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/userEditConfirmation")
	public String postUserEditConfirmation(@ModelAttribute("user") UserModel newUser, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		System.out.println(user.getUserId());

		//		if (!newUser.getUserPassword().equals(newUser.getUser_confirmPassword())) {
		//			bindingResult.rejectValue("User_confirmPassword", "error.user", "パスワードが一致しません");
		//			return "userEdit";
		//		}

		boolean emailExists = UserDAO.checkEmailExists(newUser.getUserEmail());

		//				UserModel newuser = UserDAO.getUserById(newUser.getUserId());
		//		
		//				if(emailExists) {
		//		
		//					if(newuser == null) {
		//						System.out.println("あるけど退会してるよ");
		//						bindingResult.rejectValue("UserEmail", "error.user", "");
		//						newUser.setUserId(user.getUserId());
		//						session.setAttribute("user", newUser);
		//		
		//						return "userEditConfirmation";
		//					}else{
		//						if(user.getUserEmail().equals(newUser.getUserEmail())) {
		//							System.out.println("本人様だよー");
		//							newUser.setUserId(user.getUserId());
		//							session.setAttribute("user", newUser);
		//							return "userEditConfirmation";
		//						}else {
		//							System.out.println("すでにあるよー");
		//							bindingResult.rejectValue("UserEmail", "error.user", "●既に登録済みのメールアドレスです");
		//		
		//							return "userEditForm";
		//						}
		//					}
		//		
		//				}else {
		//					System.out.println("まだないよー");
		//					bindingResult.rejectValue("UserEmail", "error.user", "");
		//					newUser.setUserId(user.getUserId());
		//					session.setAttribute("user", newUser);
		//					return "userEditConfirmation";
		//				}

		if(emailExists) {
			System.out.println("すでにあるよー");

			//sessionの情報を格納しているuserと入力されたnewUserを比較

			if(user.getUserEmail().equals(newUser.getUserEmail())) {
				System.out.println("本人様だよー");
				newUser.setUserId(user.getUserId());
				session.setAttribute("user", newUser);
				return "userEditConfirmation";
			}else {

				System.out.println(user.getUserEmail() + newUser.getUserEmail());
				bindingResult.rejectValue("UserEmail", "error.user", "●既に登録済みのメールアドレスです");

				return "userEditForm";
			}
		}else {
			System.out.println("登録できるよ");
			bindingResult.rejectValue("UserEmail", "error.user", "");
			session.setAttribute("user", user);

			return "userEditConfirmation";
		}

		//		// エラーチェック
		//		if (bindingResult.hasErrors()) {
		//			return "userEdit";
		//		}

		//		newUser.setUserId(user.getUserId());
		//入力してた情報をsessionにsetする
		//session.setAttribute("user", newUser);
		//		System.out.println(newUser.getUserCompanyName());
		//		System.out.println(newUser.getUserDepartmentName());
		//		System.out.println(newUser.getUserLastName());
		//		System.out.println(newUser.getUserFirstName());
		//		System.out.println(newUser.getUserLastNameKana());
		//		System.out.println(newUser.getUserFirstNameKana());
		//		System.out.println(newUser.getUserEmail());
		//		System.out.println(newUser.getUserPassword());

		//return "userEditConfirmation";

	}

	@GetMapping("/userEditConfirmation")
	public String getUserEditConfirmation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if(user == null){
			return "redirect:/login";
		}else{
			return "redirect:/userEditForm";
		}

	}

	@PostMapping("/userEditComplete")
	public String postUserEditComplete(@ModelAttribute("user") UserModel newUser, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		System.out.println(user.getUserCompanyName());
		System.out.println(user.getUserDepartmentName());
		System.out.println(user.getUserLastName());
		System.out.println(user.getUserFirstName());
		System.out.println(user.getUserLastNameKana());
		System.out.println(user.getUserFirstNameKana());
		System.out.println(user.getUserEmail());
		System.out.println(user.getUserPassword());

		newUser.setUserId(user.getUserId());

		// ユーザー情報をデータベースに保存
		UserDAO.updateUser(user);

		//model.addAttribute("user", newUser);

		//sessionnに登録し直し
		session.setAttribute("user", user);

		user = (UserModel) session.getAttribute("user");

		System.out.println(user.getUserCompanyName());
		System.out.println(user.getUserDepartmentName());
		System.out.println(user.getUserLastName());
		System.out.println(user.getUserFirstName());
		System.out.println(user.getUserLastNameKana());
		System.out.println(user.getUserFirstNameKana());
		System.out.println(user.getUserEmail());
		System.out.println(user.getUserPassword());

		return "userEditComplete";
	}

	@GetMapping("/userEditComplete")
	public String getUserEditComplete(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if(user == null){
			return "redirect:/login";
		}else{
			return "redirect:/userEditForm";
		}

	}

	//▼-----Delete関連-----
	@SuppressWarnings("unused")
	@PostMapping("/userDelete")
	public String postUserDelete(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		System.out.println(user.getUserId());

		if (user == null) {
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			System.out.println("ゆーざーないよー");
		}else {
			System.out.println("ゆーざーあるよー");
		}

		//		user = UserDAO.getUserById(user.getId());	

		System.out.println("ここまでできてるよ");



		if (user != null) {
			System.out.println("ゆーざーあるよー２");
		}



		if (user == null) {
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			return "redirect:/login";
		}

		String p = user.getUserPassword();

		//userEditでpasswordを初期値にした時
		if(p == "") {
			user = com.starsoft1.bms.dao.UserDAO.getUserById(user.getUserId());	
			System.out.println("ここきてないよ");
		}

		model.addAttribute("user", user);

		System.out.println(user.getUserId());
		user.setUserConfirmPassword(""); // 初期値を空文字列に設定する

		return "userDelete";
	}

	@GetMapping("/userDelete")
	public String getUserDelete(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user != null) {
			model.addAttribute("user", user);
			return "userDelete";
		} else {
			return "redirect:/login";
		}
	}

	@SuppressWarnings("unused")
	@PostMapping("/userDeleteConfirmation")
	public String postUserDeleteConfirmation(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		System.out.println("ゆーざーでりーと確認ページきたよ");

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		System.out.println(user.getUserId());

		if (user == null) {
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			System.out.println("ゆーざーないよー");
		}else {
			System.out.println("ゆーざーあるよー");
		}

		System.out.println("ここまでできてるよ２");



		if (user != null) {
			System.out.println("ゆーざーあるよー２");
		}

		model.addAttribute("user", user);

		System.out.println(user.getUserId());
		user.setUserConfirmName(""); // 初期値を空文字列に設定する

		return "userDeleteConfirmation";
	}

	@GetMapping("/userDeleteConfirmation")
	public String getUserDeleteConfirmation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user != null) {
			model.addAttribute("user", user);
			return "redirect:/userDelete";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/userDeleteComplete")
	public String postUserDeleteComplete(@ModelAttribute("user") UserModel passwordConfirm, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		System.out.println("userDeleteCompleteきたよー");

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		String p = user.getUserPassword();

		//userEditでpasswordを初期値にした時
		if(p == "") {
			user = com.starsoft1.bms.dao.UserDAO.getUserById(user.getUserId());	
			System.out.println("ここきてないよ２");
		}

		model.addAttribute("user", user);

		//		if (!user.getUserPassword().equals(passwordConfirm.getUser_confirmPassword())) {
		//			System.out.println(user.getUserPassword());
		//			System.out.println(passwordConfirm.getUser_confirmPassword());
		//			bindingResult.rejectValue("user_confirmPassword", "error.user", "パスワードが一致しません");
		//			return "userDelete";
		//		}
		//
		//		// エラーチェック
		//		if (bindingResult.hasErrors()) {	
		//			return "userDelete";
		//		}

		System.out.println(user.getUserId());
		System.out.println(user.getUserCompanyName());
		System.out.println(user.getUserDepartmentName());
		System.out.println(user.getUserLastName());
		System.out.println(user.getUserFirstName());
		System.out.println(user.getUserLastNameKana());
		System.out.println(user.getUserFirstNameKana());
		System.out.println(user.getUserEmail());
		System.out.println(user.getUserPassword());
		System.out.println(user.getUserRegistrationDate());
		System.out.println(user.getUserRole());
		System.out.println(user.getUserDeleteFlag());
		System.out.println("今から変更するよ");
		user.setUserDeleteFlag(1);
		System.out.println("変更したよ");
		System.out.println(user.getUserDeleteFlag());
		model.addAttribute("user", user);

		// ユーザー削除処理
		//UserDAO.deleteUser(user);
		UserDAO.updateUser(user);
		//sessionを削除処理
		session.invalidate();

		return "userDeleteComplete";
	}

	@GetMapping("/userDeleteComplete")
	public String getUserDeleteComplete(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user != null) {
			model.addAttribute("user", user);
			return "redirect:/userDelete";
		} else {
			return "redirect:/login";
		}
	}

}
