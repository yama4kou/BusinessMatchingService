<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
}

.container {
	max-width: 400px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.container h2,
p {
	text-align: center;
}

.container label {
	display: block;
	margin-top: 10px;
}

.container input[type="text"],
.container input[type="password"] {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.container button {
	width: 100%;
	padding: 10px;
	background-color: #4caf50;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.container button:hover {
	background-color: #45a049;
}

.container .login {
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<h2>ログイン</h2>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<label for="email">メールアドレス:</label>
			<input type="text" id="email" name="email" 
			value="<%= session.getAttribute("email") != null ? session.getAttribute("email") : "" %>" required>
			<label for="password">パスワード:</label>
			<input type="password" id="password" name="password" required>
			
			<p id="error-message" style="color: red"></p>
			
			<c:if test="${not empty param.error}">
				<p style="color: red">メールアドレスまたはパスワードが正しくありません</p>
			</c:if>
			
			<div class="login">
				<button type="submit">ログイン</button>
			</div>
		</form>
		<p>
			<a href="/userRegistrationForm">新規ユーザー登録</a>
		</p>
	</div>
</body>
</html>
