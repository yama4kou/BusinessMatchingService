<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.starsoft1.bms.model.EngineerModel"%>
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
	border-radius: 5px;
}

h1 {
	font-size: 24px;
	text-align: center;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
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

.button-container .engineerSearch-button, .button-container .caseSearch-button,
	.button-container .engineer-button, .button-container .case-button,
	.button-container .edit-button, .button-container .delete-button,
	.button-container .logout-button, .button-container .mail-button {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	color: #fff;
	cursor: pointer;
}

.button-container .engineerSearch-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .caseSearch-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .engineer-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.button-container .case-button {
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
		<h1>ようこそ、${user.userLastName}さん</h1>
		<table>
			<tr>
				<td>会社名</td>
				<td>${user.userCompanyName}</td>
			</tr>
			<tr>
				<td>部署名</td>
				<td>${user.userDepartmentName}</td>
			</tr>
			<tr>
				<td>氏名</td>
				<td>${user.userLastName}${user.userFirstName}</td>
			</tr>
			<tr>
				<td>氏名（かな）</td>
				<td>${user.userLastNameKana}${user.userFirstNameKana}</td>
			</tr>
		</table>

		<div class="button-container">
			<form action="/" method="post">
				<button type="submit" class="engineerSearch-button">エンジニア検索</button>
			</form>
			<form action="/" method="post">
				<button type="submit" class="caseSearch-button">案件検索</button>
			</form>
			<form action="/" method="post">
				<button type="submit" class="engineer-button">エンジニア検索</button>
			</form>
			<form action="/" method="post">
				<button type="submit" class="case-button">案件検索</button>
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

		<div>
			登録したエンジニア一覧
			<table id="engineerTable">
				<tr>
					<th>エンジニアID</th>
					<th>エンジニア名</th>
					<th>所属会社</th>
				</tr>
				<c:forEach var="engineer" items="${engineers}">
					<tr>
						<td><c:out value="${engineer.engineer_id}"></c:out></td>
						<td><c:out value="${engineer.engineer_name}"></c:out></td>
						<td><c:out value="${engineer.engineer_companyName}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
</body>
</html>
