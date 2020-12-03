/**
 * 無限スクロール
 */

//jScrollの使用
$(function(){
    /*$('.flex_container').jscroll();*/
    $('.scroll').jscroll();
});

var scrollOption = {
	loadingHtml: 'now loading',
    autoTrigger: true,
    padding: 20,
    nextSelector: 'a.jscroll-next'/*,
    contentSelector: '#post_content'*/
};
$('.scroll').jscroll(scrollOption);


