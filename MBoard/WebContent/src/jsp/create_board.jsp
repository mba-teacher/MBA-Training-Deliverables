<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板作成</title>
<link rel="stylesheet" href="../css/nav.css" />
<link rel="stylesheet" href="../css/create_board.css" />
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

			<a href="#"> <img src="../img/mb_0_link.png" class="nav-icon">
			</a> <a href="#"> <img src="../img/mb_0_boad.png" class="nav-icon">
			</a> <a href="#"> <img src="../img/mb_0_address.png" class="nav-icon">
			</a> <a href="#"> <img src="../img/mb_0_link.png" class="nav-icon"
				id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#"> <img src="../img/mb_0_notice.png" class="nav-icon">
				</a> <a href="#"> <img src="../img/mb_0_other.png" class="nav-icon">
				</a>
			</div>

		</div>

		<div class="main">

			<div class="mypage_content">
				<div class="create-area">
					<form action="" method="" name="">
						<h1 class="page-title">掲示板作成</h1>
						<div class="board-icon-area">
							<div class="board-icon"></div>
							<input type="file" name="" id="board-icon" value="参照"> <label
								for="board-icon" class="browse-button">参照</label>
						</div>
						<div class="board-name-area">
							<p>掲示板名</p>
							<input type="text" name="" placeholder="掲示板名">
						</div>
						<div>
							<p>掲示板色選択</p>
							<select name="" class="board-color">
								<option value="">レッド</option>
								<option value="">ブルー</option>
								<option value="">グリーン</option>
							</select>
						</div>
						<p>アクセス制限</p>
						<div class="access">
							<div class="access-box">
								<div>アクセス制限</div>
								<div class="arrow">▼</div>
							</div>
							<div class="access-list-bg"></div>
							<div class="access-list-area">

								<div class="access-list-header">
									<div class="access-list-header-title">アクセス制限</div>
									<div class="arrow">▲</div>
								</div>
								<div class="access-list-item">
									<input type="checkbox" id="user1"> <label for="user1"
										class="access-item"> <img src="../img/mb_e_plus.png"
										class="access-user-icon">
										<div class="access-user-name">苗字名前</div>
										<div class="checkbox"></div>
									</label>
								</div>
								<div class="access-list-item">
									<input type="checkbox" id="user2"> <label for="user2"
										class="access-item"> <img src="../img/mb_e_plus.png"
										class="access-user-icon">
										<div class="access-user-name">苗字名前</div>
										<div class="checkbox"></div>
									</label>
								</div>
							</div>
						</div>
						<p>掲示板詳細</p>
						<textarea class="board-detail" placeholder="掲示板詳細"></textarea>
						<div class="submit-area">
							<input type="button" name="" value="キャンセル" class="cancel">
							<input type="submit" name="" value="作成" class="submit">
						</div>
					</form>
				</div>
			</div>

		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="../img/mb_0_attendance.png"> <img
						src="../img/mb_0_attendance.png"> <img
						src="../img/mb_0_attendance.png"> <img
						src="../img/mb_0_attendance.png"> <img
						src="../img/mb_0_attendance.png">
				</div>
			</div>
		</div>
	</div>

	<script src="../js/nav.js"></script>
	<script src="../js/create_board.js"></script>
</body>
</html>