<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>デプロイテスト</title>
</head>
<body>
<% String item = (String)request.getAttribute("item"); %>
<% ArrayList<UserInfoBean> uibs = (ArrayList<UserInfoBean>)request.getAttribute("allmembers"); %>
	<h1>DBにアクセスする</h1>
	<% if(item == null || item.isEmpty()) { %>
		<p>表示しています</p>
	<% } else { %>
		<p><%=item%> ページから遷移できました</p>
	<% } %>
	<div>
		<p>ユーザーインフォ <% %></p>
		<p>-----------------------------------</p>
		<% for (int i = 0; i < uibs.size(); i++) { %>
			<p><%=uibs.get(i).getUserId()%><br><%=uibs.get(i).getUserName()%><br><%=uibs.get(i).getLoginId()%><br>
			<%=uibs.get(i).getLoginPass()%><br><%=uibs.get(i).getLoginLog()%><br>
			<%=uibs.get(i).getProfileImgPath()%><br><%=uibs.get(i).getAdmin()%></p>
			<p>-----------------------------------</p>
		<% } %>
	</div>
</body>
</html>