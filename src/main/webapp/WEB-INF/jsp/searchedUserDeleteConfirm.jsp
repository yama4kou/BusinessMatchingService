<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
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
	background-color: #f44336;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
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
</style>
</head>
<body>
	<div class="container">
		<h2>ユーザー情報編集</h2>
		<p>以下の情報を削除しますか？</p>
		<form:form
			action="${pageContext.request.contextPath}/searchedUserDeleteComplete"
			method="post" modelAttribute="user">
			<div class="input-wrapper">
				<label for="companyName">会社名:</label>
				<form:input path="userCompanyName" id="companyName" required="true"
					value="${user.userCompanyName}" />
			</div>

			<div class="input-wrapper">
				<label for="departmentName">所属部署名:</label>
				<form:input path="userDepartmentName" id="departmentName"
					required="true" value="${user.userDepartmentName}" />
			</div>

			<div class="input-wrapper">
				<label for="lastName">姓:</label>
				<form:input path="userLastName" id="lastName" required="true"
					value="${user.userLastName}" />
			</div>

			<div class="input-wrapper">
				<label for="firstName">名:</label>
				<form:input path="userFirstName" id="firstName" required="true"
					value="${user.userFirstName}" />
			</div>

			<div class="input-wrapper">
				<label for="lastNameKana">姓(フリガナ):</label>
				<form:input path="userLastNameKana" id="lastNameKana" required="true"
					value="${user.userLastNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="firstNameKana">名(フリガナ):</label>
				<form:input path="userFirstNameKana" id="firstNameKana" required="true"
					value="${user.userFirstNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="email">メールアドレス:</label>
				<form:input path="userEmail" id="email" required="true"
					value="${user.userEmail}" />
			</div>

			<div class="error-message">
				<form:errors path="userConfirmPassword" />
			</div>
			<form:input path="userId" id="id" required="true" hidden="true"
				value="${user.userId}" />
			<button class="delete" type="submit">削除確認</button>
		</form:form>

		<p>
			<a href="${pageContext.request.contextPath}/">マイページ</a>へ戻る
		</p>
	</div>
</body>
</html>
