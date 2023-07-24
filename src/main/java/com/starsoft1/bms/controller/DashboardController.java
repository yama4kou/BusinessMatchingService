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
public class DashboardController {

	public DashboardController(UserDAO UserDAO) {
	}

	//USER,CREATER,EDITOR,ADMIN

	//▼userのマイページ

	@GetMapping("/userDashboard")
	public String getUserDashboard(Model model, HttpServletRequest request) {
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

		return "userDashboard";
	}

	@PostMapping("/userDashboard")
	public String postDashboard() {
		//Postで遷移する処理ないから使いません
		return "userDashboard";
	}

	//▼createrのマイページ

	@GetMapping("/createrDashboard")
	public String getCreaterDashboard(Model model, HttpServletRequest request) {
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

		return "createrDashboard";
	}


	@PostMapping("/createrDashboard")
	public String postCreaterDashboard() {
		//Postで遷移する処理ないから使いません
		return "createrDashboard";
	}

	//▼userのマイページ

	@GetMapping("/editorDashboard")
	public String getEditorDashboard(Model model, HttpServletRequest request) {
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

		return "editorDashboard";
	}


	@PostMapping("/editorDashboard")
	public String postEditorDashboard() {
		//Postで遷移する処理ないから使いません
		return "editorDashboard";
	}

	//▼adminのマイページ

	@GetMapping("/adminDashboard")
	public String getAdminDashboard(Model model, HttpServletRequest request) {
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

		return "adminDashboard";
	}

	@PostMapping("/adminDashboard")
	public String postAdminDashboard() {
		//Postで遷移する処理ないから使いません
		return "/adminDashboard";
	}
	
	//いろんなページからdashboardに戻るときの処理
	@GetMapping("/dashboard")
	public String getBackDashboard(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UserModel user = (UserModel) session.getAttribute("user");
		
		// ログインしたユーザーの権限に応じてリダイレクト先を決定
        String userRole = user.getUserRole();
        if (userRole.equals("一般")) {
            // 一般ユーザーの場合、ダッシュボードへリダイレクト
            return "redirect:/userDashboard";
        } else if(userRole.equals("管理者")) {
            // 管理者ユーザーの場合、管理者用ダッシュボードへリダイレクト
            return "redirect:/adminDashboard";
        }else {
        	return "login";
        }
	}

}
