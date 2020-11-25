<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% boolean error = false; %>
<% if(request.getParameter("error") != null) { %>
<% error = true; %>
<% } %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント作成</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/createuser.css" />
</head>
<body>
<section class="inputView">
<h1>アカウント作成</h1><%-- 画像？文字？ --%>
<form action="#" method="post">
	<div class="inputForm">
		<p class="title">ユーザー名</p>
		<input type="text" name="Login_ID">
		<p class="title">仮パスワード</p>
		<input type="text" name="Login_Pass">
		<p class="title">メールアドレス</p>
		<input type="text" name="Email_Address">
		<p class="title">LINE WORKS ID</p>
		<input type="text" name="Line_Works_ID">
		<%-- 決まってない入力欄 --%>
		<p class="title">項目名</p>
		<input type="text" name="Sample_Item">
		<p class="title">管理者権限</p>
	<table class="chooseAdmin">
		<tr>
			<td><input type="radio" name="Admin" value="true">あり</td>
			<td><input type="radio" name="Admin" value="false" checked="checked">なし</td>
		</tr>
	</table>
	<% if (error) { %>
		<p class="inputerror">未入力項目があります</p>
	<% } %>
	<table>
		<tr>
			<td><input type="button" name="button" value="キャンセル" id="cancel"></td>
			<%-- onClick="popupCancel()" --%>
			<td><input type="submit" name="button" value="作成"></td>
		</tr>
	</table>
	</div>
</form>
</section>

<%-- モーダルポップアップ --%>
<section class="modal" id="modalArea">
	<div class="modalBg"></div>
	<div class="modalContents">
		<p>編集は保持されませんがよろしいですか？</p>
		<button id="modalCancel" value="キャンセル">キャンセル</button>
		<button id="modalOK" value="OK">OK</button>
	</div>
</section>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/src/js/popup.js"></script>
</body>
</html>