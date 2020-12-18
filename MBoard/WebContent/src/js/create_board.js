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


/**
 * 全選択させる
 */
$(function() {
	// 1. 「全選択」する
	$('#userall').on('click', function() {
		$("input[name='accessList']").prop('checked', this.checked);
	});
	// 2. 「全選択」以外のチェックボックスがクリックされたら、
	$("input[name='accessList']").on('click', function() {
		if ($('#sub :checked').length == $('#sub :input').length) {
			// 全てのチェックボックスにチェックが入っていたら、「全選択」 = checked
			$('#userall').prop('checked', true);
		} else {
			// 1つでもチェックが外れたら、「全選択」 = not-check
			$('#userall').prop('checked', false);
		}
	});
});


/**
 * 掲示板作成画面の未入力項目チェック
 */
function formCheck() {
	var flag = 0;
	var checks = document.boardForm.accessList;
	var count = 0;
	for (var i = 0; i < checks.length; i++) {
		if(checks[i].checked == false) {
			count += 1;
		}
	}

	//掲示板名
    if( document.boardForm.Board_Category.value == "" || count == checks.length){
        flag = 1;
        document.getElementById( 'errortext' ).style.display = "table"; //表示
    }else{
        document.getElementById( 'errortext' ).style.display = "none"; //非表示
    }

    if(flag == 1) {
    	return false;  //送信しない
    } else {
    	return true;   //送信実行
    }
}
