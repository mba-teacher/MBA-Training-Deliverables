<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" import="data.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー設定</title>
<link rel="stylesheet" href="src/css/admin_userconfig.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="src/js/admin_userconfig.js"></script>
<body>
	<!-- <a href="" class="backbtn js-modal-open"> 戻る </a> -->
	<form action="src/jsp/admin_top.jsp" method="post">
		<input class="backbtn js-modal-open" type="submit" value="戻る">
	</form>

	<div class="title">
		<h1>ユーザー設定</h1>
	</div>

	<%-- 配列を定義し、セッションで持って来たDB情報を挿入 --%>
	<% ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>(); %>
	<% ArrayList<GroupInfoBean> groupMemberlist; %>

	<% userlist.addAll((ArrayList<UserInfoBean>) session.getAttribute("userList")); %>


	<div id="realWrite">
		<p></p>
	</div>

	<div class="search-area">
		<div id="realText">
		<form>
			<input type="text" id="search-text" value="" placeholder="　ユーザー名検索" class="textbox">
		</form>
		</div>
	</div>

	<div class="test">
		<div class="user_name">

			<div class="user_list_header">
				<P>
					ユーザー
					<span2>グループ</span2>
				</P>
			</div>

		<div class="user_list">
			<%-- 検索後の結果をここに表示する --%>
			<div class="search-result"id="search-result">
				<div id="search-result__list"></div>

	    		<div id="noResult">
					<p id="none">該当しませんでした</p>
				</div>
	    	</div>

		</div>

		<!-- <div class="target-area"id="target-area"> -->
		<%-- ここのfor文内の記述を表示・非表示で コントロールするタグが必要--%>
		<div id="close-target">
		<% for(int i=0;i<userlist.size();i++){ %>
		 	<div class="user_list">

				<div class="target-area"id="target-area">
				<%--ここのワードのみを検索対象にするタグ --%>
					<wr>
						<%=userlist.get(i).getUserName()%>
					</wr>
				<%--ここのワードのみを検索対象にするタグ終わり --%>

				<% groupMemberlist = new ArrayList<GroupInfoBean>(); %>
				<% groupMemberlist =(ArrayList<GroupInfoBean>) session.getAttribute("groupMemberList"+i); %>
				<%-- System.out.println(groupMemberlist.get(0).getGroupName()); --%>
				<span2 class="groupChoise">
				<% int groupCount=0;
				boolean wordLine=false; %>
				<%-- ユーザーごとの所属グループを表示させる処理 --%>
				<% for(int j=0;j<groupMemberlist.size();j++){ %>
					<% if(j==0){ %>
						<% String gro = groupMemberlist.get(j).getGroupName(); %>
						<%=gro %>
						<% groupCount=groupCount+gro.length(); %>
					<% }else if(j!=0){ %>
						<% String gro = groupMemberlist.get(j).getGroupName(); %>
						<% System.out.println(groupCount+gro.length()+1); %>
						<%-- 所属グループ名の合計文字数が20字を超えているかいないかで判定する処理 --%>
						<% if(groupCount+gro.length()+1<=20){ %>
							/<%=gro %>
							<% groupCount=groupCount+gro.length()+1; %>
						<% }else if(wordLine==false){ %>
							/<%=gro.substring(0,20-(groupCount+1)) %>…
							<% wordLine=true; %>
						<% } %>
					<% } %>
				<% } %>
				</span2>

				<form action="http://localhost:8080/MBoard/GroupUser" method="post" class="editButton">
					<%-- 決定ボタンを押した時、対象ユーザーのIDを送付 --%>
					<input type='hidden' name='userId' id='input' value='<%=userlist.get(i).getUserID()%>'>
					<input type="submit" class="delete js-modal-open2" name="goto" value="編集"
					style="font-size: 1.0em;width: 70px;height: 40px;">
				</form>

				</div>

			</div>
		<% } %>
		</div><%-- ここに表示非表示のコントロールタグ終わり --%>

		</div>
	</div>

</body>
</html>