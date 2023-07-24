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

.container input[type="password"],
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

</style>
</head>
<body>
	<div class="container">
		<h2>アカウント削除</h2>
		<p>パスワードを入力して下さい</p>

		<form:form id="userDeleteForm"
			action="${pageContext.request.contextPath}/userDeleteConfirmation"
			method="post" modelAttribute="user">
			
			<div class="input-wrapper">
				<label for="User_password">パスワード:</label>
				<form:input path="UserPassword" id="User_password" type="password" required="true" 
					readonly="true" value="${user.userPassword}" />
			</div>
			
			<div class="input-wrapper">
				<label for="User_confirmPassword">パスワード確認:</label>
				<form:input path="UserConfirmPassword" id="User_confirmPassword"
					type="password" required="true" />
			</div>
			
			<div id="error-message" class="error-message">
				<form:errors path="UserConfirmPassword" cssClass="error-message" />
			</div>
			
			<div class="regist">
				<button type="submit">削除確認</button>
			</div>
		</form:form>
		<p>
			<a href="${pageContext.request.contextPath}/">マイページ</a>へ戻る
		</p>
	</div>
<script>
var deleteForm = document.getElementById("userDeleteForm");
var passwordInput = document.getElementById("User_password");
var confirmPasswordInput = document.getElementById("User_confirmPassword");
var errorMessage = document.getElementsByClassName("error-message")[0];


deleteForm.addEventListener("submit", function(event) {
	event.preventDefault(); 

	if (passwordInput.value !== confirmPasswordInput.value) {
		errorMessage.innerText = "●パスワードが一致しません";
		errorMessage.style.color = "red";
	} else {
		deleteForm.submit(); 
	}
});
</script>
</body>
</html>