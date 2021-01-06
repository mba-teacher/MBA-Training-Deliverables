/**
 * キャンバスに文字を書く
 */
 function drawText(canvas_id, text) {
	var canvas = document.getElementById(canvas_id);
	var ctx = canvas.getContext('2d');
	var text = text.slice(0, 1);
	//文字のスタイルを指定
	ctx.font = '32px serif';
	ctx.fillStyle = '#404040';
	//文字の配置を指定（左上基準にしたければtop/leftだが、文字の中心座標を指定するのでcenter
	ctx.textBaseline = 'center';
	ctx.textAlign = 'center';
	//座標を指定して文字を描く（座標は画像の中心に）
	var x = (canvas.width / 2);
	var y = (canvas.height / 2);
	ctx.fillText(text.value, x, y);
}