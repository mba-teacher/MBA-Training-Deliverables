<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean,data.BoardPermissionInfoBean,data.TemplateInfoBean,java.util.ArrayList,java.util.HashMap"%>
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
<!--				セッションから情報を取得 	        -->
<!-- ログイン中のユーザー情報を取得 -->
<% UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- ユーザーIDをキーにして、そのユーザー情報を取得する連想配列を取得 --%>
<% HashMap<Integer, UserInfoBean> userIdHash = (HashMap<Integer, UserInfoBean>)session.getAttribute("userIdHash");%>
<%-- ログインユーザーの参加可能な掲示板情報をリスト配列で取得 --%>
<% ArrayList<BoardInfoBean> permissionBoardList = (ArrayList<BoardInfoBean>)session.getAttribute("permissionBoard"); %>
<%-- ログインユーザーの参加中の示板情報をリスト配列で取得 --%>
<% BoardInfoBean[] bib = (BoardInfoBean[])session.getAttribute("boardInfoBean"); %>
<%-- ログインユーザーの定型文情報をリスト配列で取得 --%>
<% ArrayList<TemplateInfoBean> TemplateList = (ArrayList<TemplateInfoBean>)session.getAttribute("TemplateInfoList"); %>
<%-- 掲示板IDをキーにして参加中か参加可能か判別する連想配列を取得 --%>
<% HashMap<Integer, Boolean> joinJudge = (HashMap<Integer, Boolean>)session.getAttribute("joinJudge"); %>
<!-- 所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)を取得 -->
<% ArrayList<PostInfoBean[]> pibList=(ArrayList<PostInfoBean[]>)session.getAttribute("postInfoBeanList");  %>
<!-- コメントIDをキーにして、その確認済み数を取得する連想配列を取得 -->
<% HashMap<Integer, Integer> readCount = (HashMap<Integer, Integer>)session.getAttribute("readCount");%>
<!-- コメントIDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列を取得 -->
<% HashMap<Integer, Integer> commentCount = (HashMap<Integer, Integer>)session.getAttribute("comentCount"); %>
<!-- コメントIDをキーにして、そのコメント数を取得する連想配列を取得 -->
<% HashMap<Integer, Boolean> userRead = (HashMap<Integer, Boolean>)session.getAttribute("userRead"); %>
<%-- 選択中の掲示板のIDを取得 --%>
<% int boardId = (int)session.getAttribute("boardId"); %>
<%-- 選択中の掲示板の値が0の場合一番上の掲示板を代入 --%>
<%if(boardId==0){
	boardId=bib[0].getBoardId();
}
%>
<%-- 選択中の掲示板の番号を取得 --%>
<% int boardNum = 0; %>
<script>
//ログインユーザーのID
var userId='<%out.print(myb.getUserID());%>';
//掲示板の名前を格納する配列
var boardName=[];
//掲示板のIDを格納する配列
var boardId=[];
//DBから取得した情報をそれぞれの配列に格納
<% for (int i = 0; i < bib.length; i++ ) { %>
boardId.push('<%out.print(bib[i].getBoardId());%>');
boardName.push('<%out.print(bib[i].getBoardCategory());%>');
<% } %>
//選択中の掲示板のID
var selectBoardId='<%out.print(boardId);%>';

