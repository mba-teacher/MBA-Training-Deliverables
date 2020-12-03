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

function formCheck(){
    var flag = 0;

    // 入力必須項目が入力されているかチェック
	//ユーザー名（ログインID）
    if( document . Form1 . Login_ID . value == "" ){
        flag = 1;
        document . getElementById( 'inputItem1' ) . style . display = "block"; //表示
    }else{
        document . getElementById( 'inputItem1' ) . style . display = "none"; //非表示
    }
	//仮パスワード
    if( document . Form1 . Login_Pass . value == "" ){
        flag = 1;
        document . getElementById( 'inputItem1' ) . style . display = "block"; //表示
    }else{
        document . getElementById( 'inputItem1' ) . style . display = "none"; //非表示
    }
	//メールアドレス
	if(document.Form1.Email_Address.value == "") {
		flag = 1;
		document.getElementById( 'inputItem1' ).style.display = "block"; //表示
	} else {
		document.getElementById( 'inputItem1' ).style.display = "none"; //非表示
	}
	//LINEWORKS ID
	if(document.Form1.Line_Works_ID.value == "") {
		flag = 1;
		document.getElementById( 'inputItem1' ).style.display = "block"; //表示
	} else {
		document.getElementById( 'inputItem1' ).style.display = "none"; //非表示
	}

    if( flag ){ // 入力必須項目に未入力があった場合
        return false; // 送信中止
    }else{ // 入力必須項目が全て入力済みだった場合
        return true; // 送信実行
    }

}