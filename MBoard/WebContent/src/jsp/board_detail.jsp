<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>掲示板詳細画面</title>
	<link rel="stylesheet" href="src/css/nav.css" />
	<link rel="stylesheet" href="src/css/board.css" />
	<link rel="stylesheet" href="src/css/board_detail.css" />
	<link rel="stylesheet" href="src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- 選択した掲示板情報をセッションから受け取る --%>
<% BoardInfoBean bib = (BoardInfoBean)session.getAttribute("BoardInfo"); %>
<%-- 選択した掲示板に参加するユーザー情報をセッションから受け取る --%>
<% ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>(); %>
<% userlist.addAll((ArrayList<UserInfoBean>) session.getAttribute("memberName")); %>

<div class="flex_container">
		<%-- 固定のリンクバー --%>
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<a href="#">
				<img src="src/img/mb_0_boad.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="src/img/mb_0_address.png" class="nav-icon">
			</a>
			<a href="#">
				<img src="src/img/mb_0_link.png" class="nav-icon" id="link-show">
			</a>

			<div class="nav-bottom">
				<a href="#">
				<img src="src/img/mb_0_notice.png" class="nav-icon">
				</a>
				<a href="#">
				<img src="src/img/mb_0_other.png" class="nav-icon">
				</a>
			</div>

		</div>


		<div class="main">
			<div class="mypage_content">

				<div class="board-detail-content-area">

					<%-- 掲示板名表示ヘッダー --%>
					<div class="board-header">
						<div class="board-name-area">
							<%-- ★↓掲示板画像のパス階層、要調整 --%>
							<img src="src/img/<%=bib.getBoardImgPath() %>.png" class="board-icon">
							<!-- <img src="src/img/mb_e_plus.png" class="board-icon"> -->
							<div class="board-name"><%= bib.getBoardCategory() %></div>
							<!-- <img src="src/img/mb_2_syousai.png" class="board-menu"> -->
						</div>
					</div>

					<%-- 掲示板詳細・参加メンバー表示 --%>
					<div class="board-content">
						<div class="board-detail-area">
							<div class="heading">掲示板詳細</div>
							<div class="board-detail">掲示板詳細
							<br><br><br><br><br><br><br><br><br>
							<br><br><br><br><br><br><br><br><br>
							<%= bib.getBoardContents() %></div>
							<div class="heading">参加メンバー</div>
							<div class="board-users">

								<% for(int i=0;i<userlist.size();i++){ %>
									<div class="board-user">
										<%-- ★↓ユーザーのアイコン画像のパス階層、要調整 --%>
										<img src="src/img/<%=userlist.get(i).getProfileImgPath() %>.png" class="user-icon">
										<div class="user-name"><%=userlist.get(i).getUserName() %></div>
									</div>
								<% } %>

							</div>
						</div>
					</div>

				</div>

			</div>
		</div>

	</div>

	<script src="src/js/nav.js"></script>
	<script src="src/js/board.js"></script>
</body>
</html>