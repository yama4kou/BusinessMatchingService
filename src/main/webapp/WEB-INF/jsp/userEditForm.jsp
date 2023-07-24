<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー情報編集</title>
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

.container .input-wrapper input[type="text"], .container .input-wrapper input[type="password"]
	{
	flex-basis: 70%;
}

.container .regist {
	margin-top: 10px;
}

.error-message {
	color: red;
	text-align: center;
}
</style>

</head>
<body>
	<div class="container">
		<h2>ユーザー情報編集</h2>
		<p>以下の情報を編集しますか？</p>
		<form:form
			action="${pageContext.request.contextPath}/userEditConfirmation"
			method="post" modelAttribute="user">

			<div class="input-wrapper">
				<label for="User_companyName">会社名:</label>
				<form:input path="UserCompanyName" id="User_companyName"
					required="true" value="${user.userCompanyName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_departmentName">所属部署名:</label>
				<form:input path="UserDepartmentName" id="User_departmentName"
					required="true" value="${user.userDepartmentName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastName">姓:</label>
				<form:input path="UserLastName" id="User_lastName" required="true"
					value="${user.userLastName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_firstName">名:</label>
				<form:input path="UserFirstName" id="User_firstName"
					required="true" value="${user.userFirstName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastNameKana">姓(フリガナ):</label>
				<form:input path="UserLastNameKana" id="User_lastNameKana"
					required="true" value="${user.userLastNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="User_firstNameKana">名(フリガナ):</label>
				<form:input path="UserFirstNameKana" id="User_firstNameKana"
					required="true" value="${user.userFirstNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="User_email">メールアドレス:</label>
				<form:input path="UserEmail" id="User_email" required="true"
					value="${user.userEmail}" />
			</div>

			<div class="input-wrapper">
				<label for="User_password">パスワード:</label>
				<form:input path="UserPassword" id="User_password" type="password"
					required="true" />
			</div>

			<div class="input-wrapper">
				<label for="User_confirmPassword">パスワード確認:</label>
				<form:input path="UserConfirmPassword" id="User_confirmPassword"
					type="password" required="true" />
			</div>

			<div class="input-wrapper">
				<label for="User_registrationDate">登録日:</label>
				<form:input path="UserRegistrationDate" id="User_registrationDate"
					required="true" value="${user.userRegistrationDate}" />
			</div>

			<div class="input-wrapper">
				<label for="User_role">ユーザー権限:</label>
				<form:input path="UserRole" id="User_role" required="true"
					value="${user.userRole}" />
			</div>

			<div class="input-wrapper">
				<label for="User_deleteFlag">退会状況:</label>
				<form:input path="UserDeleteFlag" id="User_deleteFlag"
					required="true" value="${user.userDeleteFlag}" />
			</div>

			<!-- エラーメッセージ表示領域 -->
			<div class="error-message">
				<form:errors path="UserEmail" cssClass="error-message" />
			</div>

			<div class="error-message">
				<span id="errorMessage" class="error-message"></span>
			</div>

			<!-- フォームの送信ボタン -->
			<div class="confirmation">
				<button type="submit">確認</button>
			</div>

			<p>
				<a href="${pageContext.request.contextPath}/">マイページ</a>へ戻る
			</p>

		</form:form>
	</div>
	<script>
		var registrationForm = document.getElementsByTagName("form")[0];
		var lastNameInput = document.getElementById("User_lastName");
		var firstNameInput = document.getElementById("User_firstName");
		var lastNameKanaInput = document.getElementById("User_lastNameKana");
		var firstNameKanaInput = document.getElementById("User_firstNameKana");
		var passwordInput = document.getElementById("User_password");
		var emailInput = document.getElementById("User_email");
		var confirmPasswordInput = document
				.getElementById("User_confirmPassword");

		var errorMessageElement = document.getElementById("errorMessage");
		var errorMessageEmail = document.getElementById("errorMessageEmail");

		registrationForm
				.addEventListener(
						"submit",
						function(event) {
							event.preventDefault();
							let errorEle = document
									.getElementById("User_email.errors");
							if (errorEle !== null) {
								errorEle.innerHTML = "";
							}
							var hiraganaPattern = /^[\u3040-\u309F]+$/;
							var katakanaPattern = /^[\u30A0-\u30FF]+$/;
							var kanjiPattern = /^[\u4E00-\u9FFF]+$/;
							var alphabetPattern = /^[a-zA-Z\s]+$/;
							var nameKanaPattern = /^[\u3040-\u309f|a-zA-Z\s|\u4e00-\u9faf|\u30a0-\u30ff]+$/;
							var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{6,12}$/;
							var emailPattern = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;

							var errorMessage = "";

							if (passwordInput.value.trim() !== confirmPasswordInput.value
									.trim()) {
								errorMessage += "●パスワードが一致しません\n";
							}

							if (!passwordPattern.test(passwordInput.value)) {
								errorMessage += "●パスワードはアルファベットと数字を含み、\n6文字以上12文字以下で入力してください\n";
							}

							if (!emailPattern.test(emailInput.value)) {
								errorMessage += "●メールアドレスを適切に入力してください\n";
							}

							if (!katakanaPattern.test(firstNameKanaInput.value)) {
								errorMessage += "●名（フリガナ）はカタカナで入力してください\n";
							}

							if (!katakanaPattern.test(lastNameKanaInput.value)) {
								errorMessage += "●姓（フリガナ）はカタカナで入力してください\n";
							}

							if (!nameKanaPattern.test(lastNameInput.value)) {
								errorMessage += "●姓には日本語（漢字、ひらがな、カタカナ）\nまたは半角英語を入力してください\n";
							}

							if (!nameKanaPattern.test(firstNameInput.value)) {
								errorMessage += "●名には日本語（漢字、ひらがな、カタカナ）\nまたは半角英語を入力してください\n";
							}

							errorMessageElement.innerText = errorMessage;

							if (errorMessage === "") {
								registrationForm
										.querySelector("button[type='submit']").disabled = true;
								registrationForm.submit();
							} else {
								errorMessageEmail.innerText = "";
							}
						});
	</script>
</body>
</html>