//定型文の内容を格納する配列
var templateContent=[];
//DBから取得した定型文情報をそれぞれの配列に格納
<% for (int i = 0; i < TemplateList.size(); i++ ) { %>
templateContent.push('<%out.print(TemplateList.get(i).getTempleContents());%>');
<% } %>
</script>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>

			<img src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon" onclick="myPage()" id="my-icon">

			<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon" onclick="board()">

			<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon" onclick="addressBook()">

			<%-- 外部リンク一覧のポップアップを出すだけなので遷移先なし --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">

			<div class="nav-bottom">
				<%-- 通知のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon" id="alarm-show">
				<%-- その他のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon" id="link-botoom-show">
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
								<% if(bib[i].getBoardId()==boardId){ %>
									<%  boardNum = i; %>
									<li class="tab color<%=bib[i].getBoardColor()%>  is-active ">掲示板名:<%= bib[i].getBoardCategory() %></li>
								<% }else{ %>
									<li class="tab color<%=bib[i].getBoardColor()%> ">掲示板名:<%= bib[i].getBoardCategory() %></li>
								<% } %>
							<% } %>
						</ul>

					</div>
					<div class="board-list-footer">
						<div class="show-board-list">掲示板を登録</div>
						<img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="add_button show-board-list">
					</div>
				</div>

				<div class="board-content-area">
					<div class="board-header">
						<div class="board-name-area">
							<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="board-icon"> --%>
							<%-- <img src="<%=request.getContextPath()%><%= bib[0].getBoardImgPath() %>" > --%>
							<div id="boardName" class="board-name">選択中の掲示板<%-- <%= bib[0].getBoardCategory() %> --%></div>
							<img src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png" class="board-menu" >
						</div>
					</div>

					<div class="board-content">
						<form action="board" method="post" class="form" id="postForm" name="postForm" onsubmit="return postFormCheck()" >
							<input class="post-form" name="postTitle" placeholder="なんでも投稿できます">
							<div class="post-detail">
								<textarea id="post-form-content" name="postContent" placeholder="なんでも投稿できます"  wrap="soft"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<div class="template-icon">
											<img src="<%=request.getContextPath()%>/src/img/mb_2_template.png" class="template-menu">
										</div>
									</div>
									<input type="hidden" value='<%out.print(bib[0].getBoardId());%>' id="boardNameHidden" name="boardId">
									<input type="hidden" name="formName" value="makePost" >
									<input type="submit" value="投稿"  class="submit" id="postSubmit" >
								</div>
							</div>
						</form>
						<!-- <p id="errortext" style="display: none; color: red; width:calc(100% - 220px);">空白項目があります</p> -->

						<textarea class="search"></textarea>

						<!--掲示板タブを切り替えて表示する記事一覧-->
						<div id="panelGroup" class="panel-group">
						<% for (int i = 0; i < pibList.size(); i++ ) { %>
								<% if(i==boardNum){ %>
									<div class="panel is-show">
								<% }else{ %>
									<div class="panel">
								<% } %>

								<%if(pibList.get(i)==null){ %>
								 <p> まだ記事がありません</p>
								<%}else{%>
									<% for (int x = 0; x < pibList.get(i).length; x++ ) { %>
								<% int postId =pibList.get(i)[x].getPostId();%>
								<% int postUserId =pibList.get(i)[x].getPostUserId();%>
								<div class="post" name='<%out.print(postId);%>'>
									<% if (userIdHash.get(postUserId).getProfileImgPath() == null || userIdHash.get(postUserId).getProfileImgPath().isEmpty()) { %>
										<%-- 画像がないときの仮画像 --%>
										<img src="<%=request.getContextPath()%>/src/img/noimage.jpg" class="post-icon" onclick="memberPage('<%out.print(postUserId);%>')">
										<% } else { %>
										<img src="<%=request.getContextPath()%><%= userIdHash.get(postUserId).getProfileImgPath() %>" class="post-icon" onclick="memberPage('<%out.print(postUserId);%>')">
										<% } %>
										<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon">--%>
								<div class="post-board-name"><%= pibList.get(i)[x].getPostTitle() %></div>
								<div class="post-user-name"> <%=userIdHash.get(postUserId).getUserName() %></div>
								<div id="aaaaaaa" class="post-date"><%= pibList.get(i)[x].getPostDate() %></div>
								<div class="clear"></div>
								<div class="post-letter"><%= pibList.get(i)[x].getPostContents() %></div>
								<div class="post-icon-area">
									<div class="comment">
										<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
										<span class="number"><%= commentCount.get(pibList.get(i)[x].getPostId()) %></span>コメント
									</div>
									<div class="good">
										<img class="readButton" src="<%=request.getContextPath()%>/src/img/mb_j_good.png" onclick="readClick('<%out.print(pibList.get(i)[x].getPostId());%>')">
										<span id="count<%out.print(pibList.get(i)[x].getPostId());%>"><%= readCount.get(pibList.get(i)[x].getPostId()) %></span>
										<div id="read<%out.print(pibList.get(i)[x].getPostId());%>" >
											<div class="<%out.print(userRead.get(pibList.get(i)[x].getPostId()));%>">
											<% if(userRead.get(pibList.get(i)[x].getPostId())){ %>
											確認済
											<% }else{ %>

											<% } %>
											</div>
										</div>
									</div>
								</div>
								<div class="post-detail-button">
									<%-- <img src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png"> --%>
								</div>
								</div>
									<% } %>

									<%}%>
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
					<img src="<%=request.getContextPath()%>/src/img/mb_0_LINEWORKS.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_calendar.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_drive.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_mail.png">
				</div>
			</div>
		</div>

		<div class="popup-alarm-list">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="alarm-list-header">通知</div>
				<ul>
					<li class="alarm-list-item">
						<p><span class="bold">ユーザー名</span>があなたのコメントに反応しました。</p>
						<p class="alarm-detail">投稿内容、投稿内容</p>
					</li>
					<li class="alarm-list-item">
						<p><span class="bold">ユーザー名</span>があなたのコメントに反応しました。</p>
						<p class="alarm-detail">投稿内容、投稿内容</p>
					</li>
				</ul>
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

		<div class="popup-board-list">
			<div class="link-hide popup-board-bg"></div>
			<div class="popup-board-area">
				<div class="popup-board-header">
					<div class="popup-board-title">掲示板一覧</div>
					<div class="popup-board-header-items">
						<input type="text" placeholder="検索" class="popup-board-search">
						<div class="popup-board-add" onclick="createBoard()">新規作成</div>
					</div>
					<div class="popup-board-close">
						<img src="<%=request.getContextPath()%>/src/img/mb_f_close.png">
					</div>
				</div>
				<div class="popup-board-content">

					<%for(int i=0;i<permissionBoardList.size();i++){ %>
					<div class="popup-board-item">
						<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png"> --%>
						<img src="<%=request.getContextPath()%><%= permissionBoardList.get(i).getBoardImgPath() %>" >
						<p class="popup-board-name"><%= permissionBoardList.get(i).getBoardCategory() %></p>
						<%int id=permissionBoardList.get(i).getBoardId(); %>
						<%if(joinJudge.get(id)){ %>
							<div class="board-join">参加中</div>
						<%}else{ %>
							<div class="board-leave" onclick="joinBoard('<%out.print(id);%>')">参加</div>
						<%} %>
					</div>
					<%} %>

				</div>
			</div>
		</div>

		<div class="popup-board-property">
			<div class="link-hide popup-property-bg"></div>
			<div class="popup-property-area">
				<div class="property-item" onclick="boardDetail()" >掲示板詳細</div>
				<div class="property-item" >通知設定</div>
				<div class="property-item" onclick="popUp()">掲示板から退出</div>
			</div>
		</div>

		<div class="popup-template-property">
			<div class="link-hide template-property-bg"></div>
			<div class="template-property-area">
				<div class="template-item">定型文一覧　<input type="button" value="編集" onclick="template()"></input></div>
				<%for(int i=0;i<TemplateList.size();i++){ %>
				<div class="template-item" onclick="setTemplate('<%out.print(i);%>')"><%= TemplateList.get(i).getTempleName() %></div>
				<%} %>
			</div>
		</div>

		<!-- 記事投稿以外の遷移イベントはすべてこのフォームを通る。
		いいねボタン(確認済みボタン)押下時に数値は変わるが、実際にDB更新されるのは遷移される時なので、
		遷移時に毎回BoardServletを通り、いいねテーブル更新後に、サーブレットから別ページに飛ぶようにしている。 -->
		<form action="board" method="post" id="hiddenForm">
			<input type="hidden" name="formName" id="formNameHidden">
			<input type="hidden" name="postId" id="postIdHidden">
			<input type="hidden" name="boardId" id="boardIdHidden">
			<input type="hidden" name="pageType" id="pageType">
			<input type="hidden" name="memberId" id="memberId">
		</form>

		<!-- 		掲示板退出ポップアップウインドウ -->
		 <div class="modal js-modal" id="boardPop">
		     <div class="modal__bg js-modal-close"></div>
		     <div class="modal__content">
			<h2>本当に退出してもよろしいですか？</h2>
				<input type="button" value="キャンセル" class="js-modal-close modal_cancel"  onclick="popUpClose()">
				<input type="submit" value="OK"  id="" class="modal_ok" id="modal_ok" onclick="leaveBoard()">
				<input type="hidden"  name="action" id="deleteAction" value="" class="notice">
	      		<input type="hidden" id="deleteHidden" name="tempId" value="" >
		     </div>
		 </div>

		 <!-- 	記事投稿警告ポップアップウインドウ -->
		 <div class="modal js-modal" id="postPop">
		     <div class="modal__bg js-modal-close"></div>
		     <div class="modal__content">
		     <h2 style="width:calc(100% - 220px);">警告</h2>
			<h2>タイトルがありませんが投稿してよろしいですか？</h2>
				<input type="button" value="キャンセル" class="js-modal-close modal_cancel"  onclick="popUpClose()">
				<input type="submit" value="はい"  id="" class="modal_ok"  onclick="postFormSubmit()">
				<input type="hidden"  name="action" id="deleteAction" value="" class="notice">
	      		<input type="hidden" id="deleteHidden" name="tempId" value="" >
		     </div>
		 </div>

	</div>



<script>


//要素を取得 ( → <ul id="target"> ... </ul> )
var ulElement = document.getElementById( "tabGroup" ) ;
var panelGroup = document.getElementById( "panelGroup" ) ;
//最初の子要素を取得 ( → <li>要素1</li> )
var tabFirstLi = ulElement.firstElementChild ;
var panelFirst = panelGroup.firstElementChild ;
//一番上の掲示板タブを表示させる
//tabFirstLi.classList.add("is-active");
//panelFirst.className="panel is-show";





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
		//name.textContent=boardName[i];
		//表示中の掲示板のIDを格納
		selectBoardId=boardId[i];
		//掲示板IDを記事作成フォームのhiddenのvalueに代入
		//サーブレット側で受け取って、記事を追加する時に該当の掲示板を判別
		hidden.value=boardId[i];
	});
});


