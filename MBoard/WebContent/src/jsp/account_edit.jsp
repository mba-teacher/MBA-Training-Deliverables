<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント修正・削除</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/account_fix.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/src/js/account_fix.js"></script>
</head>
<body>
<% int memberId = Integer.parseInt(request.getParameter("memberId")); %>
<% String memberName = request.getParameter("memberName" + memberId); %>
<% String admin = request.getParameter("isAdmin" + memberId); %>

<a href="<%=request.getContextPath()%>/src/jsp/account_fix.jsp" class="backbtn"> 戻る </a>

	<div class="title">
		<h1>アカウント修正・削除</h1>
	</div>

	<section class="edit_main">
		<p><span id="edit_name"><%=memberName%>さん</span>の管理者権限</p>

		<form action="<%=request.getContextPath()%>/src/jsp/account_fix.jsp" method="post">
		<div class="edit_choice">
		<% if(admin.equals("true")) { %>
			<input type="radio" id="true" name="authority<%=memberId%>" value="true" checked="checked">
			<label for="true" class="radioLabel">あり</label>
			<input type="radio" id="false" name="authority<%=memberId%>" value="false">
			<label for="false" class="radioLabel">なし</label>
		<% } else { %>
			<input type="radio" id="true" name="authority<%=memberId%>" value="true">
			<label for="true" class="radioLabel">あり</label>
			<input type="radio" id="false" name="authority<%=memberId%>" value="false" checked="checked">
			<label for="false" class="radioLabel">なし</label>
		<% } %>
		</div>

		<input type="submit" class="submit" value="変更">
		</form>

	</section>

</body>
</html>