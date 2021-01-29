<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*" import="data.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>各ユーザーグループ設定画面</title>
<link rel="stylesheet" href="src/css/group_user.css">
<link rel="stylesheet" href="src/css/guser_cansel_modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="src/js/group_user.js"></script>
</head>
<body>

	<input type="button" class="backbtn js-modal-open" value="戻る">


	<%-- 配列を定義し、セッションで持って来たDB情報を挿入 --%>
	<% ArrayList<GroupInfoBean> grouplist = new ArrayList<GroupInfoBean>(); %>
	<% ArrayList<GroupMemberInfoBean> memberGrouplist = new ArrayList<GroupMemberInfoBean>(); %>
	<% UserInfoBean userInfo= (UserInfoBean)session.getAttribute("userInfo"); %>
	<% grouplist.addAll((ArrayList<GroupInfoBean>) session.getAttribute("groupList")); %>
	<% memberGrouplist.addAll((ArrayList<GroupMemberInfoBean>) session.getAttribute("memberGroupList")); %>

	<div class="name">
		<h1><%=userInfo.getUserName() %>:ユーザーグループ設定</h1>
		<p>追加するグループを選択してください。</p>
	</div>

	<form action="http://localhost:8080/MBoard/GroupUser" method="post">

	<div class="center_box">
		<div class="c_box">

		<% for(int i=0;i<grouplist.size();i++){ %>
			<%-- 3つ項目が表示されるごとにdiv class="c_box"で囲い直す --%>
			<% int groupId=grouplist.get(i).getGroupId(); %>
			<% if(i%3==0 && i!=0){ %>
				</div>
				<div class="c_box">
			<% } %>

			<%-- そのグループに対象ユーザーが所属しているかの判定 --%>
			<% boolean checkflag=false; %>
			<% for(int j=0;j<memberGrouplist.size();j++){ %>
				<% int memberGroupId= memberGrouplist.get(j).getGroupId(); %>
				<% if(groupId==memberGroupId){ %>
					<% checkflag=true; %>
				<% } %>
			<% } %>
			<% if(checkflag==false){ %>
				<%-- 所属してないグループなら --%>
				<input type="checkbox" id="check-<%=i %>" name="checkbox01" onChange="change()" value="<%=grouplist.get(i).getGroupId() %>"> <label
				for="check-<%=i %>" class="checkbox01"> <%=grouplist.get(i).getGroupName() %></label>
			<% } else if(checkflag==true){ %>
				<%-- 所属してるグループならチェックボックスにチェックを入れた状態に --%>
				<input type="checkbox" id="check-<%=i %>" name="checkbox01" checked="checked" onChange="change()" value="<%=grouplist.get(i).getGroupId() %>"> <label
				for="check-<%=i %>" class="checkbox01"> <%=grouplist.get(i).getGroupName() %></label>
			<% } %>
		<% } %>
		</div>

	</div>

	<%-- 決定ボタンを押した時、対象ユーザーのIDを送付 --%>
	<input type='hidden' name='userId' id='input' value='<%=userInfo.getUserID() %>'>
	<input type="submit" class="okbtn" name="goto" value="決定">

	<%-- ここからポップアップ --%>
	<div class="modal js-modal">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				編集内容は保持されませんが<br> よろしいですか？
			</p>
			<div class="btn_box">
				<input type="button" class="js-modal-close" value="キャンセル">
				<!-- <a href="" class="js-modal-close">キャンセル</a> -->
				<input type="submit" class="mobal_btn2" id="mobal_btn2" name="goto" value="OK">
				<!-- <a href="" class="mobal_btn2">OK</a> -->
			</div>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->
	</form>


</body>
</html>