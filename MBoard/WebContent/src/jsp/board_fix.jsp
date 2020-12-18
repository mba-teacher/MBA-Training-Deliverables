<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板修正・削除</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/account_fix.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/cansel_modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<a href="" class="backbtn"> 戻る </a>


	<div class="title">
		<h1>掲示板修正・削除</h1>
	</div>



	<div id="realWrite">
		<p></p>
	</div>

	<div id="realText">
		<input type="text" value="" placeholder="　掲示板名検索" class="textbox">
	</div>



	<div class="test">

		<div class="user_name">
			<div class="user_list">
				<P>掲示板</P>
				<a href="javascript:setBoardAndSubmit('1')" class="fix"> 修正 </a>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>日報Aチーム</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>今この掲示板の名は二十文字記入しています</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>MBA全体</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６</P>
				<a href="" class="fix"> 修正 </a> <a href=""
					class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>１２３４５６
			</div>

		</div>
		<form name="sendIdForm" action="<%=request.getContextPath()%>/editBoard" method="post">
			<input type="hidden" name="pageType" value="edit">
			<input type="hidden" name="boardId" value="">
			<!-- <input type="submit" value="修正"> -->
		</form>
	</div>





	<div class="modal js-modal2">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				<br>本当に削除してもよろしいですか？
			</p>
			<div class="btn_box">
				<a href="" class="js-modal-close">キャンセル</a> <a href=""
					class="mobal_btn2">OK</a>
			</div>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->

<script src="<%=request.getContextPath()%>/src/js/account_fix.js"></script>
<script src="<%=request.getContextPath()%>/src/js/account_fix_modal.js"></script>
<script src="<%=request.getContextPath()%>/src/js/method.js"></script>

</body>
</html>