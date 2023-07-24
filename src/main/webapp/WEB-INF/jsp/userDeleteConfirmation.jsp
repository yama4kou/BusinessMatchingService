<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>アカウント削除</title>
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

.container h2, p {
	text-align: center;
}

.container label {
	display: block;
}

.container input[type="password"], .container button {
	width: 70%;
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

.container .input-wrapper {
	display: flex;
	align-items: center;
	margin: 2px;
}

.container .input-wrapper label {
	flex-basis: 30%;
}

.container .input-wrapper input[type="password"] {
	flex-basis: 70%;
}

.container .regist {
	margin-top: 10px;
}

.error-message {
	color: red;
	text-align: center;
}

#User_name {
	display: none;
}
</style>
</head>
<body>
	<div class="container">
		<h2>アカウント削除確認</h2>
		<p>あなたの姓名を入力して下さい</p>

		<form id="userDeleteForm" action="/userDeleteComplete" method="post">

			<div class="input-wrapper">
				<label for="User_name"></label> <span id="User_name"></span>
			</div>

			<div class="input-wrapper">
				<label for="User_confirmName">姓名:</label> <input type="text"
					id="User_confirmName" required="true" />
			</div>

			<div id="error-message" class="error-message"></div>

			<div class="regist">
				<button type="submit">削除</button>
			</div>
		</form>
		<p>
			<a href="${pageContext.request.contextPath}/">マイページ</a>へ戻る
		</p>
	</div>
	<script>
		var nameInput = document.getElementById("User_name");
		var confirmNameInput = document.getElementById("User_confirmName");
		var errorMessage = document.getElementById("error-message");

		nameInput.textContent = '${user.userLastName}${user.userFirstName}';

		document.getElementById("userDeleteForm").addEventListener("submit",
				function(event) {
					event.preventDefault();

					if (nameInput.textContent !== confirmNameInput.value) {
						errorMessage.innerText = "●登録している姓名と違います";
						errorMessage.style.color = "red";
					} else {
						this.submit();
					}
				});
	</script>
</body>
</html>