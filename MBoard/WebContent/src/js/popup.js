/**
 * モーダルポップアップの表示・非表示
 */

$(function () {
  $('#cancel').click(function(){
      $('#modalArea').fadeIn();
  });

  $('#modalCancel, #modalOK').click(function(){
    $('#modalArea').fadeOut();
  });

});


/**
 * 入力必須項目が入力されているかチェック
 */
function formCheck(){
    var flag = 0;
    var ret = 0;

	//ユーザー名（ログインID）、仮パスワード、メールアドレス、LINEWORKS ID
    if( document.Form1.Login_ID.value == "" || document.Form1.Login_Pass.value == "" ||
    	document.Form1.Email_Address.value == "" || document.Form1.Line_Works_ID.value == "" ){
        flag = 1;
        document.getElementById( 'errortext' ).style.display = "block"; //表示
    }else{
        document.getElementById( 'errortext' ).style.display = "none"; //非表示
    }


    //var inputelements = this.querySelectorAll('input');  // フォームの中にあるinput要素をすべて得る
	var alerts = document.getElementsByClassName('alertarea');

      // ――――――――――――――――――
      // ▽アラートの表示数をカウントする
      // ――――――――――――――――――
      for (var j = 0; j < alerts.length; j++) {
        if( alerts[j].innerHTML.length > 0 ) {
          // アラートが表示されていればカウント
          ret++;
        }
      }

	if( flag ){ // 入力必須項目に未入力があった場合
		return false; // 送信中止
	}
	else { // 入力必須項目が全て入力済みだった場合
    	if( ret == 0 ) {
    		return true; //送信実行
    	} else {
		// エラーメッセージが1つ以上あれば、アラートを表示して送信をブロック。
			return false;
		}
	}

}


/**
 * バリデーションチェック（実装するには文字数制限などの確認がいる）
 */
document.addEventListener('DOMContentLoaded', function() {

	//ユーザー名、LINEWORKS-IDの入力チェック
	var targets = document.getElementsByClassName('id');
	for (var i = 0; i < targets.length; i++) {
		//文字が入力されたタイミングでチェックしている
		targets[i].oninput = function () {
			var alertelement = this.parentNode.getElementsByClassName('alertarea');

			// ★条件1：指定以外の文字があるかどうかを判断（1文字以上入力されている場合）
			if( ( this.value != '') && ( this.value.match( /[^a-zA-Z0-9_!#@\-]/ )) ) {
				// ▼英数字以外の文字があれば
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "入力には英数字と記号(_!#@-)だけが使えます。";
				}
				this.style.border = "2px solid red";
			}

			// ★条件2：入力文字数が3文字よりも少ないかどうかを判断
			else if(( this.value != '') && ( this.value.length < 3 )) {
				// ▼入力文字数が3文字よりも少なければ
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "3文字以上を入力して下さい。";
				}
				this.style.border = "2px solid orange";
			}

			else {
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "";
				}
				this.style.border = "1px solid black";
			}
		}
	}

	//パスワードの入力チェック
	var targets = document.getElementsByClassName('password');
	for (var i = 0; i < targets.length; i++) {
		//文字が入力されたタイミングでチェックしている
		targets[i].oninput = function () {
			var alertelement = this.parentNode.getElementsByClassName('alertarea');

			// ★条件1：英数字以外の文字があるかどうかを判断（1文字以上入力されている場合）
			if( ( this.value != '') && ( this.value.match( /[^a-zA-Z0-9]/ )) ) {
				// ▼英数字以外の文字があれば
				if( alertelement[0] ) { alertelement[0].innerHTML = "入力には英数字だけが使えます。"; }
				this.style.border = "2px solid red";
			}

			// ★条件2：入力文字数が6文字よりも少ないかどうかを判断
			else if( ( this.value != '') && ( this.value.length < 6 ) ) {
				// ▼入力文字数が6文字よりも少なければ
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "英数字で6文字以上を入力して下さい。";
				}
				this.style.border = "2px solid orange";
			}

			else {
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "";
				}
				this.style.border = "1px solid black";
			}
		}
	}

	//Eメールの入力チェック
	var targets = document.getElementsByClassName('email');
	for (var i=0 ; i<targets.length ; i++) {
    	// ▼文字が入力されたタイミングでチェックする場合：
		targets[i].oninput = function () {
			var alertelement = this.parentNode.getElementsByClassName('alertarea');
			if( ( this.value != '') && ( !this.value.match( /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/ )) ) {
				// 何か入力があって、指定以外の文字があれば
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "メールアドレスを入力してください。";
				}
				this.style.border = "2px solid red";
			}
			else {
				// 何も入力がないか、または指定文字しかないなら
				if( alertelement[0] ) {
					alertelement[0].innerHTML = "";
				}
				this.style.border = "1px solid black";
			}
    	}
	}

});

