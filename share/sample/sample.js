$(function () {
    searchWord = function(){
      var searchResult,
          searchText = $(this).val(), // 検索ボックスに入力された値
          targetText,
          hitNum;
  
      // 検索結果を格納するための配列を用意
      searchResult = [];
  
      // 検索結果エリアの表示を空にする
      $('#search-result__list').empty();
      $('.search-result__hit-num').empty();
  
      // 検索ボックスに値が入ってる場合
      if (searchText != '') {
        $('.target-area p').each(function() {
          targetText = $(this).text();
          document.getElementById("target-area").style.display = "none";
          // 検索対象となるリストに入力された文字列が存在するかどうかを判断
          if (targetText.indexOf(searchText) != -1) {
            // 存在する場合はそのリストのテキストを用意した配列に格納
            searchResult.push(targetText);
          }else{
            //検索該当なし
            console.log("true");
            document.getElementById("noResult").style.display = "block";
          }
          console.log(searchText);
        });
  
        // 検索結果をページに出力
        for (var i = 0; i < searchResult.length; i ++) {
          $('<p id="result"name="result">').text(searchResult[i]).appendTo('#search-result__list');
        }
      }else{
        document.getElementById("target-area").style.display = "block";
        document.getElementById("noResult").style.display = "none";
      }
    };
  
    // searchWordの実行
    $('#search-text').on('input', searchWord);
  });

  function ReDisplay(){
    document.getElementById("target-area").style.display = "block";
    document.getElementById("search-result").style.display = "none";    
  }