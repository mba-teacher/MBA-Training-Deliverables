/**
 * プロフィール編集画面の未入力項目チェック
 */
function formCheck() {
	var flag = 0;

	//掲示板名
    if( document.profileForm.user_name.value == "" || document.profileForm.email_address.value == "" ||
		document.profileForm.line_works_id.value == "" ){
        flag = 1;
        document.getElementById( 'errortext' ).style.display = "table"; //表示
    }else{
        document.getElementById( 'errortext' ).style.display = "none"; //非表示
    }

	var targets = document.getElementsByClassName('email');
	for (var i = 0; i < targets.length; i++) {
		var alertelement = document.profileForm.getElementsByClassName('alertarea');
		if( ( targets[i].value != '') && ( !targets[i].value.match( /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/ )) ) {
			// 何か入力があって、指定以外の文字があれば
			if( alertelement[0] ) {
				alertelement[0].innerHTML = "メールアドレスを入力してください。";
			}
			flag = 1;
			targets[i].style.border = "2px solid red";
		}
		else {
			// 何も入力がないか、または指定文字しかないなら
			if( alertelement[0] ) {
				alertelement[0].innerHTML = "";
			}
			targets[i].style.border = "1px solid black";
		}
	}

    if(flag == 1) {
    	return false;  //送信しない
    } else {
    	return true;   //送信実行
    }
}