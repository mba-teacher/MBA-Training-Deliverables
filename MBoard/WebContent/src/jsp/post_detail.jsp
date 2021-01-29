<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean,data.CommentInfoBean,java.util.ArrayList,java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>会話詳細</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/nav.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/board.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/post_detail.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<!--				セッションから情報を取得 	        -->
<!-- ログイン中のユーザー情報を取得 -->
<% UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean"); %>
<%-- ユーザーIDをキーにして、そのユーザー情報を取得する連想配列を取得 --%>
<% HashMap<Integer, UserInfoBean> userIdHash = (HashMap<Integer, UserInfoBean>)session.getAttribute("userIdHash");%>
<!-- 詳細表示している、メインの一番上の記事の情報を取得 -->
<% PostInfoBean post = (PostInfoBean)session.getAttribute("postBean"); %>
<!-- 詳細表示している、メインの一番上のコメントの情報を取得 -->
<% CommentInfoBean detailComment = (CommentInfoBean)session.getAttribute("commentBean"); %>
<!-- 詳細表示しているものが記事かコメントか判別する変数を取得 -->
<% String detailType = (String)session.getAttribute("detailType"); %>
<!-- 詳細表示している記事orコメントのコメントをリスト配列で取得 -->
<% ArrayList<CommentInfoBean> comment = (ArrayList<CommentInfoBean>)session.getAttribute("CommentInfoList"); %>
<!-- 詳細表示している記事orコメントのコメントのコメントを２次元リスト配列で取得 -->
<% ArrayList<ArrayList<CommentInfoBean>> commentChain= (ArrayList<ArrayList<CommentInfoBean>>)session.getAttribute("CommentChainList"); %>
<!-- コメントIDをキーにして、その確認済み数を取得する連想配列を取得 -->
<% HashMap<Integer, Integer> readCount= (HashMap<Integer, Integer>)session.getAttribute("readCount"); %>
<!-- コメントIDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列を取得 -->
<% HashMap<Integer, Boolean> userRead= (HashMap<Integer, Boolean>)session.getAttribute("userRead"); %>
<!-- コメントIDをキーにして、そのコメント数を取得する連想配列を取得 -->
<% HashMap<Integer, Integer> commentCount= (HashMap<Integer, Integer>)session.getAttribute("commentCount"); %>
<%-- ログインユーザーの参加中の掲示板情報をリスト配列で取得 --%>
<% BoardInfoBean[] bib = (BoardInfoBean[])session.getAttribute("boardInfoBean"); %>
<%-- ログインユーザーの参加可能な掲示板情報をリスト配列で取得 --%>
<% ArrayList<BoardInfoBean> permissionBoardList = (ArrayList<BoardInfoBean>)session.getAttribute("permissionBoard"); %>
<%-- 掲示板IDをキーにして参加中か参加可能か判別する連想配列を取得 --%>
<% HashMap<Integer, Boolean> joinJudge = (HashMap<Integer, Boolean>)session.getAttribute("joinJudge"); %>
<%-- 選択中の掲示板のIDを取得 --%>
<% int boardId = (int)session.getAttribute("boardId"); %>
<%-- 選択中の掲示板の名前を取得 --%>
<% String boardCategory=""; %>
<script>
//ログインユーザーのID
var userId='<%out.print(userInfo.getUserID());%>';
</script>

	<div class="flex_container">
		<div class="nav-area">
			<div class="logo-area">
				<img src="<%=request.getContextPath()%>/src/img/logo_white.png">
			</div>
			<!-- <a href="#"> -->
				<img src="<%=request.getContextPath()%><%= userInfo.getProfileImgPath() %>" class="nav-icon" onclick="myPage()" id="my-icon">
			<!-- </a> -->
			<%-- <a href="<%=request.getContextPath()%>/board"> --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon" onclick="board()">
			<!-- </a> -->
			<!-- <a href=""> -->
				<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon" onclick="addressBook()">
			<!-- </a> -->
			<%-- 外部リンク一覧のポップアップを出すだけなので遷移先なし --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">

			<div class="nav-bottom">
				<%-- 通知のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon">
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
									<% boardCategory=bib[i].getBoardCategory(); %>
									<li class="tab color<%=bib[i].getBoardColor()%>  is-active " name="<%=bib[i].getBoardId()%>">掲示板名:<%= bib[i].getBoardCategory() %></li>
								<% }else{ %>
									<li class="tab color<%=bib[i].getBoardColor()%> " name="<%=bib[i].getBoardId()%>">掲示板名:<%= bib[i].getBoardCategory() %></li>
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
							<div class="board-name">選択中の掲示板<%-- <%=boardCategory%> --%></div>
							<img src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png" class="board-menu">
						</div>
					</div>

						<form action="postDetail" method="post" class="form" id="postForm">
							<input class="post-form" name="postTitle" placeholder="返信する" readonly>
							<div class="post-detail">
								<textarea id="post-form-content" name="postContent" placeholder="返信内容"  wrap="hand"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
 										<img src="<%=request.getContextPath()%>/src/img/mb_2_template.png">
										<%--<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png">
										<img src="<%=request.getContextPath()%>/src/img/mb_g_letteredit.png"> --%>
									</div>
									<%if(detailType.equals("comment")){%>
									<input type="hidden" name="postId" value="<%out.print(detailComment.getCommentId());%>" >
									<%}%>
									<input type="hidden" name="formName" value="makeComment" >
									<input type="submit" value="投稿" class="submit">
								</div>
							</div>
						</form>

					<div class="board-content">
					<%if(detailType.equals("post")){%>
							<!-- 記事IDとコメントIDのかぶり防止に記事IDを負の値にしている -->
							<%int postId=post.getPostId()*-1;%>
							<%int postUserId=post.getPostUserId();%>
						<div class="post" name="mainDtail" id="<%out.print(postId);%>">
						<img src="<%=request.getContextPath()%><%= userIdHash.get(postUserId).getProfileImgPath() %>" class="post-icon" onclick="memberPage('<%out.print(postUserId);%>')">
							<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon"> --%>
							<div class="post-board-name"><%= post.getPostTitle() %></div>
							<div class="post-user-name"><%=userIdHash.get(postUserId).getUserName() %></div>
							<div class="post-date"><%=post.getPostDate()%></div>
							<div class="clear"></div>
							<div class="post-letter"><%=post.getPostContents()%><br></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
									<span class="number"><%= session.getAttribute("postCommentCount") %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="<%=request.getContextPath()%>/src/img/mb_j_good.png" onclick="readClick('<%out.print(postId);%>')">
									<span id="count<%out.print(postId);%>"><%= session.getAttribute("postReadCount") %></span>
										<div id="read<%out.print(postId);%>" >
											<div class="<%out.print((boolean)session.getAttribute("postUserRead"));%>">
											<% if((boolean)session.getAttribute("postUserRead")){ %>
											確認済
											<% }else{ %>

											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(post.getPostUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(postId);%>" class="post-detail-button" src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>
						<%}else if(detailType.equals("comment")){%>
						<% int detailId=detailComment.getCommentId();%>
						<% int detailUserId=detailComment.getCommentUserId();%>
						<div class="post" name="mainDtail" id="<%out.print(detailId);%>">
							<img src="<%=request.getContextPath()%><%= userIdHash.get(detailUserId).getProfileImgPath() %>" class="post-icon" onclick="memberPage('<%out.print(detailUserId);%>')">
							<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon"> --%>
							<div class="post-board-name">コメント</div>
							<div class="post-user-name"><%=userIdHash.get(detailUserId).getUserName() %></div>
							<div class="post-date"><%= detailComment.getCommentDate() %></div>
							<div class="clear"></div>
							<div class="post-letter"><%=detailComment.getCommentContents()%><br></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
									<span class="number"><%= session.getAttribute("detailCommentCount") %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="<%=request.getContextPath()%>/src/img/mb_j_good.png" onclick="readClick('<%out.print(detailId);%>')">
									<span id="count<%out.print(detailId);%>"><%= session.getAttribute("postReadCount") %></span>
										<div id="read<%out.print(detailId);%>" >
											<div class="<%out.print((boolean)session.getAttribute("postUserRead"));%>">
											<% if((boolean)session.getAttribute("postUserRead")){ %>
											確認済
											<% }else{ %>

											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(detailComment.getCommentUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(detailId);%>" class="post-detail-button" src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>
						<%}%>

						<% for (int i = 0; i < comment.size(); i++ ) { %>
						<%int commentId= comment.get(i).getCommentId();%>
						<% int commentUserId=comment.get(i).getCommentUserId();%>
						<div class="post post-comment" id="<%out.print(commentId);%>">
						<img src="<%=request.getContextPath()%><%= userIdHash.get(commentUserId).getProfileImgPath() %>" class="post-icon" onclick="memberPage('<%out.print(commentUserId);%>')">
							<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon"> --%>
							<%-- <div class="post-board-name"><%= commentId %></div> --%>
							<%-- <div class="post-comment-for"><%= post.getPostTitle() %>へのコメント</div> --%>
							<div class="post-user-name"><%=userIdHash.get(commentUserId).getUserName() %></div>
							<div class="post-date"><%= comment.get(i).getCommentDate() %></div>
							<div class="clear"></div>
							<div class="post-letter"><%= comment.get(i).getCommentContents() %></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
									<span class="number"><%= commentCount.get(commentId) %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="<%=request.getContextPath()%>/src/img/mb_j_good.png" onclick="readClick('<%out.print(commentId);%>')">
									<span id="count<%out.print(commentId);%>"><%= readCount.get(commentId) %></span>
										<div id="read<%out.print(commentId);%>" >
											<div class="<%out.print(userRead.get(commentId));%>">
											<% if(userRead.get(commentId)){ %>
											確認済
											<% }else{ %>

											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(comment.get(i).getCommentUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(commentId);%>" class="post-detail-button" src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>

							<% for (int x = 0; x < commentChain.get(i).size(); x++ ) { %>
							<%int commentChainId= commentChain.get(i).get(x).getCommentId();%>
							<% int chainUserId=commentChain.get(i).get(x).getCommentUserId();%>
							<div class="tree-area">
							<div class="comment-branch">
								<div class="branch-border border-top"></div>
								<%if(x+1!=commentChain.get(i).size()){ %>
								<div class="branch-border border-bottom"></div>
								<%} %>
							</div>
							<div class="post post-comment post-tree" id="<%out.print(commentChainId);%>">
							<img src="<%=request.getContextPath()%><%= userIdHash.get(chainUserId).getProfileImgPath() %>" class="post-icon" onclick="memberPage('<%out.print(chainUserId);%>')">
								<%-- <img src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" class="post-icon"> --%>
								<%-- <div class="post-board-name"><%= commentChainId %></div> --%>
								<%-- <div class="post-comment-for"><%= post.getPostTitle() %>へのコメントのコメント</div> --%>
								<div class="post-user-name"><%=userIdHash.get(chainUserId).getUserName() %></div>
								<div class="post-date"><%= commentChain.get(i).get(x).getCommentDate() %></div>
								<div class="clear"></div>
								<div class="post-letter"><%= commentChain.get(i).get(x).getCommentContents() %></div>
								<div class="post-icon-area">
									<div class="comment">
										<img src="<%=request.getContextPath()%>/src/img/mb_i_comment.png">
										<span class="number"> <%=commentCount.get(commentChainId) %></span>コメント
									</div>
									<div class="good">
										<img class="readButton" src="<%=request.getContextPath()%>/src/img/mb_j_good.png" onclick="readClick('<%out.print(commentChainId);%>')">
										<span id="count<%out.print(commentChainId);%>"><%= readCount.get(commentChainId) %></span>
										<div id="read<%out.print(commentChainId);%>" >
											<div class="<%out.print(userRead.get(commentChainId));%>">
											<% if(userRead.get(commentChainId)){ %>
											確認済
											<% }else{ %>

											<% } %>
											</div>
										</div>
									</div>
								</div>
								<div>
									<% if(commentChain.get(i).get(x).getCommentUserId()==userInfo.getUserID()){ %>
									<img  id="<%out.print(commentChainId);%>" class="post-detail-button" src="<%=request.getContextPath()%>/src/img/mb_2_syousai.png">
									<% } %>

								</div>
							</div>
						</div>
							<% } %>
						<% } %>

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
				<div class="property-item" onclick="boardDetail()">掲示板詳細</div>
				<div class="property-item">通知設定</div>
				<div class="property-item" onclick="popUp()">掲示板から退出</div>
			</div>
		</div>

		<!-- 記事投稿以外の遷移イベントはすべてこのフォームを通る。
		いいねボタン(確認済みボタン)押下時に数値は変わるが、実際にDB更新されるのは遷移される時なので、
		遷移時に毎回BoardServletを通り、いいねテーブル更新後に、サーブレットから別ページに飛ぶようにしている。 -->
		<form action="postDetail" method="post" id="hiddenForm">
			<input type="hidden" name="formName" id="formNameHidden">
			<input type="hidden" name="postId" id="postIdHidden">
			<input type="hidden" name="boardId" id="boardIdHidden">
			<input type="hidden" name="pageType" id="pageType">
			<input type="hidden" name="memberId" id="memberId">
		</form>

		<!-- 		ポップアップウインドウ -->
		 <div class="modal js-modal" id="boardPop">
		     <div class="modal__bg js-modal-close"></div>
		     <div class="modal__content">
			<h2>本当に退出してもよろしいですか？</h2>
				<input type="button" value="キャンセル" class="js-modal-close modal_cancel"  onclick="popUpClose()">
				<input type="submit" value="OK"  id="" class="modal_ok" onclick="leaveBoard()">
				<input type="hidden"  name="action" id="deleteAction" value="" class="notice">
	      		<input type="hidden" id="deleteHidden" name="tempId" value="" >
		     </div>
		 </div>


	</div>

<script>
//フォームのサブミット二重処理防止
$('.submit').on('click', function () {
	  $(this).css('pointer-events','none');
	});

//記事をクリック時
$('.post').click(function(event){
	var target = $(event.target);
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var postIdHidden = document.getElementById( "postIdHidden" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	if(target.attr('class')==="post-detail-button"){
		postIdHidden.value=$(this).attr('id');
		formNameHidden.value="postEdit";
		hiddenForm.submit();
	}else if(target.attr('class')!=="readButton" && $(this).attr('name')!=="mainDtail"&& target.attr('class')!=="post-icon"){
		postIdHidden.value=$(this).attr('id');
		formNameHidden.value="commentDetail";
		hiddenForm.submit();
	}
});

//掲示板タブクリック時
$('.tab').click(function(event){
	var target = $(event.target);
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" );
	formNameHidden.value="selectBorad";
	boardIdHidden.value=target.attr('name');
	hiddenForm.submit();
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

//掲示板詳細をクリック時、サーブレット経由で掲示板詳細画面へ遷移
function boardDetail(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" );
	formNameHidden.value="boardDetail";
	boardIdHidden.value='<%out.print(boardId);%>';
	hiddenForm.submit();
}

//「掲示板から退出」テキスト押下時
function leaveBoard(){
	//target = $(event.target);
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var boardIdHidden = document.getElementById( "boardIdHidden" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	boardIdHidden.value='<%out.print(boardId);%>';
	formNameHidden.value="leaveBoard";
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

//---掲示板一覧ポップ画面ように2つ追加---
//新規掲示板作成をクリック時、サーブレット経由で掲示板作成画面へ遷移
function createBoard(){
	var hiddenForm = document.getElementById( "hiddenForm" ) ;
	var formNameHidden = document.getElementById( "formNameHidden" ) ;
	var pageType = document.getElementById( "pageType" ) ;
	formNameHidden.value="createBoard";
	pageType.value="create";
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


//確認済みボタンクリック時
function readClick(img){
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
		userRead.textContent="";
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
	<script src="<%=request.getContextPath()%>/src/js/nav.js"></script>
	<script src="<%=request.getContextPath()%>/src/js/board.js"></script>
</body>
</html>