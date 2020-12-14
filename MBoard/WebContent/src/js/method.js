/**
 * 遷移処理の関数
 */

function setAndSubmit(id) {
    document.postIconForm.memberId.value = id;
    document.postIconForm.submit();
}


/**
 * 選択した画像をその場で描画する
 */
 function previewImage(obj)
{

	var fileReader = new FileReader();
	fileReader.onload = (function() {
		document.getElementById('preview').src = fileReader.result;
	});
	fileReader.readAsDataURL(obj.files[0]);
	if ( document.getElementById('before') != null) {
		document.getElementById('before').style.display = "none";
	}
}
