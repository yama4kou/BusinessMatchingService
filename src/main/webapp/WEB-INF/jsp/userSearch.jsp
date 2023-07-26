<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.starsoft1.bms.model.UserModel"%>
<!DOCTYPE html>
<html>
<head>
<title>ユーザー検索</title>
<style>
body {
	background-color: #fff;
}

.container {
	max-Width: 850px;
	margin: 0 auto;
	padding: 20px;
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
	margin-top: 20px;
	margin-bottom: 10px;
}

.button-container .edit-button, .button-container .info-button,
	.button-container .logout-button {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	color: #fff;
	cursor: pointer;
}

.button-container .edit-button {
	background-color: #4caf50;
	margin-right: 10px;
}

.edit-button-user-search {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	color: #fff;
	cursor: pointer;
	background-color: #4caf50;
}

.delete-button-user-search {
	padding: 6px 16px;
	border: none;
	border-radius: 4px;
	color: #fff;
	cursor: pointer;
	background-color: #f44336;
}

.button-container .info-button {
	background-color: #36cef4;
}

.button-container .logout-button {
	background-color: #2196f3;
	margin-right: 10px;
}

.logout-button {
	margin-left: auto; /* Move the logout button to the right */
}

.smallText {
	font-size: 16px;
}

.inline {
	display: inline;
}

td.center {
	text-align: center;
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
		<h1>ユーザー検索</h1>
		<h1>
			<form action="/userSearch" method="post" class="inlineMiddle">
				<div class="input-wrapper inline">
					<span class="smallText" for="companyName">企業名: </span> <input
						path="companyName" id="companyName" name="companyName"
						value="${company}" placeholder="example.dept" />
				</div>

				<div class="input-wrapper inline">
					<span class="smallText" for="name">社員名:</span> <input path="name"
						name="lastName" id="lastName" value="${lastName}" placeholder="姓" />
					<input path="name" name="firstName" id="firstName"
						value="${firstName}" placeholder="名" />
				</div>
				<button type="submit" class="edit-button">検索</button>
			</form>
		</h1>
		<p>
			<a href="${pageContext.request.contextPath}/">マイページ</a>へ戻る
		</p>

		<div>

			<% if (request.getAttribute("users") != null ) { %>
			<% ArrayList<UserModel> users = (ArrayList<UserModel>) request.getAttribute("users");%>
			<table cellspacing="1" cellpadding="4" border="3">
				<tr>
					<TH>ユーザーID</TH>
					<TH>企業名</TH>
					<TH>社員名</TH>
					<TH>権限</TH>
					<TH></TH>
				</tr>
				<% if (users.size() > 0 ) { %>


				<tr></tr>
				<% for (UserModel user : users) { %>
				<tr>

					<td><%= user.getUserId() %></td>
					<td><%= user.getUserCompanyName() %></td>
					<td><%= user.getUserLastName() %> <%= user.getUserFirstName() %></td>
					<td><%= user.getUserRole() %></td>
					<td class="center">
						<form action="/searchedUserEdit" method="Post" class="inline">
							<input type="text" value="<%= user.getUserId() %>" name="userId"
								hidden />
							<button type="submit" class="edit-button-user-search">編集</button>
						</form>
					</td>
					<td class="center">
						<form action="/searchedUserDeleteConfirm" method="Post" class="inline">
							<input type="text" value="<%= user.getUserId() %>" name="userId"
								hidden />
							<button type="submit" class="delete-button-user-search">削除</button>
						</form>
					</td>
				</tr>
				<% } } else { %>
				<td colspan="5" class="center">ユーザーは存在しない</td>
				<% } } %>
			</table>
		</div>
	</div>
</body>
</html>
