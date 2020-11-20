<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import = "data.UserInfoBean,data.PostInfoBean"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>マイページ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/mypage.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% UserInfoBean uib = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<% PostInfoBean[] pib = (PostInfoBean[])request.getAttribute("postInfoBean"); %>
	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon">
			</a> <a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			</a> <a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon">
			</a> <a href="#"> <img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon"
				id="link-show">
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
					<div><img src="<%=request.getContextPath()%><%= uib.getProfileImgPath() %>" class = "profile_icon"></div>
					<h2><%= uib.getUserName() %></h2>
					<p class="address"><%= uib.getEmailAdress() %></p>
					<div class="profile_edit">プロフィール編集</div>
					<div class="admin">管理者画面</div>
				</div>

				<div class="tabs">
					<input id="post" type="radio" name="tab_item" checked> <label
						class="tab_item" for="post">投稿した記事</label> <input id="profile"
						type="radio" name="tab_item"> <label class="tab_item"
						for="profile">プロフィール</label>

					<div class="tab_content" id="post_content">
						<% for(int i = 0; i < pib.length; i++){ %>
						<div class="tab_content_description">
							<p class="c-txtsp">
							<div class="post_area">
								<div >
									<img src="<%=request.getContextPath()%><%= pib[i].getPostUserIconPath() %>"class="icon_area">
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

							</p>
						</div>
						<%} %>
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
</body>
</html>