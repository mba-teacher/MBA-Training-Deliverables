/**
 * ユーザー設定画面のページ内検索
 */
jQuery("#realText input:text").on('click blur keydown keyup keypress change'
 ,function() {
		var textWrite = jQuery("#realText input:text").val();
		jQuery("#realWrite p").html(textWrite);
});

$(function() {
	searchWord = function() {
		var searchResult,
			searchGroup,
			searchText = $(this).val(), // 検索ボックスに入力された値
			targetText,
			groupText,
			hitNum;

		// 検索結果を格納するための配列を用意
		searchResult = [];
		searchGroup = [];
		searchEdit = [];

		// 検索結果エリアの表示を空にする
		$('#search-result__list').empty();

		// 検索ボックスに値が入ってる場合
		if (searchText != '') {
			//元々のリストの中身を非表示に
			document.getElementById("close-target").style.display = "none";

			//$()の中身を検索対象に
			$('.user_list .target-area wr').each(function() {
				targetText = $(this).text();
				// 検索対象となるリストに入力された文字列が存在するかどうかを判断
				if (targetText.indexOf(searchText) != -1) {
					// 存在する場合はそのリストのテキストを用意した配列に格納
					searchResult.push(targetText);
					//★同階層の要素（兄弟要素）を検索してコピー
            		searchGroup.push($(this).nextAll('.groupChoise').clone());
            		searchEdit.push($(this).nextAll('.editButton').clone(true));

					console.log(searchResult.length);
				} else{
					//検索該当なし
					console.log("true");
				}
				console.log(searchText);
			});

			// 検索結果をページに出力
			for (var i = 0; i < searchResult.length; i++) {
				/*最初にulを作成//・・・prependTo()の()要素の先頭に$()の要素が挿入される
        		if (i == 0) {
        			$('<ul></ul>').prependTo('#search-result__list');
        		}*/

				//リストアイテムの作成  IDに「result＋検索数番号」を設定//.attr(A,B)でA(id等)の属性名をBに変更
				$('<div class="target-area"></div>').appendTo('#search-result__list').attr('id', 'result'+i);
				$('<wr></wr>').text(searchResult[i]).appendTo('#result'+i);
				$(searchGroup[i]).appendTo('#result'+i);
				$(searchEdit[i]).appendTo('#result'+i);
			}
			//検索対象に該当した場合
			if (searchResult.length > 0) {
				document.getElementById("search-result").style.display = "block";  //検索結果表示
			}
			//該当なしの場合
			else {
				document.getElementById("search-result").style.display = "none";   //検索結果非表示
			}

		}else{
			//初期の状態に戻す
			document.getElementById("close-target").style.display = "block";
			document.getElementById("search-result").style.display = "none";
		}
	};

	// searchWordの実行
	$('#search-text').on('input', searchWord);
});