//フォームのサブミット二重処理防止
 $('.submit').on('click', function () {
	 if($(this).attr("id")!=="postSubmit"){
		 $(this).css('pointer-events','none');
	 }
	});

//記事投稿フォームクリック時、空白じゃないか判定
 function postFormCheck() {
	var flag = 0;
	//タイトルか内容が空白の場合、警告表示
    if( document.postForm.postTitle.value === "" ){
        flag = 1;
        postFormPopUp();
        //document.getElementById( 'errortext' ).style.display = "table";
    }else{
        //document.getElementById( 'errortext' ).style.display = "none";
    }
    if(flag == 1) {
    	return false;  //送信しない
    } else {
    	return true;   //送信実行
    }
}
//記事投稿時、警告ポップアップで「はい」押下時、投稿フォームをサブミット
 function postFormSubmit(){
 	document.postForm.submit();
 }


//記事をクリック時
$('.post').click(function(event){
	var target = $(event.target);
	if(target.attr('class')!=="readButton"&& target.attr('class')!=="post-icon"){
		var hiddenForm = document.getElementById( "hiddenForm" ) ;
		var postIdHidden = document.getElementById( "postIdHidden" ) ;
		var formNameHidden = document.getElementById( "formNameHidden" ) ;
		var boardIdHidden = document.getElementById( "boardIdHidden" );
		boardIdHidden.value=selectBoardId;
		postIdHidden.value=$(this).attr('name');
		formNameHidden.value="postDetail";
		hiddenForm.submit();
	}
});

