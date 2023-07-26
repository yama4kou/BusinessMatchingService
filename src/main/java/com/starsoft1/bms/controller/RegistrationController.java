package com.starsoft1.bms.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import jakarta.servlet.http.HttpSession;

@Controller
public class RegistrationController {

	private final UserDAO UserDAO;

	@Autowired
	private UserDetailsManager userDetailsManager;
	
	public RegistrationController(UserDAO UserDAO, UserDetailsManager userDetailsManager) {
		this.UserDAO = UserDAO;
		this.userDetailsManager = userDetailsManager;
	}



	@GetMapping("/userRegistrationForm")
	public String getUserRegistrationForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		String referrer = request.getHeader("referer");

		System.out.println(referrer);
		String login = "http://localhost:8080/login";

		if(referrer.equals(login)){
			session.invalidate();
		}

		session = request.getSession();

		// モデルから「user」という属性を取得します
		UserModel user = (UserModel) model.getAttribute("user");
		UserModel formData = (UserModel) session.getAttribute("user");

		// もし「user」属性がnullの場合は、新しいUserModelオブジェクトを作成します
		if (user == null) {
			user = new UserModel();
		}

		// モデルに「user」属性を追加します
		model.addAttribute("user", user);
		model.addAttribute("formData", formData);


		String p = user.getUserPassword();
		System.out.println(p);

		return "userRegistrationForm"; // 登録フォームのビュー名を返します
	}

	//多分使わない
	@PostMapping("/userRegistrationForm")
	public String postUserRegistrationForm(){
		return "userRegistrationForm";
	}

	@GetMapping("/userRegistrationConfirmation")
	public String getUserRegistrationConfirmation() {
		return "redirect:/userRegistrationForm";
	}

	@PostMapping("/userRegistrationConfirmation")
	public String postUserRegistrationConfirmation(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserModel user = (UserModel) model.getAttribute("user");
		
		String p = userModel.getUserPassword();
		System.out.println("あ");
		System.out.println(p);
		
		//		// パスワードとパスワード確認の一致を検証する
		//		if (!userModel.getUser_password().equals(userModel.getUser_confirmPassword())) {
		//			bindingResult.rejectValue("User_confirmPassword", "error.user", "パスワードが一致しません");
		//			return "registrationForm";
		//		}

		//		// エラーチェック
		//		if (bindingResult.hasErrors()) {
		//			System.out.println("えらーだｙ");
		//			return "registrationForm";
		//		}
		
		//メアドのチェック
		boolean emailExists = UserDAO.checkEmailExists(userModel.getUserEmail());

//		//emailをもとにnewuserに情報を格納
//		UserModel newuser = LoginDAO.getUserByEmail(userModel.getUser_email());
//
//		if(emailExists) {
//			//あるとき
//			if(newuser == null) {
//				System.out.println("あるけど退会してるよ");
//				bindingResult.rejectValue("User_email", "error.user", "");
//				session.setAttribute("user", user);
//
//				return "registrationConfirmation";
//			}else{
//				System.out.println("すでにあるよー");
//				bindingResult.rejectValue("User_email", "error.user", "●既に登録済みのメールアドレスです");
//
//				return "registrationForm";
//			}
//
//		}else {
//			System.out.println("まだないよー");
//			bindingResult.rejectValue("User_email", "error.user", "");
//			session.setAttribute("user", user);
//
//			return "registrationConfirmation";
//		}
		
		if(emailExists) {
			System.out.println("すでにあるよー");
			bindingResult.rejectValue("userEmail", "error.user", "●既に登録済みのメールアドレスです");

			return "userRegistrationForm";
		}else {
			System.out.println("登録できるよ");
			bindingResult.rejectValue("userEmail", "error.user", "");
			session.setAttribute("user", user);

			return "userRegistrationConfirmation";
		}

	}

	@GetMapping("/userRegistrationComplete")
	public String getUserRegistrationComplete() {
		return "redirect:/userRegistrationForm";
	}

	@PostMapping("/userRegistrationComplete")
	public String postUserRegistrationComplete(@ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		LocalDateTime registrationDateTime = LocalDateTime.now();

		// LocalDateTimeをjava.sql.Timestampに変換
		Timestamp sqlRegistrationDate = Timestamp.valueOf(registrationDateTime);

		// 指定したフォーマットで日時を文字列に変換
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = sqlRegistrationDate.toLocalDateTime().format(formatter);

		// UserModelに設定
		user.setUserRegistrationDate(sqlRegistrationDate);

		model.addAttribute("user", user);
		model.addAttribute("formattedDateTime", formattedDateTime);

		System.out.println(formattedDateTime);

		// ユーザー情報をデータベースに保存
		UserDAO.saveUser(user);

		@SuppressWarnings("deprecation")
		UserDetails customer = User.withDefaultPasswordEncoder()
				.username(user.getUserEmail())
				.password(user.getUserPassword())
				.roles("閲覧ユーザー")
				.build();
		this.userDetailsManager.createUser(customer);
		
		System.out.println("登録したよ");

		return "userRegistrationComplete"; // 登録完了画面のビュー名を返します
	}


}