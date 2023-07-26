<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
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
	max-Width: 400px;
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
	.container button, .User_role {
	width: 70%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.container button {
	width: 45%;
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

.container .confirmation {
	margin-top: 10px;
	text-align: center;
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
			action="${pageContext.request.contextPath}/SearchUserEditComplete"
			method="post" modelAttribute="user">
			<form:input path="userId" hidden="true" required="true"
				value="${user.userId}" />
			<div class="input-wrapper">
				<label for="User_companyName">会社名:</label>
				<form:input path="userCompanyName" id="User_companyName"
					required="true" value="${user.userCompanyName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_departmentName">所属部署名:</label>
				<form:input path="userDepartmentName" id="User_departmentName"
					required="true" value="${user.userDepartmentName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastName">姓:</label>
				<form:input path="userLastName" id="User_lastName" required="true"
					value="${user.userLastName}" />
			</div>

			<div class="input-wrapper">
				<label for="userFirstName">名:</label>
				<form:input path="userFirstName" id="User_firstName" required="true"
					value="${user.userFirstName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastNameKana">姓(フリガナ):</label>
				<form:input path="userLastNameKana" id="User_lastNameKana"
					required="true" value="${user.userLastNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="User_firstNameKana">名(フリガナ):</label>
				<form:input path="userFirstNameKana" id="User_firstNameKana"
					required="true" value="${user.userFirstNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="userEmail">メールアドレス:</label>
				<form:input path="userEmail" id="User_email" required="true"
					value="${user.userEmail}" />
			</div>

			<security:authorize access="hasRole('ROLE_管理者')">
				<div class="input-wrapper">
					<label for="role">権限:</label>

					<form:select name="User_role" id="User_role" class="User_role"
						path="userRole">
						<%
						ArrayList<String> roleList = (ArrayList<String>) request.getAttribute("roleList");
						%>

						<%
						for (String role : roleList) {
						%>
						<form:option value="<%=role%>">
							<%=role%>
						</form:option>
						<%
						}
						%>
					</form:select>
				</div>
			</security:authorize>

			<!-- エラーメッセージ表示領域 -->
			<div class="error-message">
				<form:errors path="userEmail" cssClass="error-message" />
			</div>

			<div class="error-message">
				<span id="errorMessage" class="error-message"></span>
			</div>

			<!-- フォームの送信ボタン -->
			<div class="confirmation">
				<button type="submit">確認</button>
			</div>
			<p>
				<a href="${pageContext.request.contextPath}/userSearch">ユーザー検索</a>へ戻る
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
							var emailPattern = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;

							var errorMessage = "";

							if (!katakanaPattern.test(firstNameKanaInput.value)) {
								errorMessage = "名（フリガナ）はカタカナで入力してください";
							}

							if (!katakanaPattern.test(lastNameKanaInput.value)) {
								errorMessage = "姓（フリガナ）はカタカナで入力してください";
							}

							if (!nameKanaPattern.test(lastNameInput.value)) {
								errorMessage = "姓には日本語（漢字、ひらがな、カタカナ）\nまたは半角英語を入力してください";
							}

							if (!nameKanaPattern.test(firstNameInput.value)) {
								errorMessage = "名には日本語（漢字、ひらがな、カタカナ）\nまたは半角英語を入力してください";
							}

							if (!emailPattern.test(emailInput.value)) {
								errorMessage = "メールのパターンが一致しません";
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
