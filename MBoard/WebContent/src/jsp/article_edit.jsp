<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.PostInfoBean,data.CommentInfoBean,java.util.ArrayList"%>
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
<% PostInfoBean post = (PostInfoBean)session.getAttribute("editPost"); %>
<% CommentInfoBean comment = (CommentInfoBean)session.getAttribute("editComment"); %>
<% String editType = (String)session.getAttribute("editType"); %>

<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<a href="#">
				<img src="src/img/mb_0_link.png" class="nav-icon">
			</a>
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

				<div class="article-edit-area">

					<h1 class="page-title">記事編集</h1>
					<form action="articleEdit" method="post">

						<div class="form">
							<% if(editType.equals("post")){%>
								<input class="post-form" name="post" placeholder="なんでも投稿できます" value="<%out.print(post.getPostId());%>">
							<% }else if(editType.equals("comment")){%>
								<input class="post-form" name="post" placeholder="なんでも投稿できます" value="<%out.print(comment.getCommentId());%>">
							<% }%>
							<div class="post-detail">
								<% if(editType.equals("post")){%>
									<textarea class="post-form-content" name="post-content" placeholder="なんでも投稿できます"><%= post.getPostContents() %></textarea>
								<% }else if(editType.equals("comment")){%>
									<textarea class="post-form-content" name="post-content" placeholder="なんでも投稿できます" ><%= comment.getCommentContents() %></textarea>
								<% }%>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
									</div>
								</div>
							</div>
						</div>

						<div class="submit-area">
							<input type="submit" name="delete" value="削除" class="delete">
							<input type="button" name="cancel" value="キャンセル" class="cancel">
							<input type="submit" name="save" value="保存" class="save">
						</div>

					</form>
				</div>

			</div>

		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
				<img src="src/img/mb_0_attendance.png">
				<img src="src/img/mb_0_attendance.png">
				<img src="src/img/mb_0_attendance.png">
				<img src="src/img/mb_0_attendance.png">
				<img src="src/img/mb_0_attendance.png">
			</div>
			</div>
		</div>
	</div>

	<script src="src/js/nav.js"></script>
</body>
</html>