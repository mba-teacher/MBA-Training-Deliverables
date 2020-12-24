/**
 *
 */

var change_flg = false;
//チェックボックスに変化があった時の処理
function change(){
	change_flg = true;
}

$(function() {
	//戻るボタンを押したときの処理
	$('.backbtn').click(function() {
		if(change_flg == true){
			$('.modal').show();
		}else{
			//ポップアップにあるOKボタンが押される
			document.getElementById("mobal_btn2").click();
		}
	});

	//ポップアップのキャンセルボタンを押したときの処理
	$('.js-modal-close').click(function() {
		$('.modal').hide();
	});
});