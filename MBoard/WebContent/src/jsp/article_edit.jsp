<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.PostInfoBean,data.CommentInfoBean,data.UserInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>記事編集</title>
	<link rel="stylesheet" href="src/css/nav.css" />
	<link rel="stylesheet" href="src/css/article_edit.css" />
	<link rel="stylesheet" href="src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<!--				セッションから情報を取得 	        -->
<!-- 編集する記事の情報を取得 -->
<% PostInfoBean post = (PostInfoBean)session.getAttribute("editPost"); %>
<!-- 編集するコメントの情報を取得 -->
<% CommentInfoBean comment = (CommentInfoBean)session.getAttribute("editComment"); %>
<!-- 編集するものが記事かコメントか判別する変数を取得 -->
<% String editType = (String)session.getAttribute("editType"); %>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>

<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<form name="nav-trans" method="post">

			<input type="image" src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon"
			id="my-icon" formaction="<%=request.getContextPath()%>/mypage" value="">

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
				<% if(editType.equals("comment")){%>
					<h1 class="big-title">コメント編集</h1>
				<% } else {%>
					<h1 class="big-title">記事編集</h1>
				<% }%>
				<div class="article-edit-area">


					<% if(editType.equals("post")){%>
						<p class="page-title">記事編集</p>
					<% }else if(editType.equals("comment")){%>
						<p class="page-title">コメント編集</p>
					<% }%>
					<form action="articleEdit" method="post" name="form" onsubmit="return check_data()">

						<div class="form">
							<div class="post-titlebar">
								<% if(editType.equals("post")){%>
									<input class="post-form" name="post" placeholder="なんでも投稿できます" value="<%out.print(post.getPostTitle());%>">
									<!-- <select class="post-forboard"></select> -->
									<p class="post-date"><%=post.getPostDate()%></p>
								<% }else if(editType.equals("comment")){ %>
								<input class="post-form" name="post" value="コメント内容" readonly>
									<!-- <select class="post-forboard"></select> -->
									<p class="post-date"><%=comment.getCommentDate()%></p>
								<%} %>
							</div>
							<div class="post-detail">
								<% if(editType.equals("post")){%>
									<textarea class="post-form-content" name="post-content" placeholder="編集できます"><%= post.getPostContents().replace("<br>", "\n") %></textarea>
								<% }else if(editType.equals("comment")){%>
									<textarea class="post-form-content" name="post-content" placeholder="編集できます" ><%= comment.getCommentContents().replace("<br>", "\n") %></textarea>
								<% }%>
								<div class="post-option">
									<div class="post-option-icon"><!--
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png"> -->
									</div>
								</div>
							</div>
						</div>

						<div class="submit-area">
							<input type="submit" name="delete" id="deleteButton" value="削除" class="delete" onclick="form.key.value='pop'">
							<%-- <a href="<%=request.getContextPath()%>/postDetail"> --%>
							<input type="button" name="cancel" value="キャンセル" class="cancel" onclick="location.href='<%=request.getContextPath()%>/postDetail'">
							<%--</a> --%>
							<input type="submit" name="save" value="保存" class="save" onclick="form.key.value='save'">
							<input name="key" type="hidden" value="" />
						</div>

					</form>
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

			<!-- 		掲示板退出ポップアップウインドウ -->
		 <div class="modal js-modal" id="deletePop">
		     <div class="modal__bg js-modal-close"></div>
		     <div class="modal__content">
			<h2>記事を削除してよろしいですか？</h2>
				<input type="button" value="キャンセル" class="js-modal-close modal_cancel"  onclick="popUpClose()">
				<input type="button" value="はい"  id="" class="modal_ok" id="modal_ok" onclick="deleteSubmit()">
				<input type="hidden"  name="action" id="deleteAction" value="" class="notice">
	      		<input type="hidden" id="deleteHidden" name="tempId" value="" >
		     </div>
		 </div>

	<script >

	//掲示板退出ポップアップ表示
	//掲示板詳細プロパティの「掲示板を退出」押下時に呼び出し
	function popUp() {
		pop=true;
		$('#deletePop').fadeIn();
	}
	function popUpClose() {
		pop=false;
		$('.js-modal').fadeOut();
	}
	$(function() {
		$('.js-modal-close').on('click', function() {
			$('.js-modal').fadeOut();
			return false;
		});
	});

	function check_data() {
	    if (form.key.value === 'pop') {
	    	popUp();
	    	return false;
	    }else{
	    	return true;
	    }
	}

	//削除ポップアップで「はい」押下時、記事削除
	function deleteSubmit() {
		form.key.value='delete';
		form.submit();
	}

	</script>
	<script src="src/js/nav.js"></script>
</body>
</html>