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
<%--
<% ArrayList<PostInfoBean[]> pibList=(ArrayList<PostInfoBean[]>)session.getAttribute("postInfoBeanList");  %>
<% HashMap<Integer, Integer> readCount = (HashMap<Integer, Integer>)session.getAttribute("readCount");%>
<% HashMap<Integer, Integer> commentCount = (HashMap<Integer, Integer>)session.getAttribute("comentCount"); %>
<% HashMap<Integer, Boolean> userRead = (HashMap<Integer, Boolean>)session.getAttribute("userRead"); %>
 --%>
<%-- <% PostInfoBean[] pib = (PostInfoBean[])session.getAttribute("postInfoBean"); %> --%>
<script>
var boardName=[];
var boardId=[];
<% for (int i = 0; i < bib.length; i++ ) { %>
boardId.push('<%out.print(bib[i].getBoardId());%>');
boardName.push('<%out.print(bib[i].getBoardCategory());%>');
<% } %>
</script>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<a href="<%=request.getContextPath()%>/src/jsp/my_page.jsp">
				<img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon">
			</a>
			<%-- ↓同一画面には遷移しないようaタグを外します --%>
				<img src="src/img/mb_0_boad.png" class="nav-icon">

			<a href="<%=request.getContextPath()%>/addressbook">
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
						<!--切り替え可能な掲示板タブ-->
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
							<div id="boardName" class="board-name"><%= bib[0].getBoardCategory() %></div>
							<img src="src/img/mb_2_syousai.png" class="board-menu">
						</div>
					</div>

					<div class="board-content">
						<form action="board" method="post" class="form" id="postForm">
							<input class="post-form" name="postTitle" placeholder="なんでも投稿できます">
							<div class="post-detail">
								<textarea class="post-form-content" name="postContent" placeholder="なんでも投稿できます"  wrap="hand"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
									</div>
									<input type="hidden" value='<%out.print(bib[0].getBoardId());%>' id="boardNameHidden" name="boardId">
									<input type="hidden" name="formName" value="makePost" >
									<input type="submit" value="送信" class="submit">
								</div>
							</div>
						</form>

						<textarea class="search"></textarea>

						<!--掲示板タブを切り替えて表示する記事一覧-->
						<div id="panelGroup" class="panel-group">
