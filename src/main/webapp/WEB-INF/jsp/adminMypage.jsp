<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>ダッシュボード</title>
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

h1 {
	font-size: 24px;
	text-align: center;
	margin: 0px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

td {
	padding: 8px;
	border-bottom: 1px solid #ccc;
}

th {
	text-align: left;
}

.button-container {
	display: flex;
	align-items: center;
	justify-content: flex-end;
	margin-top: 10px;
	margin-bottom: 10px;
}

.button-container .userSearch-button, .button-container .edit-button,
	.button-container .delete-button, .button-container .logout-button,
	.button-container .mail-button {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	color: #fff;
	cursor: pointer;
}

.button-container .userSearch-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .mail-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .edit-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .delete-button {
	background-color: #E53935;
	margin-right: 10px;
}

.button-container .logout-button {
	background-color: #1976D2;
}

.logout-button {
	margin-left: auto; /* Move the logout button to the right */
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
		<h1>ようこそ、${user.userLastName}さん</h1>
		<table>
			<tr>
				<td>会社名</td>
				<td>：${user.userCompanyName}</td>
			</tr>
			<tr>
				<td>部署名</td>
				<td>：${user.userDepartmentName}</td>
			</tr>
			<tr>
				<td>氏名</td>
				<td>：${user.userLastName}${user.userFirstName}</td>
			</tr>
		</table>

		<div class="button-container">
			<form action="/userSearch" method="get">
				<button type="submit" class="userSearch-button">ユーザーの検索</button>
			</form>
			<form action="/mailBox" method="post">
				<button type="submit" class="mail-button">メールボックス</button>
			</form>
			<form action="/userEditForm" method="post">
				<button type="submit" class="edit-button">編集</button>
			</form>
			<form action="/userDelete" method="post">
				<button type="submit" class="delete-button">削除</button>
			</form>
			<form action="/logout" method="post">
				<button type="submit" class="logout-button">ログアウト</button>
			</form>
		</div>

	</div>
</body>
</html>