//自分のアイコンクリック時、サーブレット経由でマイページへ遷移
function myPage(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	formNameHidden.value="myPage";
	hiddenForm.submit();
}

//固定画面の掲示板アイコンクリック時、サーブレット経由で掲示板本体画面へ遷移
function board(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	formNameHidden.value="board";
	hiddenForm.submit();
}

//固定画面のアドレス帳クリック時、サーブレット経由でアドレス帳画面へ遷移
function addressBook(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	formNameHidden.value="addressBook";
	hiddenForm.submit();
}

//新規掲示板作成をクリック時、サーブレット経由で掲示板作成画面へ遷移
function createBoard(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	var pageType = document.getElementById( "pageType" ) ;
	formNameHidden.value="createBoard";
	pageType.value="create";
	hiddenForm.submit();
}

//掲示板詳細をクリック時、サーブレット経由で掲示板詳細画面へ遷移
function boardDetail(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" );
	formNameHidden.value="boardDetail";
	boardIdHidden.value=selectBoardId;
	hiddenForm.submit();
}

//記事のプロフィールアイコンをクリック時、サーブレット経由でメンバーページ画面へ遷移
//自分のアイコンの場合、マイページに遷移
 function memberPage(id){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" );
	var memberId = document.getElementById( "memberId" );
	var name="memberPage";
	memberId.value=id;
	if(id===userId){
			name="myPage";
		}
	formNameHidden.value=name;
	hiddenForm.submit();
}