<%--
						<% for (int i = 0; i < pibList.size(); i++ ) { %>
								<div class="panel">
									<% for (int x = 0; x < pibList.get(i).length; x++ ) { %>
								<div class="post" name='<%out.print(pibList.get(i)[x].getPostId());%>'>
								<img src="src/img/mb_e_plus.png" class="post-icon">
								<div class="post-board-name"><%= pibList.get(i)[x].getPostTitle() %></div>
								<div class="post-user-name">投稿者名</div>
								<div id="aaaaaaa" class="post-date">投稿日時</div>
								<div class="clear"></div>
								<div class="post-letter"><%= pibList.get(i)[x].getPostContents() %></div>
								<div class="post-icon-area">
									<div class="comment">
										<img src="src/img/mb_i_comment.png">
										<span class="number"><%= commentCount.get(pibList.get(i)[x].getPostId()) %></span>コメント
									</div>
									<div class="good">
										<img class="readButton" src="src/img/mb_j_good.png" onclick="imgwin('<%out.print(pibList.get(i)[x].getPostId());%>')">
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
 --%>


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

<!-- 記事投稿以外の遷移イベントはすべてこのフォームを通る。
	いいねボタン(確認済みボタン)押下時に数値は変わるが、実際にDB更新されるのは遷移される時なので、
	遷移時に毎回BoardServletを通り、いいねテーブル更新後に、サーブレットから別ページに飛ぶようにしている。 -->
	<form action="board" method="post" id="hiddenForm">
		<input type="hidden" name="formName" id="formNameHidden">
		<input type="hidden" name="postId" id="postIdHidden">
	</form>

<script>
//要素を取得 ( → <ul id="target"> ... </ul> )
var ulElement = document.getElementById( "tabGroup" ) ;
var panelGroup = document.getElementById( "panelGroup" ) ;
// 最初の子要素を取得 ( → <li>要素1</li> )
var tabFirstLi = ulElement.firstElementChild ;
var panelFirst = panelGroup.firstElementChild ;
//一番上の掲示板タブを表示させる
tabFirstLi.className="tab is-active";
panelFirst.className="panel is-show";


//掲示板タブを切り替えて、記事一覧パネルを切り替える
jQuery(function($){
	//掲示板タブクリック時
	$('.tab').click(function(){
		$('.is-active').removeClass('is-active');
		$(this).addClass('is-active');
		$('.is-show').removeClass('is-show');
        // クリックしたタブからインデックス番号を取得
		const index = $(this).index();
        // クリックしたタブと同じインデックス番号をもつコンテンツを表示
		$('.panel').eq(index).addClass('is-show');

        //掲示板の名前を表示している要素取得
		var name = document.getElementById( "boardName" ) ;
        //記事作成フォームの中のhidden要素取得
		var hidden = document.getElementById( "boardNameHidden" ) ;
		//インデックス番号をint型にする
		var i= parseInt($(this).index());
		//掲示板の名前を上に表示
		name.textContent=boardName[i];
		//掲示板IDを記事作成フォームのhiddenのvalueに代入
		//サーブレット側で受け取って、該当の掲示板に記事を追加
		hidden.value=boardId[i];
	});
});


//フォームのサブミット二重処理防止
$('.submit').on('click', function () {
	  $(this).css('pointer-events','none');
	});


//記事をクリック時
$('.post').click(function(event){
	var target = $(event.target);
	if(target.attr('class')!=="readButton"){
		var hiddenForm = document.getElementById( "hiddenForm" ) ;
		var postIdHidden = document.getElementById( "postIdHidden" ) ;
		var formNameHidden = document.getElementById( "formNameHidden" ) ;
		postIdHidden.value=$(this).attr('name');
		formNameHidden.value="postDetail";
		hiddenForm.submit();
	}
});


//確認済みボタンクリック時
function imgwin(img){
	var read = document.getElementById( "read"+img ) ;
	var readCount = document.getElementById( "count"+img ) ;
	// 最初の子要素を取得
	var userRead = read.firstElementChild ;
	//
	if(userRead.className==="true"){
		var i=parseInt(readCount.textContent);
		i--;
		readCount.textContent=""+i;
		userRead.className="false";
		userRead.textContent="未確認";
		//押されたボタンの記事IDをhiddenでわたすメソッド呼び出し
		//サーブレット側でdeleteReadを受け取ると該当の記事のログインユーザーの確認済みを削除
	 	readAction(img,"deleteRead");
	}else{
 		var i=parseInt(readCount.textContent);
		i++;
		readCount.textContent=""+i;
		userRead.className="true";
		userRead.textContent="確認済";
		//押されたボタンの記事IDをhiddenでわたすメソッド呼び出し
		//サーブレット側でinsertReadを受け取ると該当の記事のログインユーザーの確認済みを追加
		readAction(img,"insertRead");
	}
}


//確認済みボタンのhidden処理
function readAction(postId, name) {
	//押された記事IDの確認済みhiddenを取得
	var ele=document.getElementsByClassName("hidden"+postId);
	if(ele[0]){
		//確認済みhiddenが既に存在した場合hidden削除
		//(ページにきて２回目に確認ボタン押していた場合取り消し。DB情報は変更しなくていいため。)
		var len=ele.length
 		for(var i=0;i<len;i++){
 			ele[0].remove();
		}
	}else{
		//押された確認済みボタンがページにきて初めての場合はその情報をhiddenでフォームに追加
		//フォームの数分hidden追加メソッド呼び出し
		insertHidden("postForm",postId, name);
		insertHidden("hiddenForm",postId, name);
	}
	}

//フォームにhidden追加メソッド
function insertHidden(formName,postId, name) {
	var form = document.getElementById(formName) ;
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