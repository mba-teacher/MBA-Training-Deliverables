<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント作成</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/create_user.css" />
</head>
<body>

	<div class="header_title">
		<h1>アカウント作成</h1>
	</div>

<section class="mainView">
	<form action="<%=request.getContextPath()%>/createaccount" method="post" name="Form1" onsubmit="return formCheck()">

		<div class="inputForm">
			<!-- バリデーションチェックはJSで実装中 -->
			<p class="title">ユーザー名<span class="requierdItem">*</span><span class="alertarea"></span>
			<input type="text" name="Login_ID" placeholder="例：mba-taro" maxlength="25" class="id">
			<!-- 簡易で 25文字以内 に設定 -->
			</p>

			<p class="title">仮パスワード<span class="requierdItem">*</span><span class="alertarea"></span>
			<input type="text" name="Login_Pass" placeholder="例：123abcDE" maxlength="25" class="password">
			<!-- 簡易で 25文字以内 に設定 -->
			</p>

			<p class="title">メールアドレス<span class="requierdItem">*</span><span class="alertarea"></span>
			<input type="text" name="Email_Address" placeholder="例：taro_mba@mba-international.jp" class="email">
			</p>

			<p class="title">LINE WORKS ID<span class="requierdItem">*</span><span class="alertarea"></span>
			<input type="text" name="Line_Works_ID" placeholder="例：mba_taro" maxlength="25" class="id">
			<!-- 簡易で 25文字以内 に設定 -->
			</p>

			<%-- 追加の入力欄があればここにどうぞ --%>
			<%-- --%>

			<p class="title">管理者権限<span class="requierdItem">*</span></p>
			<ul class="chooseAdmin">
				<li>
					<input type="radio" name="Admin" value="1" id="true">
					<label for="true" class="radioLabel">あり</label>
				</li>
				<li>
					<input type="radio" name="Admin" value="0" checked="checked" id="false">
					<label for="false" class="radioLabel">なし</label>
				</li>
			</ul>

			<%-- 未入力項目があった場合 --%>
			<p class="inputerror" id="errortext" style="display: none; color: red;">未入力項目があります</p>

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
			<li><button id="modalOK" value="OK" onclick="location.href='<%=request.getContextPath()%>/src/jsp/admin_top.jsp'">OK</button></li>
		</ul>
	</div>
</section>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/src/js/popup.js"></script>
</body>
</html>