<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインログ確認</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/account_fix.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<% ArrayList<UserInfoBean> uibs = (ArrayList<UserInfoBean>)request.getAttribute("membersInfoBean"); %>
	<a href="<%=request.getContextPath()%>/src/jsp/admin_top.jsp" class="backbtn"> 戻る </a>

	<div class="title">
		<h1>ログインログ確認</h1>
	</div>


	<div id="realWrite">
		<p></p>
	</div>

	<div id="realText">
		<input type="text" value="" placeholder="　ユーザー名検索" class="textbox" id="search-text">
	</div>


	<div class="test">

		<div class="user_name">
			<div class="user_list_header">
				<p>ユーザー<span id="log_title">ログイン日時</span></p>
				<input type="button" class="all_btn" value="全登録者表示" onclick="ReDisplay()">
			</div>

			<div class="search-result" id="search-result">
				<div id="search-result__list"></div>

				<div id="noResult">
					<p id="none">該当しませんでした</p>
				</div>
			</div>

			<div class="ul" id="target-area">
			<ul>
				<% for (int i = 0; i < uibs.size(); i++) { %>
				<li class="user_list">
					<p><%=uibs.get(i).getUserName() %></p>
					<div class="log"><%=uibs.get(i).getLoginLog()%></div>
				</li>
				<% } %>
			</ul>
			</div>
		</div>
	</div>

<script src="<%=request.getContextPath()%>/src/js/search.js"></script>
</body>
</html>