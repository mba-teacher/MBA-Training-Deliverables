/**
 *
 */

$(function(){

	//アクセス制限リストを開く
	$(".access-box").click(function(){
		$(".access-list-bg").fadeIn(100);
		$(".access-list-area").fadeIn(100);
	});

	//アクセス制限リストを閉じる
	$(".access-list-header, .access-list-bg").click(function(){
		$(".access-list-area").fadeOut(100);
		$(".access-list-bg").fadeOut(100);
	});

});