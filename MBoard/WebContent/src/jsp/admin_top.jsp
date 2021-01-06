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
<script src="<%=request.getContextPath()%>/src/js/admin_top.js"></script>
<script src="<%=request.getContextPath()%>/src/js/modal.js"></script>
</head>
<body>
<% String[] notice = (String[])request.getAttribute("notice");%>

	<div class="main">

		<% if(notice != null && notice[0].equals("edited")) { %>
			<header class="alarm"><span class="blank"><%=notice[1]%></span> を更新しました。</header>
			<%-- ↑'notice[1]'に「アカウント」/「掲示板」/「グループ」のどれかを代入する --%>
		<% } %>

		<div class="left">
			<img src="<%=request.getContextPath()%>/src/img/mb_3_Administratorillust.png" width="140px"
				height="160px">
		</div>

		<div class="back">
			<a href="<%=request.getContextPath()%>/src/jsp/my_page.jsp" class="backbtn"> 戻る </a>
		</div>

		<%-- aタグを使わないように一時的に作成したフォームです --%>
		<form action="" method="post" name="centerForm">
		<div class="center">
			<a class="js-modal-open" id="">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_account.png" width="100%" height="100%">
			</a>
			<%-- <a href="<%=request.getContextPath()%>/boardfix">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_board.png" width="100%" height="100%">
			</a> --%>
			<input type="image" src="<%=request.getContextPath()%>/src/img/mb_3_board.png" class="box" formaction="<%=request.getContextPath()%>/boardfix">
			<a class="js-modal-open2" id="">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_group.png" width="100%" height="100%">
			</a>
			<a href="<%=request.getContextPath()%>/loginLog">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_log.png" width="100%" height="100%">
			</a>
		</div>
		</form>
	</div>

	<form action="" method="post">

		<div class="modal js-modal">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<p class="js-modal-close">×</p>
				<p>アカウント関連</p>
				<div class="btn_box">
					<input type="submit" class="mobal_btn1" formaction="<%=request.getContextPath()%>/src/jsp/create_user.jsp" value="アカウント作成">
					<input type="submit" class="mobal_btn2" formaction="<%=request.getContextPath()%>/editAccount" value="アカウント修正・削除">
				</div>
			</div>
			<!--modal__inner-->
		</div>
		<!--modal-->



		<div class="modal js-modal2">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<p class="js-modal-close">×</p>
				<p class="modal_title">グループ関連</p>
				<div class="btn_box">
					<input type="submit" class="mobal_btn1" formaction="<%=request.getContextPath()%>/GroupEdit" name="goto" value="グループ編集">
					<!-- <div class="mobal_btn1">グループ編集</div></a> -->
					<input type="submit" class="mobal_btn2" formaction="<%=request.getContextPath()%>/AdminUserconfig" name="goto" value="ユーザー設定">
					<!-- <div class="mobal_btn2">ユーザー設定</div></a> -->
				</div>
			</div>
		</div>

	</form>

	<%-- 一時的に作成するフォームです --%>
	<form id="" action="" method="post">
		<input type="hidden">
	</form>

</body>
</html>