<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="data.UserInfoBean,data.GroupInfoBean,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アドレス帳・グループ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/addressbook_group.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 全メンバー情報とグループ情報を受け取る --%>
<% ArrayList<UserInfoBean> uib = (ArrayList<UserInfoBean>)request.getAttribute("allMembers"); %>
<% ArrayList<ArrayList<UserInfoBean>> lists = (ArrayList<ArrayList<UserInfoBean>>)request.getAttribute("groupMembers"); %>
<% ArrayList<GroupInfoBean> gn = (ArrayList<GroupInfoBean>)request.getAttribute("groupNames"); %>

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

			<%-- 同一ページには遷移しないようする --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon">

			<%-- 外部リンク一覧のポップアップを出すだけなので遷移先なし --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon"id="link-show">
			</form>

			<div class="nav-bottom">
				<%-- 通知のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon">
				<%-- その他のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon" id="link-botoom-show">
			</div>
		</div>

		<div class="main">
			<div class="addresandgroup">
				<h1>アドレス帳・グループ一覧</h1>
				<!-- <div>
        			<span id="arrowOff" onclick="chenges1()"></span>
        			<span id="arrowOn" onclick="chenges2()"></span>
    			</div> -->

				<div class="tabs">
					<input id="address" type="radio" name="tab_item" checked>
						<label class="tab_item" for="address">アドレス</label>
					<input id="group" type="radio" name="tab_item">
						<label class="tab_item" for="group">グループ</label>

					<div class="tab_content" id="address_content">
						<div class="tab_content_description">
							<input type="text" id="search-text" class="serch" name="serch" placeholder=" 検索したい名前を入力">

							<div class="address_area">
								<p class="address">アドレス一覧</p>

								<%-- 検索結果を表示するエリア --%>
								<div class="search-result" id="search-result">
									<div id="search-result__list"></div>

									<div id="noResult">
										<p id="none">該当しませんでした</p>
									</div>
								</div>

								<div id="target-area">
									<% for (int i = 0; i < uib.size(); i++) { %>
									<a class="user_information_area" href="javascript:setAndSubmit('<%=uib.get(i).getUserID()%>','postIconForm')">
										<% if (uib.get(i).getProfileImgPath() == null || uib.get(i).getProfileImgPath().isEmpty()) { %>
										<%-- 画像がないときの仮画像 --%>
										<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="user_icon">
										<% } else { %>
										<img src="<%=request.getContextPath()%><%=uib.get(i).getProfileImgPath()%>" class="user_icon">
										<% } %>
										<p class="user_name"><%= uib.get(i).getUserName() %></p>
									</a>
									<% } %>
								<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>

					<div class="tab_content" id="group_content">
						<div class="tab_content_description">
							<div class="group_area">
								<p class="address">グループ一覧</p>
								<div class="box">
									<% for (int i = 0; i < lists.size(); i++) { %>
										<div class="group_tab">
										<label for="inbox<%=i%>"><%=gn.get(i).getGroupName()%>

											<span id="arrowOff"></span>
									        <span id="arrowOn"></span>

										</label><input type="checkbox" id="inbox<%=i%>" name="check" class="on-off" onclick="chenges()">
										<div class="dropdown">
											<% for (int j = 0; j < lists.get(i).size(); j++) { %>
											<a class="user_information_area" href="javascript:setAndSubmit('<%=lists.get(i).get(j).getUserID()%>','postIconForm')">
												<% if (lists.get(i).get(j).getProfileImgPath() == null || lists.get(i).get(j).getProfileImgPath().isEmpty()) { %>
												<%-- 画像がないときの仮画像 --%>
												<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="user_icon">
												<% } else { %>
												<img src="<%=request.getContextPath()%><%=lists.get(i).get(j).getProfileImgPath()%>" class="user_icon">
												<% } %>
												<p class="user_name"><%=lists.get(i).get(j).getUserName()%></p>
											</a>
											<% } %>
										</div>
									</div>
									<div class="clear"></div>
									<% } %>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%-- 各メンバーページに飛ばす用のユーザーIDを送るためのフォーム --%>
				<form id="postIconForm" action="<%=request.getContextPath()%>/member" method="post">
					<input type="hidden" name="memberId" value="" />
				</form>
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

	<script src="<%=request.getContextPath()%>/src/js/addressbook.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/method.js"></script>

	<script>

	function chenges1(){
		const onSan = document.getElementById("arrowOn");
        const offSan = document.getElementById("arrowOff");
        onSan.style.display = "block";
        offSan.style.display = "none";
    }
        function chenges2(){
		const onSan = document.getElementById("arrowOn");
        const offSan = document.getElementById("arrowOff");
        onSan.style.display = "none";
        offSan.style.display = "block";
	 }
     function chenges(){
    	const onSan = document.getElementById("arrowOn");
        const offSan = document.getElementById("arrowOff");
        if(offSan.style.display=="block"){
        	onSan.style.display = "block";
            offSan.style.display = "none";
        }else if(oSan.style.display=="block"){
        	onSan.style.display = "none";
            offSan.style.display = "block";
        }

     }

	/* $(function(){
    	$('[name="check"]').change(function(){
        	if( $('[name="check"]').prop('checked') ){
            	alert('チェックを入れました');
            	document.getElementsByClassName("arrowOn").style.display = "block";
             	document.getElementsByClassName("arrowOff").style.display = "none";
        	}else{
    	        alert('チェックを外しました');
    	        document.getElementsByClassName("arrowOn").style.display = "none";
            	document.getElementsByClassName("arrowOff").style.display = "block";
	        }
	    });
	}); */
	</script>

</body>
</html>