<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面本体</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/admin_top.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../js/admin_top.js"></script>
<script src="../js/modal.js"></script>
</head>
<body>
	<div class="main">

		<header class="alarm">アカウント/掲示板/グループ を更新しました。</header>

		<div class="left">
			<img src="<%=request.getContextPath()%>/src/img/mb_3_Administratorillust.png" width="140px"
				height="160px">
		</div>

		<div class="back">
			<a href="" class="backbtn"> 戻る </a>
		</div>


		<div class="center">
			<a class="js-modal-open" href="">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_account.png" width="100%" height="100%">
			</a>
			<a href="<%=request.getContextPath()%>/src/jsp/board_fix.jsp">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_board.png" width="100%" height="100%">
			</a>
			<a class="js-modal-open2" href="">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_group.png" width="100%" height="100%">
			</a>
			<a href="">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_log.png" width="100%" height="100%">
			</a>
		</div>



		<div class="modal js-modal">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<a class="js-modal-close" href="">×</a>
				<p>アカウント関連</p>
				<div class="btn_box">
					<a href="">
					<div class="mobal_btn1">アカウント作成</div></a>
				<a href="">
					<div class="mobal_btn2">アカウント修正・削除</div></a>
				</div>
			</div>
			<!--modal__inner-->
		</div>
		<!--modal-->



		<div class="modal js-modal2">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<a class="js-modal-close" href="">×</a>
				<p class="modal_title">グループ関連</p>
				<a href="">
					<div class="mobal_btn1">グループ編集</div></a>
				<a href="">
				<div class="mobal_btn2">ユーザー設定</div></a>
			</div>
		</div>



	</div>
</body>
</html>