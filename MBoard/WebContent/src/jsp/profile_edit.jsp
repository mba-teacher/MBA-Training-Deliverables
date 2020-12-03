<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>プロフィール編集</title>
	<link rel="stylesheet" href="../css/nav.css" />
	<link rel="stylesheet" href="../css/create_board.css" />
	<link rel="stylesheet" href="../css/profile_edit.css" />
	<link rel="stylesheet" href="../css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="../img/logo_white.png">
			</div>

			<a href="#">
				<img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon" id="my-icon">
			</a>
			<a href="#">
				<img src="../img/mb_0_boad.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="../img/mb_0_address.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="../img/mb_0_link.png" class="nav-icon" id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#">
				<img src="../img/mb_0_notice.png" class="nav-icon">
				</a>
				<a href="#">
				<img src="../img/mb_0_other.png" class="nav-icon">
				</a>
			</div>

		</div>

		<div class="main">

			<div class="mypage_content">
				<div class="create-area">
					<form action="#" method="post" name="">
						<h1 class="page-title">プロフィール編集</h1>
						<p>プロフィールアイコン</p>
						<div class="board-icon-area">
							<div class="board-icon"></div>
							<input type="file" name=""  accept="image/*" id="board-icon" value="参照">
							<label for="board-icon" class="browse-button">参照</label>
						</div>

						<div>
							<p>表示名</p>
							<input type="text" name="user_name" placeholder="表示名">
						</div>
						<div>
							<p>メールアドレス</p>
							<input type="text" name="email_address" placeholder="メールアドレス">
						</div>
						<div>
							<p>LINEWORKSアドレス</p>
							<input type="text" name="line_works_id" placeholder="LINEWORKSアドレス">
						</div>
						<div>
							<p>スキル</p>
							<input type="text" name="" placeholder="スキル">
						</div>
						<div>
							<p>趣味・興味</p>
							<input type="text" name="" placeholder="趣味・興味">
						</div>

						<p>自己紹介</p>
						<textarea class="board-detail" placeholder="自己紹介"></textarea>
						<div class="submit-area">
							<input type="button" name="" value="キャンセル" class="cancel" onclick="location.href='#'">
							<input type="submit" name="" value="保存" class="submit">
						</div>
					</form>
				</div>
			</div>

		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
				<img src="../img/mb_0_attendance.png">
				<img src="../img/mb_0_attendance.png">
				<img src="../img/mb_0_attendance.png">
				<img src="../img/mb_0_attendance.png">
				<img src="../img/mb_0_attendance.png">
			</div>
			</div>
		</div>
	</div>

	<script src="../js/nav.js"></script>
	<script src="../js/create_board.js"></script>
</body>
</html>