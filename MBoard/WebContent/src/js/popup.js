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
    if( document . Form1 . Login_ID . value == "" ){ // 名前が未入力の場合
        flag = 1;
        document . getElementById( 'inputItem1' ) . style . display = "block"; //表示
    }else{ // 名前が入力済みの場合
        document . getElementById( 'inputItem1' ) . style . display = "none"; //非表示
    }
    if( document . Form1 . Login_Pass . value == "" ){ // コメントが未入力の場合
        flag = 1;
        document . getElementById( 'inputItem1' ) . style . display = "block"; //表示
    }else{ // コメントが入力済みの場合
        document . getElementById( 'inputItem1' ) . style . display = "none"; //非表示
    }

    if( flag ){ // 入力必須項目に未入力があった場合
        return false; // 送信中止
    }else{ // 入力必須項目が全て入力済みだった場合
        return true; // 送信実行
    }

}