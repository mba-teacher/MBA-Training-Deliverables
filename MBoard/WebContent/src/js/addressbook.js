/**
 * アドレス帳のページ内検索
 */
 $(function() {
	searchWord = function() {
		var searchResult,
			searchText = $(this).val(), // 検索ボックスに入力された値
			targetText,
			hitNum;
		var searchBlock;

		// 検索結果を格納するための配列を用意
		searchResult = [];
		searchBlock = [];

		// 検索結果エリアの表示を空にする
		$('#search-result__list').empty();

		// 検索ボックスに値が入ってる場合
		if (searchText != '') {
			//元々のリストの中身を非表示に
			document.getElementById("target-area").style.display = "none";

			//$()の中身を検索対象に
			$('#target-area .user_name').each(function() {
				targetText = $(this).text();

				// 検索対象となるリストに入力された文字列が存在するかどうかを判断
				if (targetText.indexOf(searchText) != -1) {
					// 存在する場合はそのリストのテキストを用意した配列に格納
					searchResult.push(targetText);
					//★同階層の要素（兄弟要素）を検索してコピー
            		searchBlock.push($(this).parent().clone());
					console.log(searchResult.length);
					console.log(searchBlock.length);
				}else{
					//検索該当なし
					console.log("true");
				}
				console.log(searchText);
			});

		// 検索結果をページに出力
		for (var i = 0; i < searchResult.length; i ++) {
        	$(searchBlock[i]).appendTo('#search-result__list');
        	//リストアイテムの作成  IDに「result＋検索数番号」を設定
			//$('<li class="user_list"></li>').appendTo('#search-result__list ul').attr('id', 'result'+i);
			if (i == (searchResult.length - 1)) {
				$('<div class="clear"></div>').appendTo('#search-result__list');
			}
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


/*    $('[name="check"]').change(function(){
        if( $(this).prop('checked') ){
        alert('チェックを入れました');
            document.getElementByClass("arrowOn").style.display = "block";
        	document.getElementByClass("arrowOff").style.display = "none";
        }else{
        	alert('チェックを外しました');
        	document.getElementByClass("arrowOn").style.display = "none";
        	document.getElementByClass("arrowOff").style.display = "block";
        }
    });*/

  });

