<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>掲示板</title>
	<link rel="stylesheet" href="../css/nav.css" />
	<link rel="stylesheet" href="../css/board.css" />
	<link rel="stylesheet" href="../css/board_detail.css" />
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

				<div class="board-list-area">
					<div class="board-list-header">
						<div class="header-title">参加掲示板</div>
						<input type="text" class="">
					</div>
					<div class="board-list">

						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>

					</div>
					<div class="board-list-footer">
						<div class="show-board-list">掲示板を登録</div>
						<img src="../img/mb_e_plus.png" class="add_button show-board-list">
					</div>
				</div>

				<div class="board-content-area">

					<div class="board-header">
						<div class="board-name-area">
							<img src="../img/mb_e_plus.png" class="board-icon">
							<div class="board-name">掲示板名</div>
							<img src="../img/mb_2_syousai.png" class="board-menu">
						</div>
					</div>

					<div class="board-content">
						<div class="board-detail-area">
							<div class="heading">掲示板詳細</div>
							<div class="board-detail">掲示板詳細</div>
							<div class="heading">参加メンバー</div>
							<div class="board-users">

								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
								</div>
								<div class="board-user">
									<img src="../img/mb_e_plus.png" class="user-icon">
									<div class="user-name">苗字名前</div>
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

		<div class="popup-board-list">
			<div class="link-hide popup-board-bg"></div>
			<div class="popup-board-area">
				<div class="popup-board-header">
					<div class="popup-board-title">掲示板一覧</div>
					<div class="popup-board-header-items">
						<input type="text" placeholder="検索" class="popup-board-search">
						<div class="popup-board-add">新規追加</div>
					</div>
					<div class="popup-board-close">
						<img src="../img/mb_f_close.png">
					</div>
				</div>
				<div class="popup-board-content">

					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-leave">参加する</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="../img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>

				</div>
			</div>
		</div>

		<div class="popup-board-property">
			<div class="link-hide popup-property-bg"></div>
			<div class="popup-property-area">
				<div class="property-item">掲示板詳細</div>
				<div class="property-item">通知設定</div>
				<div class="property-item">掲示板から退出</div>
			</div>
		</div>

	</div>

	<script src="../js/nav.js"></script>
	<script src="../js/board.js"></script>
</body>
</html>