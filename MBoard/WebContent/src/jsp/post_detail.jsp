<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "data.UserInfoBean,data.BoardInfoBean,data.PostInfoBean,data.CommentInfoBean,java.util.ArrayList,java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>会話詳細</title>
	<link rel="stylesheet" href="src/css/nav.css" />
	<link rel="stylesheet" href="src/css/board.css" />
	<link rel="stylesheet" href="src/css/post_detail.css" />
	<link rel="stylesheet" href="src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<!--				セッションから情報を取得 	        -->
<!-- ログイン中のユーザー情報を取得 -->
<% UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean"); %>
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

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			<!-- <a href="#"> -->
				<img src="<%=request.getContextPath()%><%= userInfo.getProfileImgPath() %>" class="nav-icon" onclick="myPage()">
			<!-- </a> -->
			<%-- <a href="<%=request.getContextPath()%>/board"> --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon" onclick="board()">
			<!-- </a> -->
			<!-- <a href=""> -->
				<img src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon" onclick="addressBook()">
			<!-- </a> -->
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
						<div class="board-item">
							<div class="board-color"></div>
							<p>掲示板名</p>
						</div>

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
							<div class="board-name">会話詳細</div>
							<!-- <img src="src/img/mb_2_syousai.png" class="board-menu"> -->
						</div>
					</div>

						<form action="postDetail" method="post" class="form" id="postForm">
							<input class="post-form" name="postTitle" placeholder="返信する" readonly>
							<div class="post-detail">
								<textarea class="post-form-content" name="postContent" placeholder="返信内容"  wrap="hand"></textarea>
								<div class="post-option">
									<div class="post-option-icon">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
										<img src="src/img/mb_g_letteredit.png">
									</div>
									<input type="hidden" name="formName" value="makeComment" >
									<input type="submit" value="送信" class="submit">
								</div>
							</div>
						</form>

					<div class="board-content">
					<%if(detailType.equals("post")){%>
							<!-- 記事IDとコメントIDのかぶり防止に記事IDを負の値にしている -->
							<%int postId=post.getPostId()*-1;%>
						<div class="post" name="mainDtail" id="<%out.print(postId);%>">
							<img src="src/img/mb_e_plus.png" class="post-icon">
							<div class="post-board-name"><%= post.getPostTitle() %></div>
							<div class="post-user-name">投稿者名</div>
							<div class="post-date"><%=post.getPostDate()%></div>
							<div class="clear"></div>
							<div class="post-letter"><%=post.getPostContents()%><br></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="src/img/mb_i_comment.png">
									<span class="number"><%= session.getAttribute("postCommentCount") %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="src/img/mb_j_good.png" onclick="readClick('<%out.print(postId);%>')">
									<span id="count<%out.print(postId);%>"><%= session.getAttribute("postReadCount") %></span>
										<div id="read<%out.print(postId);%>" >
											<div class="<%out.print((boolean)session.getAttribute("postUserRead"));%>">
											<% if((boolean)session.getAttribute("postUserRead")){ %>
											確認済
											<% }else{ %>
											未確認
											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(post.getPostUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(postId);%>" class="post-detail-button" src="src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>
						<%}else if(detailType.equals("comment")){%>
						<% int detailId=detailComment.getCommentId();%>
						<div class="post" name="mainDtail" id="<%out.print(detailId);%>">
							<img src="src/img/mb_e_plus.png" class="post-icon">
							<div class="post-board-name">コメント</div>
							<div class="post-user-name">投稿者名</div>
							<div class="post-date"><%= detailComment.getCommentDate() %></div>
							<div class="clear"></div>
							<div class="post-letter"><%=detailComment.getCommentContents()%><br></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="src/img/mb_i_comment.png">
									<span class="number"><%= session.getAttribute("postCommentCount") %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="src/img/mb_j_good.png" onclick="readClick('<%out.print(detailId);%>')">
									<span id="count<%out.print(detailId);%>"><%= session.getAttribute("postReadCount") %></span>
										<div id="read<%out.print(detailId);%>" >
											<div class="<%out.print((boolean)session.getAttribute("postUserRead"));%>">
											<% if((boolean)session.getAttribute("postUserRead")){ %>
											確認済
											<% }else{ %>
											未確認
											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(detailComment.getCommentUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(detailId);%>" class="post-detail-button" src="src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>
						<%}%>

						<% for (int i = 0; i < comment.size(); i++ ) { %>
						<%int commentId= comment.get(i).getCommentId();%>
						<div class="post post-comment" id="<%out.print(commentId);%>">
							<img src="src/img/mb_e_plus.png" class="post-icon">
							<div class="post-board-name"><%= commentId %></div>
							<div class="post-comment-for"><%= post.getPostTitle() %>へのコメント</div>
							<div class="post-user-name">投稿者名</div>
							<div class="post-date"><%= comment.get(i).getCommentDate() %></div>
							<div class="clear"></div>
							<div class="post-letter"><%= comment.get(i).getCommentContents() %></div>
							<div class="post-icon-area">
								<div class="comment">
									<img src="src/img/mb_i_comment.png">
									<span class="number"><%= commentCount.get(commentId) %></span>コメント
								</div>
								<div class="good">
									<img class="readButton" src="src/img/mb_j_good.png" onclick="readClick('<%out.print(commentId);%>')">
									<span id="count<%out.print(commentId);%>"><%= readCount.get(commentId) %></span>
										<div id="read<%out.print(commentId);%>" >
											<div class="<%out.print(userRead.get(commentId));%>">
											<% if(userRead.get(commentId)){ %>
											確認済
											<% }else{ %>
											未確認
											<% } %>
											</div>
										</div>
								</div>
							</div>
							<div >
								<% if(comment.get(i).getCommentUserId()==userInfo.getUserID()){ %>
								<img  id="<%out.print(commentId);%>" class="post-detail-button" src="src/img/mb_2_syousai.png">
								<% } %>
							</div>
						</div>

							<% for (int x = 0; x < commentChain.get(i).size(); x++ ) { %>
							<%int commentChainId= commentChain.get(i).get(x).getCommentId();%>
							<div class="tree-area">
							<div class="comment-branch">
								<div class="branch-border border-top"></div>
								<%if(x+1!=commentChain.get(i).size()){ %>
								<div class="branch-border border-bottom"></div>
								<%} %>
							</div>
							<div class="post post-comment post-tree" id="<%out.print(commentChainId);%>">
								<img src="src/img/mb_e_plus.png" class="post-icon">
								<div class="post-board-name"><%= commentChainId %></div>
								<div class="post-comment-for"><%= post.getPostTitle() %>へのコメントのコメント</div>
								<div class="post-user-name">投稿者名</div>
								<div class="post-date"><%= commentChain.get(i).get(x).getCommentDate() %></div>
								<div class="clear"></div>
								<div class="post-letter"><%= commentChain.get(i).get(x).getCommentContents() %></div>
								<div class="post-icon-area">
									<div class="comment">
										<img src="src/img/mb_i_comment.png">
										<span class="number"> <%=commentCount.get(commentChainId) %></span>コメント
									</div>
									<div class="good">
										<img class="readButton" src="src/img/mb_j_good.png" onclick="readClick('<%out.print(commentChainId);%>')">
										<span id="count<%out.print(commentChainId);%>"><%= readCount.get(commentChainId) %></span>
										<div id="read<%out.print(commentChainId);%>" >
											<div class="<%out.print(userRead.get(commentChainId));%>">
											<% if(userRead.get(commentChainId)){ %>
											確認済
											<% }else{ %>
											未確認
											<% } %>
											</div>
										</div>
									</div>
								</div>
								<div>
									<% if(commentChain.get(i).get(x).getCommentUserId()==userInfo.getUserID()){ %>
									<img  id="<%out.print(commentChainId);%>" class="post-detail-button" src="src/img/mb_2_syousai.png">
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
	<form action="postDetail" method="post" id="hiddenForm">
		<input type="hidden" name="formName" id="formNameHidden">
		<input type="hidden" name="postId" id="postIdHidden">
	</form>

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
	}else if(target.attr('class')!=="readButton" && $(this).attr('name')!=="mainDtail"){
		postIdHidden.value=$(this).attr('id');
		formNameHidden.value="commentDetail";
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