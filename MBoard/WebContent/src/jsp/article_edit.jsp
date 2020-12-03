<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>記事編集</title>
	<link rel="stylesheet" href="../css/nav.css" />
	<link rel="stylesheet" href="../css/article_edit.css" />
	<link rel="stylesheet" href="../css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="../img/logo_white.png">
			</div>

			<a href="#">
				<img src="../img/mb_0_link.png" class="nav-icon">
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

				<div class="article-edit-area">

					<h1 class="page-title">記事編集</h1>
					<form action="" name="">

						<div class="form">
							<input class="post-form" name="post" placeholder="なんでも投稿できます">
							<div class="post-detail">
								<textarea class="post-form-content" name="post-content" placeholder="なんでも投稿できます"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="../img/mb_g_letteredit.png">
										<img src="../img/mb_g_letteredit.png">
										<img src="../img/mb_g_letteredit.png">
										<img src="../img/mb_g_letteredit.png">
									</div>
								</div>
							</div>
						</div>

						<div class="submit-area">
							<input type="button" name="" value="削除" class="delete">
							<input type="button" name="" value="キャンセル" class="cancel">
							<input type="submit" name="" value="保存" class="save">
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
</body>
</html>