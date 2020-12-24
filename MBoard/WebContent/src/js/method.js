/**
 * 遷移処理の関数
 */

function setBoardAndSubmit(id, formID) {
	var form = document.getElementById(formID);
    form.boardId.value = id;
    form.submit();
}

function setAndSubmit(id, formID) {
	var form = document.getElementById(formID);
    form.memberId.value = id;
    form.submit();
}

function setMember(id, formID) {
	var form = document.getElementById(formID);
    form.memberId.value = id;
}

function setBoard(id, formID) {
	var form = document.getElementById(formID);
    form.boardId.value = id;
}

function hiddenSubmit() {
    document.hiddenForm.submit();
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

