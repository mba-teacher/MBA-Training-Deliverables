/**
 *
 */

// textで送信する値の空白判定処理
function formCheck() {
	var targetsIn = document.getElementById('input').value;

	// 送信時のチェック（※規則に沿わない入力があれば送信しない）
	//var targetsForm = document.getElementsByClassName('checkform');

		if (targetsIn == "") {
			/* 送信する値が空白だったなら*/
			var popupE = document.getElementById('noNameEdit');
			var popupN = document.getElementById('noNameNew');
			/* エラーメッセージを表示*/
			popupE.style.display = "block";
			popupN.style.display = "block";
			//送信を拒否する
			return false;
		} else {
			// 送信を許可
			return true;
		}

}


// 編集ボタンクリック時の処理
function edit(num) {
	/* divのidで指定している「popup」と「layer」「noName」を取得する */
	var popup = document.getElementById('popup');
	var layer = document.getElementById('layer');
	var noName = document.getElementById('noNameEdit');

	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	/* この時点ではnoNameは非表示にする*/
	popup.style.display = "block";
	layer.style.display = "block";
	noName.style.display = "none"

	/* 変数resultを定義「param」という文字列と本関数の引数で渡ってきたnumを組み合わせておく */
	var result = 'param' + num;
	var resultId = 'idParam' + num;

	/* hiddenのパラメータを取得する(jsp側のhiddenのidを↑で定義した「result」と同様にする */
	var param = document.getElementById(result).value;
	var idParam = document.getElementById(resultId).value;

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('groupText');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。valueの中身を↑で取得してきたparam,idParamのvalueと同じにしておく */
	newParam.innerHTML =
		"<input type='text' name='groupName' class='number' id='input' value='" + param + "' style='width: 320px; height: 30px; font-size: 25px;' maxlength=10><input type='hidden' name='groupId' id='input' value='" + idParam + "'>";
}

// 新規作成ボタンクリック時の処理
function create() {

	/* divのidで指定している「popup」と「layer」「noName」を取得する */
	var popup = document.getElementById('popupCre');
	var layer = document.getElementById('layer');
	var noName = document.getElementById('noNameNew');
	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	/* この時点ではnoNameは非表示にする*/
	popup.style.display = "block";
	layer.style.display = "block";
	noName.style.display = "none"

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('groupCreateText');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。 */
	newParam.innerHTML =
		"<input class='number' type='text' name='groupName' class='number' id='input' placeholder=' グループ名' style='width: 320px; height: 30px; font-size: 25px;' maxlength=10>";
}

// 削除ボタンクリック時の処理
function dele(num) {
	/* divのidで指定している「popup」と「layer」を取得する */
	var popup = document.getElementById('delepop');
	var layer = document.getElementById('layer');
	/* 編集ボタンが押された段階で、非表示にしていたpopupとlayerを再表示する */
	popup.style.display = "block";
	layer.style.display = "block";

	/* 変数resultを定義「param」という文字列と本関数の引数で渡ってきたnumを組み合わせておく */
	var resultId = 'idParam' + num;

	/* hiddenのパラメータを取得する(jsp側のhiddenのidを↑で定義した「result」と同様にする */
	var idParam = document.getElementById(resultId).value;

	/* ポップアップ内のテキストエリアが入るdivのidを取得 */
	var newParam = document.getElementById('deleteId');
	/* 取得したdivの中にinnerHTMLを使いhtml要素を記述する。valueの中身を↑で取得してきたidParamのvalueと同じにしておく */
	newParam.innerHTML = "<input type='hidden' name='deleteId' id='input' value='" + idParam + "'>";
}

// ポップアップのキャンセルボタンクリック時の処理
function cancel() {
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
		nextSelector: 'a.next',
		autoTrigger: true,
		padding: 20,
		loadingHtml: '<i class="fa fa-spinner"></i>'
	});

});


