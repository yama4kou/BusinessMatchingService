<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon"
	href="${pageContext.request.contextPath}/images/logo.png">
<title>ログイン</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #fff;
}

.container {
	text-align: center;
	max-width: 850px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
}

h2 {
	margin: 0px;
}

p {
	margin: 10px;
}

.container .logo {
	text-align: left;
}

.container label {
	display: block;
	margin-top: 10px;
}

.container input[type="text"], .container input[type="password"] {
	width: 80%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.container button {
	width: 80%;
	padding: 6px;
	background-color: #1976D2;
	color: #ffc34a;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.error p {
	margin-bottom: 3px
}

#error-message{
	margin-top: 5px;
}

#message{
	margin: 5px;
}

.login {
	margin-top: 5px;
}

.service {
	border-top: 1px solid #ccc;
}
</style>
</head>
<body>
	<div class="container">
		<div class="logo">
			<header>
				<!-- ヘッダー部分の追加 -->
				<a href="/"> <img
					src="${pageContext.request.contextPath}/images/logo.png" alt="ロゴ"
					width="150" height="50">
				</a>
			</header>
		</div>
		<h2>ビジネスマッチングサービス</h2>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<label for="email"></label> <input type="text" id="email"
				name="email"
				value="<%=session.getAttribute("email") != null ? session.getAttribute("email") : ""%>"
				placeholder="メールアドレス" required> <label for="password"></label>
			<input type="password" id="password" name="password"
				placeholder="パスワード" required>

			<div class="error">
				<p id="error-message" style="color: red"></p>
				<c:if test="${not empty param.error}">
					<p id="message" style="color: red">●メールアドレスまたはパスワードが正しくありません</p>
				</c:if>
			</div>

			<div class="login">
				<button type="submit">ログイン</button>
			</div>
		</form>
		<p>
			アカウントをお持ちでない場合は<a href="/userRegistrationForm">新規ユーザー登録</a>
		</p>
		<div class="service">
			<p>
				ビジネスマッチングサービスをご利用になる場合は<br> <a href="/りよーきやく">利用規約</a>と
				<a href="/ぷらいばしーきやく">プライバシー規約</a>に同意の上ご利用ください
			</p>
		</div>
	</div>
</body>
</html>
