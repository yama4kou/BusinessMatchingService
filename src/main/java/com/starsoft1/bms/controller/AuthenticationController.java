package com.starsoft1.bms.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.starsoft1.bms.dao.LoginDAO;
import com.starsoft1.bms.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

	private LoginDAO LoginDAO;

	public AuthenticationController(LoginDAO loginDAO) {
		this.LoginDAO = loginDAO;
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(HttpServletRequest request, Model model) {
		// フォームから送信されたメールアドレスとパスワードを取得
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// データベースからメールアドレスに対応するユーザーを取得
		UserModel user = LoginDAO.getUserByEmail(email);

		// セッションを取得（なければ新しいセッションを作成）
		HttpSession session = request.getSession();

		// ユーザーが存在し、かつパスワードが一致した場合
		if (user != null && user.getUserPassword().equals(password)) {
			// ユーザーオブジェクトをセッションに保存（ログイン状態を保持）
			session.setAttribute("user", user);
			session.removeAttribute("email"); // ログイン成功時にメールアドレスをセッションスコープから削除

//			// ログインしたユーザーの権限に応じてリダイレクト先を決定
//			String userRole = user.getUserRole();
//			if (userRole.equals("一般")) {
//				// 一般ユーザーの場合、ダッシュボードへリダイレクト
//				return "redirect:/userDashboard";
//			} else if (userRole.equals("管理者")) {
//				// 管理者ユーザーの場合、管理者用ダッシュボードへリダイレクト
//				return "redirect:/adminDashboard";
//			}
		} else {
			// ログインに失敗した場合、エラーメッセージを表示
			session.setAttribute("email", email); // ログイン失敗時にメールアドレスをセッションスコープに保存
			model.addAttribute("error", "メールアドレスまたはパスワードが正しくありません");
		}

		// ログイン画面（login.html）へフォワード
		return "login";
	}
	
	@GetMapping("/")
	 public String judgeDashBoard(HttpServletRequest request) {
	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();

	        Set<String> roles = authentication.getAuthorities().stream()
	             .map(r -> r.getAuthority()).collect(Collectors.toSet());
	        
	        System.out.println(roles);

	        UserModel user = LoginDAO.getUserByEmail(email);

	        // ログイン成功
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user); // セッションにユーザー情報を格納
	        session.removeAttribute("email");
	        if(roles.contains("ROLE_管理者")) {
	         return "redirect:/adminDashboard";
	        } else if(roles.contains("ROLE_承認者")) {
	        	return "redirect:/editorDashboard";
	        }else if(roles.contains("ROLE_ユーザー")) {
	        	return "redirect:/createrDashboard";
	        }
	        return "redirect:/userDashboard";// ダッシュボードページへリダイレクト
	 }

	// ログアウト機能
	@PostMapping("/logout")
	public String postLogout(HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession(false);
		if (session != null) {
			// セッションを無効化して削除
			session.invalidate();
		}
		// ログアウト後にリダイレクトするURLを指定する
		return "redirect:/login";
	}

}
