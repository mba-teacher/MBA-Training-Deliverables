/**
 *
 */

$(function(){

	//投稿詳細オプションを開く
	$(".form").click(function(){
		$(".post-detail").css("display","block");
		$(".post-form").css("border-bottom","1px solid #000");
	});

	//投稿詳細オプションを閉じる
	$(".post-submit").click(function(){
		$(".post-detail").css("display","none");
	});



	//掲示板一覧を開く
	$(".show-board-list").click(function(){
		$(".popup-board-list").fadeIn(100);
	});

	//掲示板一覧を閉じる
	$(".link-hide, .popup-board-close").click(function(){
		$(".popup-board-list").fadeOut(100);
	});


	//掲示板プロパティを開く
	$(".board-menu").click(function(){
		$(".popup-board-property").fadeIn(100);
	});

	//掲示板プロパティを閉じる
	$(".link-hide").click(function(){
		$(".popup-board-property").fadeOut(100);
	});

});