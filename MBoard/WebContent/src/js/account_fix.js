
/*
jQuery("#realText input:text").on('click blur keydown keyup keypress change'

,function() {
		var textWrite = jQuery("#realText input:text").val();
		jQuery("#realWrite p").html(textWrite);
	});

*/

/**
 * ページ内検索
 */
 $(function() {
	searchWord = function() {
		var searchResult,
			searchText = $(this).val(), // 検索ボックスに入力された値
			targetText,
			hitNum;
		var searchAdmin,
			searchEdit,
			searchDelete;

		// 検索結果を格納するための配列を用意
		searchResult = [];
		searchAdmin = [];
		searchEdit = [];
		searchDelete = [];

		// 検索結果エリアの表示を空にする
		$('#search-result__list').empty();

		// 検索ボックスに値が入ってる場合
		if (searchText != '') {
			//元々のリストの中身を非表示に
			document.getElementById("target-area").style.display = "none";

			//$()の中身を検索対象に
			$('.ul .user_list p').each(function() {
				targetText = $(this).text();

				// 検索対象となるリストに入力された文字列が存在するかどうかを判断
				if (targetText.indexOf(searchText) != -1) {
					// 存在する場合はそのリストのテキストを用意した配列に格納
					searchResult.push(targetText);
					//★同階層の要素（兄弟要素）を検索してコピー
            		searchAdmin.push($(this).nextAll('.adminChoise').clone());
            		searchEdit.push($(this).nextAll('.edit').clone(true));
					searchDelete.push($(this).nextAll('.delete').clone(true));
					console.log(searchResult.length);
				}else{
					//検索該当なし
					console.log("true");
				}
				console.log(searchText);
			});

		// 検索結果をページに出力
		for (var i = 0; i < searchResult.length; i ++) {
        	//最初にulを作成
        	if (i == 0) {
        		$('<ul></ul>').prependTo('#search-result__list');
        	}
        	//リストアイテムの作成  IDに「result＋検索数番号」を設定
			$('<li class="user_list"></li>').appendTo('#search-result__list ul').attr('id', 'result'+i);
			$('<p>').text(searchResult[i]).appendTo('#result'+i);
			$(searchAdmin[i]).appendTo('#result'+i);
			$(searchEdit[i]).appendTo('#result'+i);
			$(searchDelete[i]).appendTo('#result'+i);
        }
        //検索対象に該当した場合
		if (searchResult.length > 0) {
			document.getElementById("noResult").style.display = "none";        //該当なし非表示
        }
        //該当なしの場合
        else {
        	document.getElementById("noResult").style.display = "block";       //該当なし表示
        }
        //検索結果表示
        document.getElementById("search-result").style.display = "block";

      }else{
        //初期の状態に戻す
        document.getElementById("target-area").style.display = "block";
        document.getElementById("search-result").style.display = "none";
      }
    };

    // searchWordの実行
    $('#search-text').on('input', searchWord);



	/**
	 * 権限の編集前準備
	 */
	$('.edit').on('click', function(){
		var name,
			radio_name,
			admin;

		name = $(this).prevAll('p').text() +"さん";
		radio_name = "authority"+ $(this).data('num');
		admin = $(this).data('bool');

		$('#edit_name').text(name);
		$('#true').attr('name', radio_name);
		$('#false').attr('name', radio_name);

		//ラジオボタンの選択制御
		$('#'+admin).prop('checked', true);

	});

  });


/**
 * 修正ボタンの遷移可否
 */
function editSendCheck() {
	var changed = document.getElementsByClassName("changed");

	if (changed.length > 0) {       //changedクラスがある場合
		console.log("class=changed");
		return true;
	}
	else {                          //changedクラスがない場合
		return false;
	}
}

