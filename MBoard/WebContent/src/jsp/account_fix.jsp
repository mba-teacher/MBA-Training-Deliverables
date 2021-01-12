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

	<input type="button" class="backbtn js-modal-open" value="戻る">

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
			<% for (int i = 0; i < uibs.size(); i++) { %><%-- 全表示 --%>
				<% if (i < uibs.size()) { %>
				<%-- 管理者権限の変更を受け取る変数 --%>
				<% if (request.getParameter("authority"+ uibs.get(i).getUserID()) != null && !request.getParameter("authority"+ uibs.get(i).getUserID()).isEmpty()) { %>
				<%    editInfo[i] = request.getParameter("authority"+ uibs.get(i).getUserID()); %>
				<% } %>
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
						<input type="button" value="編集" class="edit open3" data-num="<%=uibs.get(i).getUserID()%>" data-bool="<%=editInfo[i]%>">
					<% } else { %>
						<% if(uibs.get(i).isAdmin()) { %>
						<%    string = "あり"; %>
						<% } else { %>
						<%    string = "なし"; %>
						<% } %>
						<div class="adminChoise"><%=string%></div>
						<input type="button" value="編集" class="edit open3" data-num="<%=uibs.get(i).getUserID()%>" data-bool="<%=uibs.get(i).isAdmin()%>">
					<% } %>

					<input type="button" class="delete js-modal-open2" value="削除" onclick="setMember('<%=uibs.get(i).getUserID()%>', 'deleteForm')">
				</li>
				<% count++; %>
				<% } %>
			<% } %>
			<% session.setAttribute("count", count); %>
			<% System.out.println("遷移前のcount:" + session.getAttribute("count")); %>
			</ul>
			<%-- 無限スクロールは実装していない --%>
			<%-- <% if (count < uibs.size()) { %>
				<div class="scroll">
					<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/account_fix_buff.jsp">次の記事へ</a>
				</div>
			<% } %> --%>

			<% session.setAttribute("editInfo", editInfo); %>
			<% System.out.println(editInfo[0]); %>
			<!-- class="ul" -->
			</div>
		</div>
	</div>

	<form id="editForm" action="<%=request.getContextPath()%>/editAccountAfter" method="post" onsubmit="return editSendCheck()">
		<input type="submit" class="okbtn" value="修正">
		<input type="hidden" name="sendType" value="edit">
	</form>


	<div class="modal js-modal">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				編集内容は保持されませんが<br> よろしいですか？
			</p>
			<div class="btn_box">
				<a href="" class="js-modal-close">キャンセル</a>
				<a href="<%=request.getContextPath()%>/src/jsp/admin_top.jsp" class="mobal_btn2" id="toTop">OK</a>
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
			<form id="deleteForm" action="<%=request.getContextPath()%>/editAccountAfter" method="post">
				<input type="hidden" name="sendType" value="delete">
				<input type="hidden" name="memberId" value=""><%-- 削除するアカウントのID --%>
				<div class="btn_box">
					<input type="button" class="js-modal-close" value="キャンセル">
					<!-- <a href="" class="js-modal-close">キャンセル</a> -->
					<input type="submit" class="mobal_btn2" value="OK">
					<!-- <a href="" class="mobal_btn2">OK</a> -->
				</div>
			</form>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->

	<section class="modal edit_main">
		<div class="modal__bg"></div>
		<div class="modal__content">
		<p class="close-mark">×</p>
		<p><span id="edit_name"></span>の管理者権限</p>

		<form action="<%=request.getContextPath()%>/src/jsp/account_fix.jsp" method="post">
			<div class="edit_choice">
				<input type="radio" id="true" name="authority" value="true">
				<label for="true" class="radioLabel">あり</label>
				<input type="radio" id="false" name="authority" value="false">
				<label for="false" class="radioLabel">なし</label>
			</div>
			<div class="btn_box">
				<input type="submit" class="modal_btn3" value="変更">
			</div>
		</form>
		</div>
	</section>

<script src="<%=request.getContextPath()%>/src/js/account_fix.js"></script>
<script src="<%=request.getContextPath()%>/src/js/scroll.js"></script>
<script src="<%=request.getContextPath()%>/src/js/method.js"></script>

</body>
</html>