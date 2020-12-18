<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" import="data.GroupInfoBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>グループ編集画面</title>
<%!int i;%>

<link type="text/css" rel="stylesheet" href="src/css/GroupEdit.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="src/js/jquery.jscroll.min.js"></script>
<script src="src/js/jquery.inview.min.js"></script>
<script type="text/javascript" src="src/js/GroupEdit.js"></script>

</head>
<body>

	<%-- 配列を定義し、セッションで持って来たDB情報を挿入 --%>
	<% ArrayList<GroupInfoBean> list=new ArrayList<GroupInfoBean>(); %>
	<% list.addAll((ArrayList<GroupInfoBean>)session.getAttribute("sample")); %>
	<%-- 既に表示してるグループの回数分を取得 --%>
	<% int count = (int)session.getAttribute("count"); %>

	<% for(int i=count;i<count+5;i++){ %>
		<% String edit = "edit("+i+")"; %><%-- 編集用関数の引数指定 --%>
		<% String delete = "dele("+i+")"; %><%-- 削除用関数の引数指定 --%>
		<% String idParam = "idParam"+i; %><%-- Group_IDの指定に使用 --%>
		<% String groupParam = "param"+i; %><%-- Group_Nameの指定に使用 --%>

		<% if(i<list.size()){ %><%-- iの値がDBのレコード数未満の時 --%>
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

	<% } %>

	<%-- 既に表示した回数分を保存 --%>
	<% session.setAttribute("count",count+5); %>

	<% if(count<list.size()){ %><%-- countの値がDBのレコード数未満の時 --%>
		<div class="jscroll">
			<%-- 無限スクロールでの遷移先 --%>
			<a class="next" href="src/jsp/GroupEdit_Buff.jsp">next page</a>
		</div>
	<% } %>

</body>
</html>