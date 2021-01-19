/**
 *
 */


$(function(){

	let popupAction = false;
	//---外部リンクポップアップ---
	$("#link-show").click(function(){
		$(".popup-link").fadeIn(100);
	});

	$(".link-hide").click(function(){
		$(".popup-link").fadeOut(100);
	});

	//---通知ポップアップ---


	//---その他ポップアップ---
	$("#link-botoom-show").click(function(){
		$(".popup-bottom-link").fadeIn(100);
	});

	$(".link-hide").click(function(){
		$(".popup-bottom-link").fadeOut(100);
	});

});