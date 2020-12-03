<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント修正・削除</title>
<link rel="stylesheet" href="../css/account_fix.css">
<link rel="stylesheet" href="../css/cansel_modal.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../js/account_fix.js"></script>
<script src="../js/account_fix_modal.js"></script>
</head>
<body>

	<a href="" class="backbtn js-modal-open"> 戻る </a>
	<a href="" class="okbtn"> 修正 </a>

	<div class="title">
		<h1>アカウント修正・削除</h1>
	</div>



	<div id="realWrite">
		<p></p>
	</div>

	<div id="realText">
		<input type="text" value="" placeholder="　ユーザー名検索" class="textbox">
	</div>



	<div class="test">

		<div class="user_name">
			<div class="user_list">
				<P>
					ユーザー <span>管理者権限</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					正方形 太郎 <span> <input type="radio" name="authority"
						value="yes" checked="checked">あり <input type="radio"
						name="authority" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					今このユーザー名は二十文字記入しています <span> <input type="radio"
						name="authority2" value="yes">あり <input type="radio"
						name="authority2" value="none" checked="checked">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority3"
						value="yes">あり <input type="radio" name="authority3"
						value="none" checked="checked">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority4"
						value="yes">あり <input type="radio" name="authority4"
						value="none" checked="checked">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority5"
						value="yes" checked="checked">あり <input type="radio"
						name="authority5" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority6"
						value="yes" checked="checked">あり <input type="radio"
						name="authority6" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority7"
						value="yes" checked="checked">あり <input type="radio"
						name="authority7" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority8"
						value="yes" checked="checked">あり <input type="radio"
						name="authority8" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority9"
						value="yes" checked="checked">あり <input type="radio"
						name="authority9" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority10"
						value="yes" checked="checked">あり <input type="radio"
						name="authority10" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority11"
						value="yes" checked="checked">あり <input type="radio"
						name="authority11" value="none">なし
					</span>
				</P>
				<a href="" class="delete js-modal-open2"> 削除 </a>
			</div>

			<div class="user_list">
				<P>
					１２３４５６ <span> <input type="radio" name="authority12"
						value="yes">あり <input type="radio" name="authority12"
						value="none" checked="checked">なし
					</span>
				</P>
			</div>






		</div>
	</div>




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


	<div class="modal js-modal2">
		<div class="modal__bg"></div>
		<div class="modal__content">
			<p>
				<br>本当に削除してもよろしいですか？
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