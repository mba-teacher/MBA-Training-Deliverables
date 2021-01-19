<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板作成</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/create_board.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 全メンバー情報を受け取る --%>
<% ArrayList<UserInfoBean> ulist = (ArrayList<UserInfoBean>)request.getAttribute("allMembers"); %>

	<div class="flex_container">
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
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon">
				<%-- その他のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon" id="link-botoom-show">
			</div>

		</div>

		<div class="main">

			<div class="mypage_content">
				<div class="create-area">
				<h1 class="page-title">掲示板作成</h1>
					<form action="<%=request.getContextPath()%>/createBoardAfter" method="post" name="boardForm" enctype="multipart/form-data" onsubmit="return formCheck()">
						<div class="create-icon-area">
							<div class="create-icon">
							<img id="preview" src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" style="max-width:200px;">
							</div>
							<input type="file" name="board-icon" id="board-icon" value="参照" onchange="previewImage(this)">
							<label for="board-icon" class="browse-button">参照</label>
						</div>
						<div class="board-name-area">
							<p>掲示板名<span class="requierdItem">*</span></p>
							<input type="text" name="Board_Category" placeholder="例：日報"	>
						</div>
						<div>
							<p>掲示板色選択</p>
							<select name="Board_Color" class="board-color">
								<option value="1">レッド</option>
								<option value="2">ブルー</option>
								<option value="3">グリーン</option>
							</select>
						</div>
						<p>アクセス制限<span class="requierdItem">*</span></p>
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
								<ul class="access-ul">
								<li class="access-list-item">
									<input type="checkbox" id="userall" name="allChecked" checked>
									<label for="userall" class="access-item">
										<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="access-user-icon">
										<div class="access-user-name">全員</div>
										<div class="checkbox"></div>
									</label>
								</li>
								<li><ul id="sub">
								<% for (int i = 0; i < ulist.size(); i++) { %>
								<li class="access-list-item">
									<input type="checkbox" id="user<%=ulist.get(i).getUserID()%>" name="accessList" value="<%=ulist.get(i).getUserID()%>" checked>
									<label for="user<%=ulist.get(i).getUserID()%>" class="access-item">
										<% if (ulist.get(i).getProfileImgPath() == null || ulist.get(i).getProfileImgPath() == "") { %>
										<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="access-user-icon">
										<% } else { %>
										<img src="<%=request.getContextPath()%><%=ulist.get(i).getProfileImgPath()%>" class="access-user-icon">
										<% } %>
										<div class="access-user-name"><%=ulist.get(i).getUserName()%></div>
										<div class="checkbox"></div>
									</label>
								</li>
								<% } %>
								</ul></li>
								</ul>
							</div>
						</div>
						<p>掲示板詳細</p>
						<textarea class="create-detail" name="Board_Content" placeholder="例：日報の提出用の掲示板です。"></textarea>
						<input type="hidden" name="pageType" value="create">
						<div class="submit-area">
							<input type="button" name="" value="キャンセル" class="cancel" onclick="location.href='<%=request.getContextPath()%>/src/jsp/board.jsp'">
							<input type="submit" name="" value="作成" class="submit">
						</div>
					</form>
					<p id="errortext" style="display: none; color: red;">未入力項目があります</p>
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
					<img src="<%=request.getContextPath()%>/src/img/mb_0_config.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_signout.png">
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/create_board.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/method.js"></script>
</body>
</html>