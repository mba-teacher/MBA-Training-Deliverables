<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean,java.util.ArrayList,java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>掲示板</title>
	<link rel="stylesheet" href="src/css/nav.css" />
	<link rel="stylesheet" href="src/css/board.css" />
	<link rel="stylesheet" href="src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%-- ユーザー自身のユーザー情報をセッションから受け取る --%>
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- 掲示板情報を受け取る --%>
<% BoardInfoBean[] bib = (BoardInfoBean[])session.getAttribute("boardInfoBean"); %>
<% ArrayList<PostInfoBean[]> pibList=(ArrayList<PostInfoBean[]>)session.getAttribute("postInfoBeanList");  %>
<% HashMap<Integer, Integer> readCount = (HashMap<Integer, Integer>)session.getAttribute("readCount");%>
<% HashMap<Integer, Integer> commentCount = (HashMap<Integer, Integer>)session.getAttribute("comentCount"); %>
<% HashMap<Integer, Boolean> userRead = (HashMap<Integer, Boolean>)session.getAttribute("userRead"); %>
<%-- <% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("postInfoBean"); %> --%>
<script>
var int boardNameList;
<% for (int i = 0; i < bib.length; i++ ) { %>
var a="<%out.print(pibList.get(1)[1].getPostTitle());%>";
<% } %>
</script>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<a href="#">
				<%-- <img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon"> --%>
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

				<div class="board-list-area">
					<div class="board-list-header">
						<div class="header-title">参加掲示板</div>
						<input type="text" class="">
					</div>
					<div class="board-list">

<%-- 						<% for (int i = 0; i < bib.length; i++ ) { %>
						<div class="board-item">
							<div class="board-color<%= bib[i].getBoardColor() %>"></div>
							<p>掲示板名:<%= bib[i].getBoardCategory() %></p>
						</div>
						<% } %> --%>

						<!--タブ-->
						<ul id="tabGroup" class="tab-group">
							<% for (int i = 0; i < bib.length; i++ ) { %>
								<li class="tab">掲示板名:<%= bib[i].getBoardCategory() %></li>
							<% } %>
						</ul>

					</div>
					<div class="board-list-footer">
						<div class="show-board-list">掲示板を登録</div>
						<img src="src/img/mb_e_plus.png" class="add_button show-board-list">
					</div>
				</div>

				<div class="board-content-area">

					<div class="board-header">
						<div class="board-name-area">
							<img src="src/img/mb_e_plus.png" class="board-icon">
							<div id="boardName" class="board-name">掲示板名</div>
							<img src="src/img/mb_2_syousai.png" class="board-menu">

							<form action="board" method="post" name="form" id="testForm">
							 <input class="board_test" name="action" type="submit" value="テスト" >
							</form>

						</div>
					</div>

					<div class="board-content">
						<form action="#" method="post" class="form">
							<input class="post-form" name="post" placeholder="なんでも投稿できます">
							<div class="post-detail">
								<textarea class="post-form-content" name="post-content" placeholder="なんでも投稿できます"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
									</div>
									<input type="submit" value="送信" class="post-submit">
								</div>
							</div>
						</form>

						<textarea class="search"></textarea>

						<!--タブを切り替えて表示するコンテンツ-->
						<div id="panelGroup" class="panel-group">
						<% for (int i = 0; i < pibList.size(); i++ ) { %>
								<div class="panel">
									<% for (int x = 0; x < pibList.get(i).length; x++ ) { %>
								<div class="post">
								<img src="src/img/mb_e_plus.png" class="post-icon">
								<div class="post-board-name"><%= pibList.get(i)[x].getPostTitle() %></div>
								<div class="post-user-name">投稿者名</div>
								<div class="post-date">投稿日時</div>
								<div class="clear"></div>
								<div class="post-letter"><%= pibList.get(i)[x].getPostContents() %></div>
								<div class="post-icon-area">
									<div class="comment">
										<img src="src/img/mb_i_comment.png">
										<span class="number"><%= commentCount.get(pibList.get(i)[x].getPostId()) %></span>コメント
									</div>
									<div class="good">
										<img src="src/img/mb_j_good.png" onclick="imgwin('<%out.print(pibList.get(i)[x].getPostId());%>')">
										<span id="count<%out.print(pibList.get(i)[x].getPostId());%>"><%= readCount.get(pibList.get(i)[x].getPostId()) %></span>
										<div id="read<%out.print(pibList.get(i)[x].getPostId());%>" >
											<div class="<%out.print(userRead.get(pibList.get(i)[x].getPostId()));%>">
											<% if(userRead.get(pibList.get(i)[x].getPostId())){ %>
											確認済
											<% }else{ %>
											未確認
											<% } %>
											</div>
										</div>
									</div>
								</div>
								<div class="post-detail-button">
									<img src="src/img/mb_2_syousai.png">
								</div>
								</div>
									<% } %>
								</div>
							<% } %>



						</div>

					</div>

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
						<img src="src/img/mb_f_close.png">
					</div>
				</div>
				<div class="popup-board-content">

					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-leave">参加する</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
						<p class="popup-board-name">掲示板名</p>
						<div class="board-join">参加中</div>
					</div>
					<div class="popup-board-item">
						<img src="src/img/mb_e_plus.png">
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

