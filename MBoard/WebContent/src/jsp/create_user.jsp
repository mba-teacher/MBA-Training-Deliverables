<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント作成</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/createuser.css" />
</head>
<body>
<section class="mainView">
	<h1>アカウント作成</h1><%-- 画像？文字？ --%>
	<form action="#" method="post" name="Form1" onsubmit="return formCheck()">
		<div class="inputForm">
			<p class="title">ユーザー名</p>
			<input type="text" name="Login_ID" placeholder="ユーザー名" class="inputItem">

			<p class="title">仮パスワード</p>
			<input type="text" name="Login_Pass" placeholder="仮パスワード" class="inputItem">

			<p class="title">メールアドレス</p>
			<input type="text" name="Email_Address" placeholder="メールアドレス" class="inputItem">

			<p class="title">LINE WORKS ID</p>
			<input type="text" name="Line_Works_ID" placeholder="LINE WORKS ID" class="inputItem">

			<%-- 決まってない入力欄(消えるかもしれないし、増えるかもしれない) --%>
			<p class="title">項目名</p>
			<input type="text" name="Sample_Item" placeholder="項目名" class="inputItem">
			<%-- --%>

			<p class="title">管理者権限</p>
			<ul class="chooseAdmin">
				<li>
					<input type="radio" name="Admin" value="true" id="true">
					<label for="true" class="radioLabel">あり</label>
				</li>
				<li>
					<input type="radio" name="Admin" value="false" checked="checked" id="false">
					<label for="false" class="radioLabel">なし</label>
				</li>
			</ul>

			<%-- 未入力項目があった場合 --%>
			<p class="inputerror" id="inputItem1" style="display: none; color: red;">未入力項目があります</p>

			<ul class="button">
				<li><input type="button" name="button" value="キャンセル" id="cancel" class="buttonCustom"></li>
				<li><input type="submit" name="button" value="作成" class="buttonCustom"></li>
			</ul>
		</div>
	</form>
</section>

<%-- モーダルポップアップ --%>
<section class="modal" id="modalArea">
	<div class="modalBg"></div>
	<div class="modalContents">
		<p>編集内容は保持されませんが<br>よろしいですか？</p>
		<ul class="button">
			<li><button id="modalCancel" value="キャンセル">キャンセル</button></li>
			<li><button id="modalOK" value="OK" onclick="location.href='#'">OK</button></li>
		</ul>
	</div>
</section>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/src/js/popup.js"></script>
</body>
</html>