/**
 *
 */
document.addEventListener('DOMContentLoaded', function() {

	var targets = document.getElementsByClassName('number');
	//alert(targets.length);
	for (var i=0 ; i<targets.length ; i++) {
    	var alertelement = this.parentNode.getElementsByClassName('alertarea');
    	if( ( this.value != '') && ( this.value.match( /[^\d\-]+/ )) ) {
    		// ▼何か入力があって、指定以外の文字があれば
			if( alertelement[0] ) {
				alertelement[0].innerHTML = '入力には、数字とハイフン記号だけが使えます。';
			}
			this.style.border = "2px solid red";
    	}else {
      	// ▼何も入力がないか、または指定文字しかないなら
			if( alertelement[0] ) {
				alertelement[0].innerHTML = "";
			}
			this.style.border = "1px solid black";
		}
	}
});

document.addEventListener('DOMContentLoaded', function() {

  // 送信時のチェック（※規則に沿わない入力があれば送信しない）
  var targets = document.getElementsByClassName('checkform');
  for (var i=0 ; i<targets.length ; i++) {

    // ▼送信直前で全項目を再度チェックしてエラーを数える：
    targets[i].onsubmit = function () {
      var inputelements = this.querySelectorAll('input');  // フォームの中にあるinput要素とtextarea要素をすべて得る
      var alerts = this.getElementsByClassName('alertarea');
      //alert(inputelements.length);
      var ret = 0;

      // ――――――――――――――――――
      // ▽全項目のoninputイベントを一括実行
      // ――――――――――――――――――
      for (var j=0 ; j<alerts.length ; j++) {

        if( inputelements[j].oninput ) {
          // oninputイベントが定義されている場合にだけ実行する
          inputelements[j].oninput();
        }
      }
      // ――――――――――――――――――
      // ▽アラートの表示数をカウントする
      // ――――――――――――――――――
      for (var j=0 ; j<alerts.length ; j++) {

        if( alerts[j].innerHTML.length > 0 ) {
          // アラートが表示されていればカウント
          ret++;
        }
      }
      if( ret == 0 ) {
      	//alert("test");
        // エラーメッセージが1つもなければ送信を許可
        return true;
      }
      else {
        // エラーメッセージが1つ以上あれば、アラートを表示して送信をブロック。
        alert( ret + "個のエラーがあります。");// ※警告用のダイアログボックスを表示したくないなら、この行は削除。
        return false;
      }

    }
  }

});

// 編集ボタンクリック時の処理
function edit(num){
	/* divのidで指定している「popup」と「layer」を取得する */
	var popup = document.getElementById('popup');
	var layer = document.getElementById('layer');
	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	popup.style.display = "block";
	layer.style.display = "block";

	/* 変数resultを定義「param」という文字列と本関数の引数で渡ってきたnumを組み合わせておく */
	var result = 'param'+num;
	var resultId = 'idParam'+num;

	/* hiddenのパラメータを取得する(jsp側のhiddenのidを↑で定義した「result」と同様にする */
	var param = document.getElementById(result).value;
	var idParam =document.getElementById(resultId).value;

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('groupText');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。valueの中身を↑で取得してきたparamのvalueと同じにしておく */
	newParam.innerHTML =
	 "<input type='text' name='groupName' class='number' id='input' value='"+param+"' style='width: 320px; height: 30px; font-size: 25px;' maxlength=10><input type='hidden' name='groupId' id='input' value='"+idParam+"'>";
}

// 新規作成ボタンクリック時の処理
function create(){

	/* divのidで指定している「popup」と「layer」を取得する */
	var popup = document.getElementById('popupCre');
	var layer = document.getElementById('layer');
	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	popup.style.display = "block";
	layer.style.display = "block";

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('groupCreateText');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。valueの中身を↑で取得してきたparamのvalueと同じにしておく */
	newParam.innerHTML =
	 "<input class='number' type='text' name='groupName' class='number' id='input' placeholder=' グループ名' style='width: 320px; height: 30px; font-size: 25px;' maxlength=10>";
}

// 削除ボタンクリック時の処理
function dele(num){
	/* divのidで指定している「popup」と「layer」を取得する */
	var popup = document.getElementById('delepop');
	var layer = document.getElementById('layer');
	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	popup.style.display = "block";
	layer.style.display = "block";

	/* 変数resultを定義「param」という文字列と本関数の引数で渡ってきたnumを組み合わせておく */
	var resultId = 'idParam'+num;

	/* hiddenのパラメータを取得する(jsp側のhiddenのidを↑で定義した「result」と同様にする */
	var idParam =document.getElementById(resultId).value;

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('deleteId');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。valueの中身を↑で取得してきたparamのvalueと同じにしておく */
	newParam.innerHTML ="<input type='hidden' name='deleteId' id='input' value='"+idParam+"'>";
}

// ポップアップのキャンセルボタンクリック時の処理
function cancel(){
	$('#popup,#popupCre,#delepop, #layer').hide();
}


$(function() {
/*
    // 編集ボタンクリック時の処理(old)
    $("[id=showEdit]").click(function() {
        $("#popup,#layer").show();
        //値を取得
        const group=document.getElementById("4グループG").value;
        var div_element = document.createElement('div')
		var parent_object = document.getElementById('groupText');
		div_element.innerHTML=
		'<input type="text" name="groupName" value="'+group+'" style="width: 320px; height: 30px; font-size: 25px;" maxlength=10>';

		parent_object.appendChild(div_element);
    });
	// 新規作成ボタンクリック時の処理(old)
    $('#showNew').click(function() {
        $('#popup, #layer').show();

		var div_element = document.createElement('div')
		var parent_object = document.getElementById('groupText');
		div_element.innerHTML=
		''+count+'';
		parent_object.appendChild(div_element);
    });
	// ポップアップのキャンセルボタンクリック時の処理(old)
    $('[id=close]').click(function() {
        $('#popup,#popupCre,#delepop, #layer').hide();
    });

	$('#create').click(function() {
        $('#popup, #layer').hide();
    });

	// 削除ボタンクリック時の処理(old)
	$('[id=showDele]').click(function() {
        $('#delepop, #layer').show();
    });
	$('#delete').click(function() {
        $('#delepop, #layer').hide();
    });
*/

	//無限スクロールをさせる指定と処理
	$('.jscroll').jscroll({
		nextSelector:'a.next',
		autoTrigger: true,
		padding: 20,
		loadingHtml: '<i class="fa fa-spinner"></i>'
	});
});


/*
$('.container').infiniteScroll({
	// options
	path: 'GroupEdit2.jsp',//'?page={{#}}', //スクロールしたら読み込む要素とかファイル名とか関数とか。{{#}}を使うと1ずつ増えていきます。左のサンプルでは「getDrawDataAsyncInfinite.php?page=1」、「getDrawDataAsyncInfinite.php?page=2」、「getDrawDataAsyncInfinite.php?page=3」・・・となっていきます。
	append: '.scroll_post',//出力する要素。<div class="scroll_post">...</div>となっていれば、この範囲内を表示してくれます。
	checkLastPage: '.pagination__next', //ここで指定した要素が存在するかどうかで、最後のページかどうかチェックしてくれます。
});

$('.scroll_wrap').infinitescroll({
	navSelector  : ".navigation",
	nextSelector : ".navigation a",
	itemSelector : ".scroll_wrap .scroll"
});
*/