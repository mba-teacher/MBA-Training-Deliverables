<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>サインイン</title>
<link rel="stylesheet" href="../css/login.css" />
</head>

<body>

	<div class="login-area">
		<img src="../img/logo_black.png" class="logo">
		<h3>サインイン</h3>
		<form action="http://localhost:8080/MBoard/login" method="post" name="">
			<p>
				メールアドレス
				<input type="text" name="user" placeholder="メールアドレス" class="mail">
				パスワード
				<input type="password" name="pass" placeholder="パスワード" class="password">
				<!--<span class="alert">パスワードが間違っています。もう一度お試しください。</span>-->
				<input type="submit" name="submit" value="サインイン" class="signin">
				<a href="" class="reset">ID・パスワードを忘れた場合</a>
				</p>
		</form>
	</div>

</body>

</html>