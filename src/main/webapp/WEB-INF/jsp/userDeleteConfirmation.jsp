<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>アカウント削除</title>
<style>
body {
	background-color: #fff;
}

.container {
	max-width: 850px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border: 1px solid #fff;
	border-radius: 5px;
}

.container h2 {
	text-align: center;
	margin: 2px;
	margin-bottom: 5px;
}

.container p {
	text-align: center;
	margin: 5px;
}

.container label {
	display: block;
}

.container input[type="text"], .container input[type="password"],
	.container button {
	width: 70%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.container button {
	width: 100%;
	padding: 6px;
	background-color: #1976D2;
	color: #ffc34a;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	margin-top: 10px;
}

.container .input-wrapper {
	display: flex;
	align-items: center;
	margin: 2px;
	padding-top: 2px;
	border-top: 1px solid #ccc;
}

.container .input-wrapper-last {
	display: flex;
	align-items: center;
	margin: 2px;
	padding-top: 2px;
	padding-bottom: 2px;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.container .input-wrapper label, .container .input-wrapper-last label {
	flex-basis: 30%;
}

.container .input-wrapper input[type="text"], .container .input-wrapper input[type="password"]
	{
	flex-basis: 70%;
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
		<div class="logo">
			<header>
				<!-- ヘッダー部分の追加 -->
				<a href="/"> <img
					src="${pageContext.request.contextPath}/images/logo.png" alt="ロゴ"
					width="150" height="50">
				</a>
			</header>
		</div>
		<h2>アカウント削除確認</h2>
		<p>あなたの姓名を入力して下さい</p>

		<form id="userDeleteForm" action="/userDeleteComplete" method="post">

			<label for="User_name"></label> <span id="User_name"></span>

			<div class="input-wrapper-last">
				<label for="User_confirmName">姓名:</label> <input type="text"
					id="User_confirmName" required="true" />
			</div>

			<div id="error-message" class="error-message"></div>

			<div class="button">
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