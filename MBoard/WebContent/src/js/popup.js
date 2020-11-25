/**
 *
 */

/*function popupCancel() {
	//var popup = document.getElementsById('cancel');
	//if(!popup) return;

	//var

	var res = confirm("編集は保持されませんがよろしいですか？");
	if (res == true) {
		location.href = "#";
	}
}*/

$(function () {
  $('#cancel').click(function(){
      $('#modalArea').fadeIn();
  });

  $('#modalCancel, #modalOK').click(function(){
    $('#modalArea').fadeOut();
  });

});