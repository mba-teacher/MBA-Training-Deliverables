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
	$("#alarm-show").click(function(){
		$(".popup-alarm-list").fadeIn(100);
	});

	$(".link-hide").click(function(){
		$(".popup-alarm-list").fadeOut(100);
	});

	//---その他ポップアップ---
	$("#link-botoom-show").click(function(){
		$(".popup-bottom-link").fadeIn(100);
	});

	$(".link-hide").click(function(){
		$(".popup-bottom-link").fadeOut(100);
	});

});