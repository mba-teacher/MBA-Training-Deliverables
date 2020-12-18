<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>掲示板</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/board.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 掲示板情報を受け取る --%>
<% BoardInfoBean[] bib = (BoardInfoBean[])session.getAttribute("boardInfoBean"); %>
<% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("postInfoBean"); %>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<a href="<%=request.getContextPath()%>/src/jsp/my_page.jsp">
			<%-- <%=request.getContextPath()%>/mypage --%>
				<img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon" id="my-icon">
				<%-- <img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon"> --%>
			</a>
			<%-- 同ページには遷移しない設定にしています --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			<a href="<%=request.getContextPath()%>/addressbook">
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

				<div class="board-list-area">
					<div class="board-list-header">
						<div class="header-title">参加掲示板</div>
						<input type="text" class="">
					</div>
					<div class="board-list">

						<%-- <% for (int i = 0; i < bib.length; i++ ) { %> --%>
						<div class="board-item">
							<div class="board-color"></div>
							<%-- <%= bib[i].getBoardColor() %> --%>
							<p>掲示板名<%-- <%= bib[i].getBoardCategory() %> --%></p>
						</div>
						<%-- <% } %> --%>
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
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="add_button show-board-list">
					</div>
				</div>

				<div class="board-content-area">

					<div class="board-header">
						<div class="board-name-area">
							<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="board-icon">
							<div class="board-name">掲示板名</div>
							<img src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png" class="board-menu">
						</div>
					</div>

					<div class="board-content">
						<form action="#" method="post" class="form">
							<input class="post-form" name="post" placeholder="なんでも投稿できます">
							<div class="post-detail">
								<textarea class="post-form-content" name="post-content" placeholder="なんでも投稿できます"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
									</div>
									<input type="submit" value="送信" class="post-submit">
								</div>
							</div>
						</form>

						<textarea class="search"></textarea>

						<div class="post">
							<%-- <a href="<%=request.getContextPath()%>/member"></a> --%>
							<%-- <%=request.getContextPath()%>/member --%>
							<form name="postIconForm" action="<%=request.getContextPath()%>/member" method="post">
								<a href="javascript:setMemberAndSubmit('1')">
									<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon">
								</a>
								<input type="hidden" name="memberId" value="" />
							</form>
							<div class="post-board-name">掲示板名</div>
							<div class="post-user-name">投稿者名</div>
							<div class="post-date">投稿日時</div>
							<div class="clear"></div>
							<div class="post-letter">投稿内容<br>投稿内容<br>投稿内容<br></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
									<span class="number">100</span>コメント
								</div>
								<div class="good">
									<img src="<%=request.getContextPath()%>/src/img/mb_j_good.png">
									<span class="number">100</span>確認済
								</div>
							</div>
							<div class="post-detail-button">
								<img src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png">
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
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				</div>
			</div>
		</div>

		<div class="popup-board-list"><a id="board-list-link"></a>
			<div class="link-hide popup-board-bg"></div>
			<div class="popup-board-area">
				<div class="popup-board-header">
					<div class="popup-board-title">掲示板一覧</div>
					<div class="popup-board-header-items">
						<input type="text" placeholder="検索" class="popup-board-search">
						<a href="javascript:hiddenSubmit()" class="popup-board-add">新規追加</a>
					</div>
					<div class="popup-board-close">
						<img src="<%=request.getContextPath()%>/src/img/mb_f_close.png">
					</div>
				</div>
				<div class="popup-board-content">

					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-leave">参加する</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>

				</div>
				<form name="hiddenForm" action="<%=request.getContextPath()%>/createBoard" method="post">
					<input type="hidden" name="pageType" value="create">
				</form>
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

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/board.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/method.js"></script>
</body>
</html>