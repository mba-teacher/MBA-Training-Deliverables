/**
 *
 */

$(function() {

    // 編集ボタンクリック時の処理
    $("[id=showEdit]").click(function() {
        $("#popup,#layer").show();
    });
	// 新規作成ボタンクリック時の処理
    $('#showNew').click(function() {
        $('#popup, #layer').show();
    });

    // ポップアップのキャンセルボタンクリック時の処理
    $('#close').click(function() {
        $('#popup, #layer').hide();
    });
	$('#create').click(function() {
        $('#popup, #layer').hide();
    });


	// 削除ボタンクリック時の処理
	$('[id=showDele]').click(function() {
        $('#delepop, #layer').show();
    });
    // ポップアップのキャンセルボタンクリック時の処理
    $('#closeDele').click(function() {
        $('#delepop, #layer').hide();
    });
	$('#delete').click(function() {
        $('#delepop, #layer').hide();
    });

	$('.jscroll').jscroll({
		nextSelector:'a.next',
		loadingHtml: '<i class="fa fa-spinner">Now Loading...</i>'
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
	});*/