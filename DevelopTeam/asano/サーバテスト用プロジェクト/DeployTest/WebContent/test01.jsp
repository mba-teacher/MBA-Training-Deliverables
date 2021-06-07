<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>デプロイテスト</title>
</head>
<body>
	<h1>テスト文面</h1>
	<form action="transtest" method="post">
		<input type="hidden" name="hidden-item" value="01">
		<p>遷移するボタン</p>
		<input type="submit" value="GO" name="button">
	</form>
</body>
</html>