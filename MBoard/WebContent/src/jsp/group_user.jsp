<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>各ユーザーグループ設定画面</title>
<link rel="stylesheet" href="../css/group_user.css">
<link rel="stylesheet" href="../css/guser_cansel_modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../js/group_user.js"></script>
<title>各ユーザーグループ設定画面</title>
</head>
<body>
	<a href="" class="backbtn js-modal-open"> 戻る </a>


	<div class="name">
		<h1>ユーザー名(設定されたもの)</h1>
		<p>追加するグループを選択してください。</p>
	</div>


	<div class="center_box">

		<div class="c_box">
			<input type="checkbox" id="01-A" name="checkbox01"> <label
				for="01-A" class="checkbox01"> グループ名十文字まで</label> <input
				type="checkbox" id="01-B" name="checkbox01" checked="checked">
			<label for="01-B" class="checkbox01"> グループ名B</label> <input
				type="checkbox" id="01-C" name="checkbox01"> <label
				for="01-C" class="checkbox01"> グループ名十文字まで</label>
		</div>


		<div class="c_box">
			<input type="checkbox" id="02-A" name="checkbox01"> <label
				for="02-A" class="checkbox01"> グループ名十文字まで</label> <input
				type="checkbox" id="02-B" name="checkbox01"> <label
				for="02-B" class="checkbox01"> グループ名B</label> <input type="checkbox"
				id="02-C" name="checkbox01" checked="checked"> <label
				for="02-C" class="checkbox01"> グループ名C</label>
		</div>


		<div class="c_box">
			<input type="checkbox" id="03-A" name="checkbox01" checked="checked">
			<label for="03-A" class="checkbox01"> グループ名A</label> <input
				type="checkbox" id="03-B" name="checkbox01"> <label
				for="03-B" class="checkbox01"> グループ名十文字まで</label> <input
				type="checkbox" id="03-C" name="checkbox01"> <label
				for="03-C" class="checkbox01"> グループ名C</label>
		</div>


		<div class="c_box">
			<input type="checkbox" id="04-A" name="checkbox01"> <label
				for="04-A" class="checkbox01"> グループ名十文字まで</label> <input
				type="checkbox" id="04-B" name="checkbox01"> <label
				for="04-B" class="checkbox01"> グループ名B</label> <input type="checkbox"
				id="04-C" name="checkbox01"> <label for="04-C"
				class="checkbox01"> グループ名十文字まで</label>
		</div>


		<div class="c_box">
			<input type="checkbox" id="05-A" name="checkbox01"> <label
				for="05-A" class="checkbox01"> グループ名A</label> <input type="checkbox"
				id="05-B" name="checkbox01"> <label for="05-B"
				class="checkbox01"> グループ名B</label> <input type="checkbox" id="05-C"
				name="checkbox01"> <label for="05-C" class="checkbox01">
				グループ名C</label>
		</div>
	</div>

	<a href="" class="okbtn"> 決定 </a>



	<div class="modal js-modal">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				編集内容は保持されませんが<br> よろしいですか？
			</p>
			<div class="btn_box">
				<a href="" class="js-modal-close">キャンセル</a> <a href=""
					class="mobal_btn2">OK</a>
			</div>
		</div>
		<!--modal__inner-->
	</div>
	<!--modal-->


</body>
</html>