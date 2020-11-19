/**
 *
 */


$(function(){

	let popupAction = false;

	$("#link-show").click(function(){
		$(".popup-link").fadeIn(100);
	});

	$(".link-hide").click(function(){
		$(".popup-link").fadeOut(100);
	});
});