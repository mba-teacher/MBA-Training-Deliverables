<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.PostInfoBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メンバーページ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/mypage.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/memberpage.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jscroll/2.4.1/jquery.jscroll.min.js"></script>
</head>
<body>

<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 選択したメンバーの情報を受け取る --%>
<% UserInfoBean uib = (UserInfoBean)request.getAttribute("memberInfoBean"); %>
<% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("memberPostInfoBean"); %>
<% int count = (int)session.getAttribute("count"); %>

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
				<div class="profile_area">
					<div>
						<% if (uib.getProfileImgPath() == null || uib.getProfileImgPath().isEmpty())  { %>
							<%-- 画像がないときの仮画像 --%>
							<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="profile_icon">
						<% } else { %>
							<img src="<%=request.getContextPath()%><%= uib.getProfileImgPath() %>" class="profile_icon">
						<% } %>
					</div>
					<h2><%= uib.getUserName() %></h2>
					<p class="address"><%= uib.getEmailAdress() %></p>
					<a href="#"><img src="<%=request.getContextPath()%>/src/img/mb_0_LINEWORKS.png" id="con-LINEWORKS"></a>
					<a href="#"><img src="<%=request.getContextPath()%>/src/img/mb_0_LINEWORKS.png" id="con-mail"></a>
				</div>

				<div class="tabs">
					<input id="profile"type="radio" name="tab_item">
						<label class="tab_item" for="profile">プロフィール</label>
					<input id="post" type="radio" name="tab_item" checked>
						<label class="tab_item" for="post">投稿した記事</label>

					<div class="tab_content" id="post_content">
					<!-- tab_content -->
						<div class="tab_content_description">
							<p class="c-txtsp"></p>

					<% if (pib != null) { %>
					<%-- pib(記事Bean)がnullではない場合 --%>
								<% for(int i = 0; i < 5; i++){ %>
									<% if (i < pib.length) { %>
									<div class="post_area">
										<div >
											<% if (pib[i].getPostUserIconPath() == null) { %>
												<%-- 画像がないときの仮画像 --%>
												<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="icon_area">
											<% } else { %>
												<img src="<%=request.getContextPath()%><%= pib[i].getPostUserIconPath() %>" class="icon_area">
											<% } %>
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
									<% count++; %>
									<% } %>
								<% } %>
							<% session.setAttribute("count", count); %>
							<%-- <% System.out.println("遷移前のcount:" + session.getAttribute("count")); %> --%>
						</div><!-- class="tab_content_description" -->
						<% if (count < pib.length) { %>
							<div class="scroll">
								<a class="jscroll-next" href="<%=request.getContextPath()%>/src/jsp/post_buff.jsp">次の記事へ</a>
							</div>
						<% } %>
					<% } else { %>
					<%-- pib(記事Bean)がnullの場合 --%>
						</div><!-- class="tab_content_description" -->
					<% } %>
					</div>

					<div class="tab_content" id="profile_content">
						<div class="tab_content_description">
							<div class="profile_area">
								<p class="c-txtsp">プロフィール内容</p>
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

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/scroll.js"></script>

</body>
</html>