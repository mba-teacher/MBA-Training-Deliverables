<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>プロフィール編集</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/create_board.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/profile_edit.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<a href="<%=request.getContextPath()%>/src/jsp/my_page.jsp">
				<img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon" id="my-icon">
			</a>
			<a href="<%=request.getContextPath()%>/src/jsp/board.jsp">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon">
			</a>
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
				<div class="create-area">
					<h1 class="page-title">プロフィール編集</h1>
					<form action="../../profileEdit" method="post" name="profileForm" enctype="multipart/form-data"  onsubmit="return formCheck()">
						<p>プロフィールアイコン</p>
						<div class="create-icon-area">
							<div class="create-icon">
								<img id="before" src="<%=request.getContextPath()%><%=myb.getProfileImgPath()%>">
								<img id="preview" src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" style="max-width:200px;">
								<!--  -->
							</div>
							<input type="file" name="profile-icon"  accept="image/*" id="profile-icon" value="参照" onchange="previewImage(this)" >
							<label for="profile-icon" class="browse-button">参照</label>
						</div>

						<div>
							<p>表示名<span class="requierdItem">*</span></p>
							<input type="text" name="user_name" value="<%=myb.getUserName()%>">
							<%-- placeholder="表示名"  --%>
						</div>
						<div>
							<p>メールアドレス<span class="requierdItem">*</span><span class="alertarea"></span></p>
							<input type="text" name="email_address" value="<%=myb.getEmailAdress()%>" class="email">
							<%-- placeholder="メールアドレス" --%>
						</div>
						<div>
							<p>LINEWORKSアドレス<span class="requierdItem">*</span></p>
							<input type="text" name="line_works_id" value="<%=myb.getLineWorksID()%>">
							<%-- placeholder="LINEWORKSアドレス" --%>
						</div>
						<div>
							<p>スキル</p>
							<input type="text" name="" placeholder="スキル" value="テキトーに入れてます">
						</div>
						<div>
							<p>趣味・興味</p>
							<input type="text" name="" placeholder="趣味・興味" value="テキトーに入れてます">
						</div>

						<p>自己紹介</p>
						<textarea class="create-detail" placeholder="自己紹介">データベースにないのでまだ確認できません</textarea>
						<div class="submit-area">
							<input type="button" name="" value="キャンセル" class="cancel" onclick="location.href='<%=request.getContextPath()%>/src/jsp/my_page.jsp'">
							<input type="submit" name="" value="保存" class="submit">
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
				<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
				<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
			</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/method.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/profile_edit.js"></script>
</body>
</html>