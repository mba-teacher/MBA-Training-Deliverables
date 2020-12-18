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

		<% if(notice != null && notice[0] == "edited") { %>
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
			<a href="<%=request.getContextPath()%>/src/jsp/login_log.jsp">
				<img class="box" src="<%=request.getContextPath()%>/src/img/mb_3_log.png" width="100%" height="100%">
			</a>
		</div>
	</div>

		<div class="modal js-modal">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<p class="js-modal-close">×</p>
				<p>アカウント関連</p>
				<div class="btn_box">
					<a href="<%=request.getContextPath()%>/loginLog">
					<div class="mobal_btn1">アカウント作成</div></a>
				<a href="<%=request.getContextPath()%>/editAccount">
					<div class="mobal_btn2">アカウント修正・削除</div></a>
				</div>
			</div>
			<!--modal__inner-->
		</div>
		<!--modal-->

		<%-- グループ編集とユーザ設定の遷移先は以下のaタグに入れてください（編集終わったらこのコメントは削除してください） --%>
		<div class="modal js-modal2">
			<div class="modal__bg js-modal-close"></div>
			<div class="modal__content">
				<p class="js-modal-close">×</p>
				<p class="modal_title">グループ関連</p>
				<a href="<%=request.getContextPath()%>/#">
					<div class="mobal_btn1">グループ編集</div></a>
				<a href="<%=request.getContextPath()%>/#">
					<div class="mobal_btn2">ユーザー設定</div></a>
			</div>
		</div>



</body>
</html>