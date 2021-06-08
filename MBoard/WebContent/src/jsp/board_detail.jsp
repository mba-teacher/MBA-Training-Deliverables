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
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 選択した掲示板情報をセッションから受け取る --%>
<% BoardInfoBean bib = (BoardInfoBean)session.getAttribute("BoardInfo"); %>
<%-- 選択した掲示板に参加するユーザー情報をセッションから受け取る --%>
<% ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>(); %>
<% userlist.addAll((ArrayList<UserInfoBean>) session.getAttribute("memberName")); %>

<div class="flex_container">
		<%-- 固定のリンクバー --%>
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<form name="nav-trans" method="post">

			<input type="image" src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon"
			id="my-icon" formaction="<%=request.getContextPath()%>/mypage">

			<input type="image" src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon"
			formaction="<%=request.getContextPath()%>/board">

			<input type="image" src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon"
			formaction="<%=request.getContextPath()%>/addressbook">

			<%-- 外部リンク一覧のポップアップを出すだけなので遷移先なし --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">
			</form>

			<div class="nav-bottom">
				<%-- 通知のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon" id="alarm-show">
				<%-- その他のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon" id="link-botoom-show">
			</div>

		</div>


		<div class="main">
			<div class="mypage_content">

				<div class="board-detail-content-area">

					<%-- 掲示板名表示ヘッダー --%>
					<div class="board-header header-bg color<%=bib.getBoardColor()%>">
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
							<div class="board-detail"><%= bib.getBoardContents() %></div>
							<div class="heading">メンバー</div>
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

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_LINEWORKS.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_calendar.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_drive.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_mail.png">
				</div>
			</div>
		</div>

		<div class="popup-bottom-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_config.png" id="config_img">
					<a href="<%=request.getContextPath()%>/logout">
						<img src="<%=request.getContextPath()%>/src/img/mb_0_signout.png">
					</a>
				</div>
			</div>
		</div>

	</div>

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/board.js"></script>
</body>
</html>