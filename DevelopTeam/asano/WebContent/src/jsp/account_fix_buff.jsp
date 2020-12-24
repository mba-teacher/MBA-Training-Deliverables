<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント修正・削除</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/account_fix.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/cansel_modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jscroll/2.4.1/jquery.jscroll.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/src/js/scroll.js"></script>
<script src="<%=request.getContextPath()%>/src/js/account_fix.js"></script>
<script src="<%=request.getContextPath()%>/src/js/account_fix_modal.js"></script>
</head>
<body class="buff">
<%-- セッションにあるメンバー情報を受け取る --%>
<% ArrayList<UserInfoBean> uibs = (ArrayList<UserInfoBean>)session.getAttribute("membersInfoBean"); %>
<%-- 何件表示したかカウントを受け取る --%>
<% int count = (int)session.getAttribute("count"); %>

	<div class="scroll">

	<% int maxDisplay = count + 5; %>
	<ul>
	<% for (int i = count; i < maxDisplay; i++) { %><%-- 5件表示させる --%>
		<% if (i < uibs.size()) { %>
		<li class="user_list">
			<p><%=uibs.get(i).getUserName() %></p>
			<div class="adminChoise">
				<% if(uibs.get(i).isAdmin()) { %>
					<input type="radio" name="authority<%=uibs.get(i).getUserID()%>" value="yes" checked="checked">あり
					<input type="radio" name="authority<%=uibs.get(i).getUserID()%>" value="none">なし
				<% } else { %>
					<input type="radio" name="authority<%=uibs.get(i).getUserID()%>" value="yes">あり
					<input type="radio" name="authority<%=uibs.get(i).getUserID()%>" value="none" checked="checked">なし
				<% } %>
			</div>
			<input type="button" class="delete js-modal-open2" value="削除">
		</li>
		<% count += 1; %>
		<% } %>
	<% } %>
	</ul>

	<%-- 既に表示した回数分を保存 --%>
	<% session.setAttribute("count", count); %>
	<% System.out.println("遷移前のcount:" + session.getAttribute("count")); %>

	<% if (count < uibs.size()) { %>
			<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/account_fix_buff.jsp">次へ</a>
	<% } %>
	</div>
</body>
</html>