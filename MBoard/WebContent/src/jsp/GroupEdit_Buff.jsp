<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>グループ編集画面</title>
<link type="text/css" rel="stylesheet" href="GroupEdit.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
 <script type="text/javascript" src="test-jscroll/js/jquery.jscroll.min.js"></script>
 <script src="test-jscroll/js/jquery.inview.min.js"></script>
	<script type="text/javascript" src="GroupEdit.js"></script>
</head>
<body>

	<%-- <div class="scroll" data-max="20" data-lastnum="10"><%-- data-〇〇でカスタムデータ属性を作れる --%>

<!-- 	<div class="scroll"> -->
	<div class="jscroll">
	<% for(int i =0;i<10;i++){ %>
		<div id="group">
			<p id ="tx">グループ名2</p>
			<div class="bottonEdit">
				<input class="button1" type="button" id="showEdit" value="編集">
			</div>
			<div class="bottonDele">
				<input class="button1" type="button" id="showDele" value="削除">
			</div>
		</div>
		<% if(i==9){ %>
			<a class="next" href="GroupEdit_Buff.jsp">next page</a>
		<% } %>
	<% } %>

	</div>
<!-- 	</div> -->

	<!-- レイヤー -->
	<div id="layer"></div>
	<!-- ポップアップ -->
	<div id="popup">
    	<p>グループ作成/編集</p>
    	<g>グループ名</g>
		<div><input type="text" name="groupName" value=""
			style="width:320px;height:30px;font-size:25px;" maxlength=10></div>
    	<t>10文字以内</t>
    	<div class="inner">
    		<input class="button2" type="button" id="close" value="キャンセル">
    		<input class="button2" type="button" id="create" value="作成">
    	</div>
	</div>

	<div id="delepop">
    	<p>本当に削除してもよろしいですか？</p><br>
    	<div class="inner">
    		<input class="button2" type="button" id="closeDele" value="キャンセル">
    		<input class="button2" type="button" id="delete" value="OK">
    	</div>
	</div>

</body>
</html>