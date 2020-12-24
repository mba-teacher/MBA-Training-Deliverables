<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アドレス帳・グループ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/addressbook_group.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 全メンバー情報とグループ情報を受け取る --%>
<% ArrayList<UserInfoBean> uib = (ArrayList<UserInfoBean>)request.getAttribute("allMembers"); %>
<% ArrayList<ArrayList<UserInfoBean>> lists = (ArrayList<ArrayList<UserInfoBean>>)request.getAttribute("groupMembers"); %>
<% String[] gn = (String[])request.getAttribute("groupNames"); %>
	<div class="flex_container">
		<div class="nav-area">
			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<a href="<%=request.getContextPath()%>/src/jsp/my_page.jsp">
				<img src="<%=request.getContextPath()%><%=myb.getProfileImgPath()%>" class="nav-icon" id="my-icon">
			</a>
			<a href="<%=request.getContextPath()%>/src/jsp/board.jsp">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			</a>
			<%-- 同一ページには遷移しないようにaタグをはずす --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon">

			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon"id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon"></a>
				<a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon"></a>
			</div>
		</div>

		<div class="main">
			<div class="addresandgroup">
				<h1>アドレス帳・グループ</h1>

				<div class="tabs">
					<input id="address" type="radio" name="tab_item" checked>
						<label class="tab_item" for="address">アドレス</label>
					<input id="group" type="radio" name="tab_item">
						<label class="tab_item" for="group">グループ</label>

					<div class="tab_content" id="address_content">
						<div class="tab_content_description">
							<p class="serch">検索：<input type="text" name="serch"></p><!-- 検索窓は後追いで実装のため作動しません -->

							<div class="address_area">
								<p class="address">アドレス一覧</p>
								<!-- <div class="user_information"> -->
									<% for (int i = 0; i < uib.size(); i++) { %>
									<a class="user_information_area" href="javascript:setAndSubmit('<%=uib.get(i).getUserID()%>','postIconForm')">
										<% if (uib.get(i).getProfileImgPath() == null || uib.get(i).getProfileImgPath() == "") { %>
										<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="user_icon">
										<% } else { %>
										<img src="<%=request.getContextPath()%><%=uib.get(i).getProfileImgPath()%>" class="user_icon">
										<% } %>
										<p class="user_name"><%= uib.get(i).getUserName() %></p>
									</a>
									<% } %>
								<div class="clear"></div>
								<!-- </div> -->
							</div>
						</div>
					</div>

					<div class="tab_content" id="group_content">
						<div class="tab_content_description">
							<div class="group_area">
								<p class="address">グループ一覧</p>
								<div class="box">
									<% for (int i = 0; i < lists.size(); i++) { %>
									<div class="group_tab">
										<label for="inbox<%=i%>"><%=gn[i]%></label><input type="checkbox" id="inbox<%=i%>" class="on-off">
										<div class="dropdown">
											<% for (int j = 0; j < lists.get(i).size(); j++) { %>
											<a class="user_information_area" href="javascript:setAndSubmit('<%=lists.get(i).get(j).getUserID()%>','postIconForm')">
												<% if (lists.get(i).get(j).getProfileImgPath() == null || lists.get(i).get(j).getProfileImgPath() == "") { %>
												<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="user_icon">
												<% } else { %>
												<img src="<%=request.getContextPath()%><%=lists.get(i).get(j).getProfileImgPath()%>" class="user_icon">
												<% } %>
												<p class="user_name"><%=lists.get(i).get(j).getUserName()%></p>
											</a>
											<% } %>
										</div>
									</div>
									<div class="clear"></div>
									<% } %>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%-- 各メンバーページに飛ばす用のユーザーIDを送るためのフォーム --%>
				<form id="postIconForm" action="<%=request.getContextPath()%>/member" method="post">
					<input type="hidden" name="memberId" value="" />
				</form>
			</div>
		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/method.js"></script>
</body>
</html>