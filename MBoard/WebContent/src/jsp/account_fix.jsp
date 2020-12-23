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
<script src="<%=request.getContextPath()%>/src/js/account_fix_modal.js"></script>
</head>
<body>
<% ArrayList<UserInfoBean> uibs = (ArrayList<UserInfoBean>)session.getAttribute("membersInfoBean"); %>
<% int count = 0; %>
<% String[] editInfo = (String[])session.getAttribute("editInfo"); %>

	<a href="" class="backbtn js-modal-open"> 戻る </a>
	<%-- <a href="" class="okbtn">修正</a> --%>

	<div class="title">
		<h1>アカウント修正・削除</h1>
	</div>

	<%-- ↓これは何のため？ --%>
	<div id="realWrite">
		<p></p>
	</div>

	<div id="realText">
		<input type="text" value="" placeholder="　ユーザー名検索" class="textbox" id="search-text">
	</div>

	<div class="test">

		<form name="editSendForm" action="<%=request.getContextPath()%>/src/jsp/account_edit.jsp" method="post">

		<div class="user_name">
			<div class="user_list_header">
				<p>ユーザー<span>管理者権限</span>
				</p>
			</div>

			<div class="search-result" id="search-result">
				<div id="search-result__list"></div>

				<div id="noResult">
					<p id="none">該当しませんでした</p>
				</div>
			</div>

			<div class="ul" id="target-area">
			<ul>
			<% for (int i = 0; i < uibs.size(); i++) { %><%-- 11件表示させる --%>
				<% if (i < uibs.size()) { %>
				<%-- 管理者権限の変更を受け取る変数 --%>
				<% if (request.getParameter("authority"+ uibs.get(i).getUserID()) != null && !request.getParameter("authority"+ uibs.get(i).getUserID()).isEmpty()) { %>
				<%    editInfo[i] = request.getParameter("authority"+ uibs.get(i).getUserID()); %>
				<% } %>
				<%-- <% String admin = null; %> --%>

				<%-- 表示する文字を入れる変数 --%>
				<% String string = null; %>

				<li class="user_list">
					<p><%=uibs.get(i).getUserName() %></p>
					<input type="hidden" name="memberName<%=uibs.get(i).getUserID()%>" value="<%=uibs.get(i).getUserName()%>">

					<%-- 管理者権限の表示 --%>
					<% if (editInfo[i] != null && !editInfo[i].isEmpty() && !editInfo[i].equals(String.valueOf(uibs.get(i).isAdmin()))) { %>
					<%-- nullでなく、空文字でなく、変更があった場合 --%>
						<% if(editInfo[i].equals("true")) { %>
						<%    string = "あり"; %>
						<% } else { %>
						<%    string = "なし"; %>
						<% } %>
						<div class="adminChoise changed"><%=string%></div>
						<input type="hidden" name="isAdmin<%=uibs.get(i).getUserID()%>" value="<%=editInfo[i]%>">
					<% } else { %>
						<% if(uibs.get(i).isAdmin()) { %>
						<%    string = "あり"; %>
						<% } else { %>
						<%    string = "なし"; %>
						<% } %>
						<div class="adminChoise"><%=string%></div>
						<input type="hidden" name="isAdmin<%=uibs.get(i).getUserID()%>" value="<%=uibs.get(i).isAdmin()%>">
					<% } %>

					<input type="button" value="編集" class="edit" onclick="setAdminAndSubmit('<%=uibs.get(i).getUserID()%>')">
					<input type="button" class="delete js-modal-open2" value="削除">
				</li>
				<% count++; %>
				<% } %>
			<% } %>
			<% session.setAttribute("count", count); %>
			<% System.out.println("遷移前のcount:" + session.getAttribute("count")); %>
			</ul>
			<%-- <% if (count < uibs.size()) { %>
				<div class="scroll">
					<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/account_fix_buff.jsp">次の記事へ</a>
				</div>
			<% } %> --%>

			<% session.setAttribute("editInfo", editInfo); %>
			<!-- class="ul" -->
			</div>

		</div>
			<input type="submit" class="okbtn" formaction="<%=request.getContextPath()%>/editAccountAfter" value="修正">
			<input type="hidden" name="memberId" value=""><%-- 編集画面用のhidden --%>
		</form>
	</div>


	<div class="modal js-modal">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				編集内容は保持されませんが<br> よろしいですか？
			</p>
			<div class="btn_box">
				<a href="" class="js-modal-close">キャンセル</a>
				<a href="<%=request.getContextPath()%>/src/jsp/admin_top.jsp" class="mobal_btn2">OK</a>
			</div>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->


	<div class="modal js-modal2">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				<br>本当に削除してもよろしいですか？
			</p>
			<div class="btn_box">
				<a href="" class="js-modal-close">キャンセル</a>
				<a href="" class="mobal_btn2">OK</a>
			</div>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->

<script src="<%=request.getContextPath()%>/src/js/account_fix.js"></script>
<script src="<%=request.getContextPath()%>/src/js/scroll.js"></script>
<script src="<%=request.getContextPath()%>/src/js/method.js"></script>

</body>
</html>