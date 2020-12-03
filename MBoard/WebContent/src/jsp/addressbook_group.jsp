<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アドレス帳・グループ</title>
<link rel="stylesheet" href="../css/nav.css" />
<link rel="stylesheet" href="../css/addressbook_group.css" />
<link rel="stylesheet" href="../css/scroll.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
	<div class="flex_container">
		<div class="nav-area">
			<div class="logo-area">
				<img src="../img/logo_white.png">
			</div>

			<a href="#">
				<img src="../img/mb_0_link.png" class="nav-icon"></a>
			<a href="#"> <img src="../img/mb_0_boad.png" class="nav-icon"></a>
			<a href="#"> <img src="../img/mb_0_address.png" class="nav-icon"></a>
			<a href="#"> <img src="../img/mb_0_link.png" class="nav-icon"id="link-show"></a>

			<div class="nav-bottom">
				<a href="#"> <img src="../img/mb_0_notice.png" class="nav-icon"></a>
				<a href="#"> <img src="../img/mb_0_other.png" class="nav-icon"></a>
			</div>
		</div>

		<div class="main">
			<div class="addresandgroup">
				<h1>アドレス帳・グループ</h1>
				<div class="tabs">
					<input id="address" type="radio" name="tab_item" checked>
						<label class="tab_item" for="address">アドレス</label>
					<input id="group" type="radio" name="tab_item">
						<label class="tab_item"for="group">グループ</label>
					<div class="tab_content" id="address_content">
						<div class="tab_content_description">
							<div class="address_area">
								<div class="address">アドレス一覧</div>
									<a class="user_information_area">
									<div class="user_information">
										<img src="../img/mb_e_plus.png" class="user_icon">
										<div class="user_name">ユーザーネーム</div>
									</div>
									</a>
								<div class="clear"></div>
							</div>
						</div>
					</div>

					<div class="tab_content" id="group_content">
						<div class="tab_content_description">
							<div class="group_area">
								<div class="address">グループ一覧</div>
								<div class="box">
									<div class="group_tab">
										<label for="inbox1">グループ名</label> <input type="checkbox" id="inbox1" class="on-off">
										<div class="dropdown">
											<a class="user_information_area">
												<div class="user_information">
													<img src="../img/mb_e_plus.png" class="user_icon">
													<div class="user_name">ユーザーネーム</div>
												</div>
											</a>
											<div class="clear"></div>
										</div>
									</div>
									<div class="box">
										<div class="group_tab">
											<label for="inbox2">グループ名</label>
											<input type="checkbox" id="inbox2" class="on-off">
											<div class="dropdown">
												<a class="user_information_area">
													<div class="user_information">
														<img src="../img/mb_e_plus.png" class="user_icon">
														<p>ユーザーネーム</p>
													</div>
												</a>
												<div class="clear"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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