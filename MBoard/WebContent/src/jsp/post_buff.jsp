<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.PostInfoBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メンバーページ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/mypage.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jscroll/2.4.1/jquery.jscroll.min.js"></script>
<script src="<%=request.getContextPath()%>/src/js/scroll.js"></script>
</head>
<body>
<% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("postInfoBean"); %>
<% int count = (int)session.getAttribute("count"); %>
<%-- <%! int count = 1; %> --%>
<%-- <% System.out.println("post_buff到着"); %> --%>

	<div class="scroll">
		<% int max = count + 2; %>
		<% for (int i = count; i < max; i++) { %>
		<div class="post_area">
			<div >
				<img src="<%=request.getContextPath()%><%= pib[count].getPostUserIconPath() %>" class="icon_area">
			</div>
			<div class="title_area">
				<div>
					<span class="title_name"><%= pib[count].getPostUserName() %></span>が投稿しました
				</div>
				<div class="date"><%= pib[count].getPostDate() %><!-- date --></div>
			</div>
			<div class="clear"></div>
			<div class="post"><%= pib[count].getPostContents() %><!-- 投稿内容 --></div>
			<% count++; %>
		</div>
		<% } %>
		<% session.setAttribute("count", count); %>
		<% System.out.println("count:" + count); %>
		<% if (count < pib.length) {%>
			<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/post_buff.jsp">次の記事へ</a>
		<% } %>
	</div>
</body>
</html>