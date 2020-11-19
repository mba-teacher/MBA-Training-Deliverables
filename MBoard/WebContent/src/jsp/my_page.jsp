<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>マイページ</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/mypage.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon">
				</a>
				<a href="#">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon">
				</a>
			</div>

		</div>

		<div class="main">
			<div class="mypage_content">
				<div class="profile_area">
					<div class="profile_icon">
					</div>
					<h2>苗字名前</h2>
					<p class="address">mail:mail.mba-international.jp</p>
					<div class="profile_edit">プロフィール編集</div>
					<div class="admin">管理者画面</div>
				</div>

				<div class="tabs">
					<input id="post" type="radio" name="tab_item" checked>
					<label class="tab_item" for="post">投稿した記事</label>
					<input id="profile" type="radio" name="tab_item">
					<label class="tab_item" for="profile">プロフィール</label>

					<div class="tab_content" id="post_content">
						<div class="tab_content_description">
							<p class="c-txtsp">投稿内容</p>
						</div>
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
</body>
</html>