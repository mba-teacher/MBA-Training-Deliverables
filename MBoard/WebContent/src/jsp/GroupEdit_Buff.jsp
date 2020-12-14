<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" import="data.GroupInfoBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>グループ編集画面</title>
<%!int b = 0;%>
<%!int a = 5;%><%-- 遷移してきた時aを外部からの値(0)で上書き --%>
<%!int i;%>
<%
	System.out.println("b:" + b);
%>
<%-- if(b!=0){ --%>
<link type="text/css" rel="stylesheet" href="src/css/GroupEdit.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="src/js/jquery.jscroll.min.js"></script>
<script src="src/js/jquery.inview.min.js"></script>
<script type="text/javascript" src="src/js/GroupEdit.js"></script>

<% System.out.println(a); %>

</head>
<body>

	<% ArrayList<GroupInfoBean> list=new ArrayList<GroupInfoBean>(); %>
	<% list.addAll((ArrayList<GroupInfoBean>)session.getAttribute("sample")); %>
	<% int count = (int)session.getAttribute("count"); %>

	<% for(int i=count;i<count+5;i++){ %>
		<% String edit = "edit("+i+")"; %><%-- + --%>
		<% String delete = "dele("+i+")"; %>
		<% String idParam = "idParam"+i; %>
		<% String groupParam = "param"+i; %><%-- + --%>

		<% if(i<list.size()){ %><%-- iの値がDBのカラム数未満の時 --%>
		<div id="group">

			<p id="tx"><%=list.get(i).getGroupName() %></p>
			<div class="bottonEdit">
				<input class="button1" type="button" id="showEdit" value="編集"
					onclick="<%=edit %>">
			</div>
			<p><%-- 指定したGroup_IDのパラメータを送信 --%>
				<input type="hidden" name="idParam" id="<%= idParam %>"
					value="<%= list.get(i).getGroupId() %>">
			</p>
			<p><%-- 指定したGroup_Nameのパラメータを送信 --%>
				<input type="hidden" name="groupParam" id="<%= groupParam %>"
					value="<%= list.get(i).getGroupName() %>">
			</p><%-- + --%>
			<div class="bottonDele">
				<input class="button1" type="button" id="showDele" value="削除"
					onclick="<%=delete %>">
			</div>
		</div>
		<% } %>
	<%-- System.out.println("count:"+count); --%>
	<% } %>

	<% session.setAttribute("count",count+5); %>
	<% if(count<list.size()){ %><%-- countの値がDBのカラム数未満の時 --%>
		<div class="jscroll">
			<%-- 無限スクロールでの遷移先 --%>
			<a class="next" href="src/jsp/GroupEdit_Buff.jsp">next page</a>
		</div>
	<% } %>

</body>
</html>