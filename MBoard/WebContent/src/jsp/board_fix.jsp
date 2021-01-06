<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="data.BoardInfoBean,java.util.ArrayList"%>
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
<% ArrayList<BoardInfoBean> bibs = (ArrayList<BoardInfoBean>)session.getAttribute("boardsInfoBean"); %>
<% int count = 0; %>

	<a href="<%=request.getContextPath()%>/src/jsp/admin_top.jsp" class="backbtn"> 戻る </a>

	<div class="title">
		<h1>掲示板修正・削除</h1>
	</div>

	<div id="realWrite">
		<p></p>
	</div>

	<div id="realText">
		<input type="text" value="" placeholder="　掲示板名検索" class="textbox" id="search-text">
	</div>


	<div class="test">

		<div class="user_name">
			<div class="user_list_header">
				<p>掲示板</p>
			</div>

			<div class="search-result" id="search-result">
				<div id="search-result__list"></div>

				<div id="noResult">
					<p id="none">該当しませんでした</p>
				</div>
			</div>

			<div class="ul" id="target-area">
			<ul>
				<% for (int i = 0; i < bibs.size(); i++) { %>
				<li class="user_list">
					<p class="board"><%=bibs.get(i).getBoardCategory()%></p>
					<input type="button" class="fix" value="修正" onclick="setBoardAndSubmit('<%=bibs.get(i).getBoardId()%>','sendIdForm')">
					<input type="button" class="delete js-modal-open2" value="削除" onclick="setBoard('<%=bibs.get(i).getBoardId()%>','deleteForm')">
				</li>

				<% } %>
			</ul>
			<!-- class="ul" -->
			</div>
		</div>
	</div>

	<form id="sendIdForm" action="<%=request.getContextPath()%>/editBoard" method="post">
		<input type="hidden" name="pageType" value="edit">
		<input type="hidden" name="boardId" value="">
		<!-- <input type="submit" value="修正"> -->
	</form>


	<div class="modal js-modal2">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				<br>本当に削除してもよろしいですか？
			</p>
			<form id="deleteForm" action="<%=request.getContextPath()%>/boardfix" method="post">
				<input type="hidden" name="boardId" value="">
				<input type="hidden" name="sendType" value="delete">
				<div class="btn_box">
					<input type="button" class="js-modal-close" value="キャンセル">
					<input type="submit" class="mobal_btn2" value="OK">
				</div>
			</form>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->

<script src="<%=request.getContextPath()%>/src/js/board_fix.js"></script>
<script src="<%=request.getContextPath()%>/src/js/account_fix_modal.js"></script>
<script src="<%=request.getContextPath()%>/src/js/method.js"></script>

</body>
</html>