/**
 * 無限スクロール
 */

//jScrollの使用
$(function(){
    $('.scroll').jscroll();
});

var scrollOption = {
	loadingHtml: 'now loading',
    autoTrigger: true,
    padding: 20,
    nextSelector: 'a.jscroll-next'
};
$('.scroll').jscroll(scrollOption);


//JavaScriptで無限スクロール
/*document.querySelectorAll('.scroll').forEach(elm => {
	elm.onscroll = function () {
		if (this.scrollTop + this.clientHeight >= this.scrollHeight) {
			if (parseInt(this.dataset.lastnum) < parseInt(this.dataset.max)) {
				this.dataset.lastnum = parseInt(this.dataset.lastnum) + 1;
				let img = document.createElement('img');
				img.src = this.dataset.lastnum + '.jpg';
				this.appendChild(img);
			}
		}
	}
})*/