//定型文編集アイコンをクリック時、サーブレット経由で定型文編集画面へ遷移
function template(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" );
	formNameHidden.value="template";
	hiddenForm.submit();
}

//参加するボタンクリック時
function joinBoard(id){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	boardIdHidden.value=id;
	formNameHidden.value="joinBoard";
	hiddenForm.submit();
}

//「掲示板から退出」テキスト押下時
function leaveBoard(){
	//target = $(event.target);
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	boardIdHidden.value=""+selectBoardId;
	formNameHidden.value="leaveBoard";
	hiddenForm.submit();
}

//定型文を投稿フォームに代入
function setTemplate(i){
	var postFormContent = document.getElementById( "post-form-content") ;
	postFormContent.value+=templateContent[i];

}

//「掲示板から退出」テキスト押下時
/* $('.property-item').click(function(event){
	 target = $(event.target);
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	boardIdHidden.value=""+selectBoardId;
	formNameHidden.value="leaveBoard";
	alert(selectBoardId);
	hiddenForm.submit();
}); */


//確認済みボタンクリック時
function readClick(id){
	var read = document.getElementById( "read"+id ) ;
	var readCount = document.getElementById( "count"+id ) ;
	// 最初の子要素を取得
	var userRead = read.firstElementChild ;
	//
	if(userRead.className==="true"){
		var i=parseInt(readCount.textContent);
		i--;
		readCount.textContent=""+i;
		userRead.className="false";
		userRead.textContent="";
		//押されたボタンの記事IDをhiddenでわたすメソッド呼び出し
		//サーブレット側でdeleteReadを受け取ると該当の記事のログインユーザーの確認済みを削除
	 	readAction(id,"deleteRead");
	}else{
		var i=parseInt(readCount.textContent);
		i++;
		readCount.textContent=""+i;
		userRead.className="true";
		userRead.textContent="確認済";
		//押されたボタンの記事IDをhiddenでわたすメソッド呼び出し
		//サーブレット側でinsertReadを受け取ると該当の記事のログインユーザーの確認済みを追加
		readAction(id,"insertRead");
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
	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/board.js"></script>
</body>
</html>