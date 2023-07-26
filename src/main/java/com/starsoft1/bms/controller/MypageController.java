package com.starsoft1.bms.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.starsoft1.bms.dao.EngineerDAO;
import com.starsoft1.bms.dao.UserDAO;
import com.starsoft1.bms.model.EngineerModel;
import com.starsoft1.bms.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	public MypageController(UserDAO UserDAO) {
	}

	//USER,CREATER,EDITOR,ADMIN

	//▼userのマイページ

	@GetMapping("/userMypage")
	public String getUserMypage(Model model, HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user == null) {
			System.out.println("ログインされてないよー");
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			return "redirect:/login";
		}

		System.out.println(user.getUserRegistrationDate());

		String p = user.getUserPassword();

		// userEditでpasswordを初期値にした時
		if (p.equals("")) {
			user = UserDAO.getUserById(user.getUserId());
			System.out.println("ここきてないよ");
			System.out.println(user.getUserPassword());
		}

		// ログイン済みの場合、ユーザー情報をモデルに追加
		model.addAttribute("user", user);

		System.out.println("ここまできてるよ");

		return "userMypage";
	}

	@PostMapping("/userMypage")
	public String postMypage() {
		//Postで遷移する処理ないから使いません
		return "userMypage";
	}

	//▼createrのマイページ

	@GetMapping("/createrMypage")
	public String getCreaterMypage(Model model, HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user == null) {
			System.out.println("ログインされてないよー");
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			return "redirect:/login";
		}

		List<EngineerModel> engineers = EngineerDAO.getEngineersByUserId(user.getUserId());
		//		エンジニア情報の処理
		//		for (EngineerModel engineer : engineers) {
		//			//System.out.println(engineer.getEngineer_name());
		//			// エンジニア情報の追加処理を行う（例えば、エンジニア情報を別のリストに追加するなど）
		//		}

		System.out.println(user.getUserRegistrationDate());

		String p = user.getUserPassword();

		// userEditでpasswordを初期値にした時
		if (p.equals("")) {
			user = UserDAO.getUserById(user.getUserId());
			System.out.println("ここきてないよ");
			System.out.println(user.getUserPassword());
		}


		// ログイン済みの場合、ユーザー情報とエンジニア情報をモデルに追加
		model.addAttribute("user", user);
		model.addAttribute("engineers", engineers); // エンジニア情報をリクエストの属性として設定

		System.out.println("ここまできてるよ");

		return "createrMypage";
	}


	@PostMapping("/createrMypage")
	public String postCreaterMypage() {
		//Postで遷移する処理ないから使いません
		return "createrMypage";
	}

	//▼userのマイページ

	@GetMapping("/editorMypage")
	public String getEditorMypage(Model model, HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user == null) {
			System.out.println("ログインされてないよー");
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			return "redirect:/login";
		}

		System.out.println(user.getUserRegistrationDate());

		String p = user.getUserPassword();

		// userEditでpasswordを初期値にした時
		if (p.equals("")) {
			user = UserDAO.getUserById(user.getUserId());
			System.out.println("ここきてないよ");
			System.out.println(user.getUserPassword());
		}


		// ログイン済みの場合、ユーザー情報をモデルに追加
		model.addAttribute("user", user);

		System.out.println("ここまできてるよ");

		return "editorMypage";
	}


	@PostMapping("/editorMypage")
	public String postEditorMypage() {
		//Postで遷移する処理ないから使いません
		return "editorMypage";
	}

	//▼adminのマイページ

	@GetMapping("/adminMypage")
	public String getAdminMypage(Model model, HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");

		if (user == null) {
			System.out.println("ログインされてないよー");
			// ログインしていない場合の処理を記述（例: ログインページにリダイレクト）
			return "redirect:/login";
		}

		System.out.println(user.getUserRegistrationDate());

		String p = user.getUserPassword();

		//userEditでpasswordを初期値にした時
		if(p == "") {
			//userオブジェクトの生成しなおし
			user = UserDAO.getUserById(user.getUserId());	
			System.out.println("ここきてないよ");
			System.out.println(user.getUserPassword());
		}

		// ログイン済みの場合、ユーザー情報をモデルに追加
		model.addAttribute("user", user);

		return "adminMypage";
	}

	@PostMapping("/adminMypage")
	public String postAdminMypage() {
		//Postで遷移する処理ないから使いません
		return "/adminMypage";
	}

}
