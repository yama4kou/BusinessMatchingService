<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー編集確認</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
}

.container {
	max-width: 850px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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

.container .button {
	margin-top: 10px;
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
		<p>以下の情報で編集しますか？</p>

		<form:form
			action="${pageContext.request.contextPath}/userEditComplete"
			method="post" modelAttribute="user">

			<div class="input-wrapper">
				<label for="User_companyName">会社名:</label>
				<form:input path="UserCompanyName" id="User_companyName"
					readonly="true" value="${user.userCompanyName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_departmentName">所属部署名:</label>
				<form:input path="UserDepartmentName" id="User_departmentName"
					readonly="true" value="${user.userDepartmentName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastName">姓:</label>
				<form:input path="UserLastName" id="User_lastName" readonly="true"
					value="${user.userLastName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_firstName">名:</label>
				<form:input path="UserFirstName" id="User_firstName" readonly="true"
					value="${user.userFirstName}" />
			</div>

			<div class="input-wrapper">
				<label for="User_lastNameKana">姓(フリガナ):</label>
				<form:input path="UserLastNameKana" id="User_lastNameKana"
					readonly="true" value="${user.userLastNameKana}" />
			</div>

			<div class="input-wrapper">
				<label for="User_firstNameKana">名(フリガナ):</label>
				<form:input path="UserFirstNameKana" id="User_firstNameKana"
					readonly="true" value="${user.userFirstNameKana}" />
			</div>

			<div class="input-wrapper-last">
				<label for="User_email">メールアドレス:</label>
				<form:input path="UserEmail" id="User_email" readonly="true"
					value="${user.userEmail}" />
			</div>

			<div class="button">
				<button type="submit">編集</button>
			</div>
		</form:form>
		<p>
			<a href="${pageContext.request.contextPath}/userEditForm">入力画面</a>へ戻る
		</p>
	</div>
</body>
</html>