<script>
//要素を取得 ( → <ul id="target"> ... </ul> )
var ulElement = document.getElementById( "tabGroup" ) ;
var panelGroup = document.getElementById( "panelGroup" ) ;
// 最初の子要素を取得 ( → <li>要素1</li> )
var tabFirstLi = ulElement.firstElementChild ;
var panelFirst = panelGroup.firstElementChild ;
tabFirstLi.className="tab is-active";
panelFirst.className="panel is-show";

jQuery(function($){
	$('.tab').click(function(){
		$('.is-active').removeClass('is-active');
		$(this).addClass('is-active');
		$('.is-show').removeClass('is-show');
        // クリックしたタブからインデックス番号を取得
		const index = $(this).index();
		boardNum=parseInt(index, 10);
        // クリックしたタブと同じインデックス番号をもつコンテンツを表示
		$('.panel').eq(index).addClass('is-show');
		//var boardName = document.getElementById( "boardName" ) ;
		<%-- boardName.textContent="<%out.print(pibList.get(1)[1].getPostTitle());%>"; --%>

	});
});

function imgwin(img){
	var read = document.getElementById( "read"+img ) ;
	var readCount = document.getElementById( "count"+img ) ;
	// 最初の子要素を取得
	var userRead = read.firstElementChild ;

	if(userRead.className==="true"){
		var i=parseInt(readCount.textContent);
		i--;
		readCount.textContent=""+i;
		userRead.className="false";
		userRead.textContent="未確認";

	 	readAction(img,"deleteRead");
	}else{
		/* var test1 = document.getElementsByName( "test111");
		test1[0].remove(); */
 		var i=parseInt(readCount.textContent);
		i++;
		readCount.textContent=""+i;
		userRead.className="true";
		userRead.textContent="確認済";

		readAction(img,"insertRead");
	}
}

function readAction(postId, name) {
	var ele=document.getElementsByClassName("hidden"+postId);
	if(ele[0]){
		var len=ele.length
 		for(var i=0;i<len;i++){
 			ele[0].remove();
		}

	}else{
		insertHidden(postId, name);
	}
	//alert(ele[0]);
	}

function insertHidden(postId, name) {
	var form = document.getElementById("testForm") ;
	var input = document.createElement('input');
	input.type="hidden";
	input.name=name;
	input.className="hidden"+postId;
	input.value=postId;
	form.insertAdjacentElement('beforeend',input);
}


</script>
	<script src="src/js/nav.js"></script>
	<script src="src/js/board.js"></script>
</body>
</html>