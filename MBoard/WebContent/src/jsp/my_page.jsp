<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import = "data.UserInfoBean,data.PostInfoBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>マイページ</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/mypage.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jscroll/2.4.1/jquery.jscroll.min.js"></script>
</head>
<body>
<!-- ユーザー自身のユーザー情報をセッションから受け取る -->
<% UserInfoBean uib = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- req から session に変更 --%>
<% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("postInfoBean"); %>
<% session.setAttribute("count", 0); %>
<% int count = (int)session.getAttribute("count"); %>

	<% if (request.getAttribute("notice") != null && (String)request.getAttribute("notice") == "edited") { %>
		<header class="alarm">プロフィール を更新しました。</header>
	<% } %>
	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<%-- 同一ページには遷移しない設定のためaタグをはずす --%>
				<img src="<%=request.getContextPath()%><%= uib.getProfileImgPath() %>" class="nav-icon" id="my-icon">

			<a href="<%=request.getContextPath()%>/src/jsp/board.jsp">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			</a>
			<a href="<%=request.getContextPath()%>/addressbook">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon">
				</a> <a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon">
				</a>
			</div>

		</div>

		<div class="main">
			<div class="mypage_content">
				<div class="profile_area">
					<div>
						<% if (uib.getProfileImgPath() == null || uib.getProfileImgPath().isEmpty())  { %>
							<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="profile_icon">
						<% } else { %>
							<img src="<%=request.getContextPath()%><%= uib.getProfileImgPath() %>" class="profile_icon">
						<% } %>
					</div>
					<h2><%= uib.getUserName() %></h2>
					<p class="address"><%= uib.getEmailAdress() %></p>
					<a href="profile_edit.jsp"><div class="profile_edit">プロフィール編集</div></a>
					<% if (uib.isAdmin()) { %>
						<a href="admin_top.jsp"><div class="admin">管理者画面</div></a>
					<% } %>
				</div>

				<div class="tabs">
					<input id="post" type="radio" name="tab_item" checked> <label
						class="tab_item" for="post">投稿した記事</label> <input id="profile"
						type="radio" name="tab_item"> <label class="tab_item"
						for="profile">プロフィール</label>

					<div class="tab_content" id="post_content">
						<div class="tab_content_description">
							<p class="c-txtsp"></p>

					<% if (pib != null) { %>
					<%-- pib(記事Bean)がnullではない場合 --%>
							<% for(int i = 0; i < 5; i++){ %>
								<% if (i < pib.length) { %>
									<div class="post_area">
										<div >
											<img src="<%=request.getContextPath()%><%= pib[i].getPostUserIconPath() %>" class="icon_area">
										</div>
										<div class="title_area">
											<div>
												<span class="title_name"><%= pib[i].getPostUserName() %></span>が投稿しました
											</div>
											<div class="date"><%= pib[i].getPostDate() %><!-- date --></div>
										</div>
										<div class="clear"></div>
										<div class="post"><%= pib[i].getPostContents() %><!-- 投稿内容 --></div>
									</div>
									<% count++; %>
									<% System.out.println("count:" + count); %>
								<% } %>
							<% } %>
							<% session.setAttribute("count", count); %>
							<% System.out.println("遷移前のcount:" + count); %>
						</div>
						<% if (count < pib.length) { %>
							<div class="scroll">
								<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/post_buff.jsp">次の記事へ</a>
							</div>
						<% } %>
					<% } else { %>
					<%-- pib(記事Bean)がnullの場合 --%>
						</div><!-- class="tab_content_description" -->
					<% } %>
					</div>
					<div class="tab_content" id="profile_content">
						<div class="tab_content_description">
							<p class="c-txtsp">プロフィール内容</p>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png"> <img
						src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png"> <img
						src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png"> <img
						src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png"> <img
						src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/scroll.js"></script>
</body>
</html>