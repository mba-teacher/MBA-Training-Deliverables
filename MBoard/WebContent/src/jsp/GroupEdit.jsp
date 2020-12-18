<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" import="data.GroupInfoBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>グループ編集画面</title>
<%!int a = 0;%>
<%-- if(a!=0){ --%>
<link type="text/css" rel="stylesheet" href="src/css/GroupEdit.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="src/js/jquery.jscroll.min.js"></script>
<script src="src/js/jquery.inview.min.js"></script>
<script type="text/javascript" src="src/js/GroupEdit.js"></script>

</head>
<body>

	<%-- ここからサーブレットに送信した値がDB上の値と重複していた際に行う処理記述 --%>
	<% boolean cover=(boolean)session.getAttribute("cover"); %>
	<% if(cover==true){ %>
		<header class="alarm">そのグループ名は既に存在します。</header>
	<% } %>

	<h1>グループ編集</h1>

	<%-- 配列を定義し、セッションで持って来たDB情報を挿入 --%>
	<% ArrayList<GroupInfoBean> list = new ArrayList<GroupInfoBean>(); %>
	<% list.addAll((ArrayList<GroupInfoBean>) session.getAttribute("sample")); %>

	<div class="pos">

		<div class="bottonBack">
			<form action="src/jsp/admin_top.jsp" method="post">
				<input class="button4" type="submit" value="戻る"><br>
			</form>
		</div>

		<%-- ここから無限スクロール --%>
		<div class="jscroll"
			style="overflow-y: auto; width: 700px; height: 420px;">
			<% int count = 0; %>
		<% for (int i = 0; i < 5; i++) { %>
			<% String edit = "edit(" + i + ")"; %><%-- 編集用関数の引数指定 --%>
			<% String delete = "dele(" + i + ")"; %><%-- 削除用関数の引数指定 --%>
			<% String idParam = "idParam" + i; %><%-- Group_IDの指定に使用 --%>
			<% String groupParam = "param" + i; %><%-- Group_Nameの指定に使用 --%>

			<% if(i<list.size()){ %><%-- iの値がDBのレコード数未満である時 --%>
			<div id="group">
				<p id="tx"><%=list.get(i).getGroupName()%></p>
				<div class="bottonEdit">
					<input class="button1" type="button" id="showEdit" value="編集"
						onclick="<%=edit%>">

					<% if (i < 5) {
						count = i + 1;
						} %>
				</div>
				<p><%-- 指定したGroup_IDのパラメータを送信 --%>
					<input type="hidden" name="idParam" id="<%=idParam%>"
						value="<%=list.get(i).getGroupId()%>">
				</p>
				<p><%-- 指定したGroup_Nameのパラメータを送信 --%>
					<input type="hidden" name="groupParam" id="<%=groupParam%>"
						value="<%=list.get(i).getGroupName()%>">
				</p><%-- + --%>
				<div class="bottonDele">
					<input class="button1" type="button" id="showDele" value="削除"
						onclick="<%=delete%>">
				</div>
			</div>
			<% } %>
		<% } %>

		<%-- 既に表示してる回数分を保存 --%>
		<% session.setAttribute("count", count); %>

		<%-- 無限スクロールでの遷移先 --%>
		<a class="next" href="src/jsp/GroupEdit_Buff.jsp">next page</a>
		</div>
		<%-- 無限スクロール終わり --%>

		<div id="groupNew">
			<input class="button3" type="button" id="showNew" value="新規作成"
				onclick="create()">
		</div>
	</div>

	<!-- レイヤー -->
	<div id="layer"></div>

	<form action="http://localhost:8080/MBoard/GroupEdit" method="POST" class="checkform" onsubmit="return formCheck()">
		<!-- ポップアップ -->
		<div id="popup">
			<p>グループ編集</p>
			<g>グループ名</g>

			<div id="groupText"></div><%-- jsから挿入される --%>
			<div id="noNameEdit">*グループ名が入っていません</div><%-- 空白時のエラーメッセージ --%>
			<span class="alertarea"></span>
			<%-- <div>//テキスト欄(old)
			<input type="text" name="groupName" value="<%= gName %>"
				style="width: 320px; height: 30px; font-size: 25px;" maxlength=10>
			</div> --%>
			<t>10文字以内</t>

			<div class="inner">
				<input class="button2" type="button" value="キャンセル"
					onclick="cancel()">
				<input class="button2" type="submit"
					id="create" name="goto" value="更新">
			</div>
		</div>
	</form>

	<form action="http://localhost:8080/MBoard/GroupEdit" method="POST" class="checkform" onsubmit="return formCheck()">
		<!-- ポップアップ -->
		<div id="popupCre">
			<p>グループ作成</p>
			<g>グループ名</g>

			<div id="groupCreateText"></div><%-- jsから挿入される --%>
			<div id="noNameNew">*グループ名が入っていません</div><%-- 空白時のエラーメッセージ --%>
			<%-- <div>//テキスト欄(old)
			<input type="text" name="groupName" value="<%= gName %>"
				style="width: 320px; height: 30px; font-size: 25px;" maxlength=10>
			</div> --%>
			<t>10文字以内</t>

			<div class="inner">
				<input class="button2" type="button" value="キャンセル"
					onclick="cancel()">
				 <input class="button2" type="submit"
					id="create" name="goto" value="作成">
			</div>
		</div>
		</form>

	<form action="http://localhost:8080/MBoard/GroupEdit" method="POST" onsubmit="return true;">
		<!-- ポップアップ -->
		<div id="delepop">
			<p>本当に削除してもよろしいですか？</p>
			<br>
			<div id="deleteId"></div>

			<div class="inner">
				<input class="button2" type="button" value="キャンセル"
					onclick="cancel()">
				<input class="button2" type="submit" id="delete" name="goto"
					value="OK">
			</div>
		</div>
	</form>


</body>
</html>