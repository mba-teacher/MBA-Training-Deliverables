<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import = "data.TemplateInfoBean,data.UserInfoBean"
	import ="java.util.ArrayList"%>
	<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>マイページ</title>
	<link rel="stylesheet" href="src/css/nav.css" />
	<link rel="stylesheet" href="src/css/template.css" />
	<link rel="stylesheet" href="src/css/scroll.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<%
//ログインユーザー情報をセッションから取得
UserInfoBean myb = (UserInfoBean)session.getAttribute("userInfoBean");
//ログインユーザーの定型文情報をセッションからリスト配列で取得
ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();
TemplateInfoList=(ArrayList<TemplateInfoBean>)session.getAttribute("TemplateInfoList");
//定型分情報を格納する変数を宣言
Integer tempId[] = new Integer[10];
String tempName[] = new String[10];
String tempContent[] = new String[10];
//定型分情報を代入
int i=0;
for(TemplateInfoBean bean:TemplateInfoList) {
	tempId[i]=bean.getTempleId();
	tempName[i]=bean.getTempleName();
	tempContent[i]=bean.getTempleContents();
	i++;
}
%>
<script>
var selectEdit;//編集選択中の行要素
var selectDelete;//削除選択中の行要素
var deleteList=[];//削除行の要素
var rowCount = 0;//定型文一覧の行数
//定型文のID
var tempId ={
		edit1:"<%out.print(tempId[0]);%>",edit2:"<%out.print(tempId[1]);%>",edit3:"<%out.print(tempId[2]);%>",
		edit4:"<%out.print(tempId[3]);%>",edit5:"<%out.print(tempId[4]);%>",edit6:"<%out.print(tempId[5]);%>",
		edit7:"<%out.print(tempId[6]);%>",edit8:"<%out.print(tempId[7]);%>",edit9:"<%out.print(tempId[8]);%>",
		edit10:"<%out.print(tempId[9]);%>"
		};
//定型文の名前
var tempName ={
		edit1:"<%out.print(tempName[0]);%>",edit2:"<%out.print(tempName[1]);%>",edit3:"<%out.print(tempName[2]);%>",
		edit4:"<%out.print(tempName[3]);%>",edit5:"<%out.print(tempName[4]);%>",edit6:"<%out.print(tempName[5]);%>",
		edit7:"<%out.print(tempName[6]);%>",edit8:"<%out.print(tempName[7]);%>",edit9:"<%out.print(tempName[8]);%>",
		edit10:"<%out.print(tempName[9]);%>"
		};
//定型文の内容
var tempContent ={
		edit1:"<%out.print(tempContent[0]);%>",edit2:"<%out.print(tempContent[1]);%>",edit3:"<%out.print(tempContent[2]);%>",
		edit4:"<%out.print(tempContent[3]);%>",edit5:"<%out.print(tempContent[4]);%>",edit6:"<%out.print(tempContent[5]);%>",
		edit7:"<%out.print(tempContent[6]);%>",edit8:"<%out.print(tempContent[7]);%>",edit9:"<%out.print(tempContent[8]);%>",
		edit10:"<%out.print(tempContent[9]);%>"
		};
</script>

	<div class="flex_container">
		<div class="nav-area">

			<div class="logo-area">
				<img src="src/img/logo_white.png">
			</div>

			 <form name="nav-trans" method="post">

			<input type="image" src="<%=request.getContextPath()%><%= myb.getProfileImgPath() %>" class="nav-icon"
			id="my-icon" formaction="<%=request.getContextPath()%>/mypage">

			<input type="image" src="<%=request.getContextPath()%>/src/img/mb_0_boad.png" class="nav-icon"
			formaction="<%=request.getContextPath()%>/board">

			<input type="image" src="<%=request.getContextPath()%>/src/img/mb_0_address.png" class="nav-icon"
			formaction="<%=request.getContextPath()%>/addressbook">

			<%-- 外部リンク一覧のポップアップを出すだけなので遷移先なし --%>
			<img src="<%=request.getContextPath()%>/src/img/mb_0_link.png" class="nav-icon" id="link-show">
			</form>

			<div class="nav-bottom">
				<%-- 通知のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_notice.png" class="nav-icon" id="alarm-show">
				<%-- その他のポップアップを出すだけなので遷移先なし --%>
				<img src="<%=request.getContextPath()%>/src/img/mb_0_other.png" class="nav-icon" id="link-botoom-show">
			</div>
		</div>

		<div class="popup-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_LINEWORKS.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_calendar.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_attendance.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_drive.png">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_mail.png">
				</div>
			</div>
		</div>

		<div class="popup-bottom-link">
			<div class="link-hide popup-bg"></div>
			<div class="popup-content">
				<div class="popup-icon">
					<img src="<%=request.getContextPath()%>/src/img/mb_0_config.png" id="config_img">
					<a href="<%=request.getContextPath()%>/logout">
						<img src="<%=request.getContextPath()%>/src/img/mb_0_signout.png">
					</a>
				</div>
			</div>
		</div>


	<script src="src/js/nav.js"></script>

		<div class="top">
<!-- 修正箇所 --><h1 class=title>定型文編集</h1>
		 	<p class="notice"><%out.print(session.getAttribute("notice"));%></p>
<!-- 修正箇所 --><form action = "http://localhost:8080/MBoard/board"method="get" class="back_button"><input type="submit" class="backbtn" value="戻る" ></form>
		</div>

	<div class="template">
		<div class="template_list">
			<p >定型文一覧</p>
			<table id="table" border="1"  >
			    <tr class=foot>
				        <td colspan="1">
<!-- 修正箇所 -->
							<img id="coladd" src="<%=request.getContextPath()%>/src/img/mb_e_plus.png" onclick="coladd()">
				        </td>
				        <td colspan="4">
				            定型文を追加
				        </td>
			    	</tr>
			</table>
		 </div>

		<div class="change_area">
			 <form action="template" method="post" name="form" id="save">
			 <input id="template_title" type="text" placeholder="タイトル"  value="" name="tempName"><br>
			 <textarea id="template_content"  placeholder="本文を入力"  name="tempContent"  wrap="soft"></textarea><br>
			 <input class="template_save" type="submit" value="保存" >
			 <input type="hidden"  name="action" id="saveAction" value="" class="notice">
	      	 <input type="hidden" id="saveHidden" name="tempId" value="" >
			 <input class="template_soufu" type="button" value="添付" >
			 <input class="template_kakou" type="button" value="加工" >
			 </form>

		 </div>

<!-- 		ポップアップウインドウ -->
		 <div class="modal js-modal">
		     <div class="modal__bg js-modal-close"></div>
		     <div class="modal__content">
<!-- 修正箇所 --><h2>本当に削除してもよろしいですか？</h2>
				<input type="button" value="キャンセル" class="js-modal-close modal_cancel"  onclick="popUpClose()">
				<form action="template" method="post" name="form" id="delete" class="modal_form" >
					<input type="submit" value="OK"  id="" class="modal_ok">
					<input type="hidden"  name="action" id="deleteAction" value="" class="notice">
	      			<input type="hidden" id="deleteHidden" name="tempId" value="" >
				</form>
		     </div>
		 </div>

	</div>


	</div>

  <script>
	//削除ボタン押された時
    $('#delete').submit(function(e) {
    	var deleteButtonHidden = document.getElementById('deleteHidden');
    	var deleteAction = document.getElementById('deleteAction');
    	deleteButtonHidden.value=tempId[selectDelete.id];
    	if(tempId[selectDelete.id]!="null"){
    		deleteAction.value="削除";
    	}
    });
    //保存ボタン押された時
    $('#save').submit(function(e) {
    	var saveAction = document.getElementById('saveAction');
    	var saveButtonHidden = document.getElementById('saveHidden');
    	saveButtonHidden.value=tempId[selectEdit.id];
    	if(tempId[selectEdit.id]=="null"){
    		saveAction.value="作成";
    	}else{
    		saveAction.value="更新";
    	}
    	var template_content = document.getElementById('template_content');
    	//template_content.value =template_content.value.replace("\r\n", "<br>");


    });

    var $name1 = $('#template_title');
	//編集中の定型文の名前変更時に、一覧に表示されている名前も連動して変わる
    $name1.on('keyup change',function(ready){
    var $name2 = $('#'+selectEdit.id+'name');
    $name2.val($name1.val())
    tempName[selectEdit.id]=$name1.val();
    });
    $name2.on('keyup change',function(ready){
    $name1.val($name2.val())
    });
  </script>

<script>
//メッセージを自動的にフェードアウト
setTimeout(function(){    //x秒後に実行
	$('.notice')
      .fadeOut(500);
 },1000);                  //setTimeoutの秒数指定



		//データベースにある定型文の数分追加
		for(var key in tempName){
			if(tempName[key]!='null'){
				coladd();
			}
		}

		//デフォルトで1番上の定型文を編集中にする
		document.getElementById('edit1').click();


		//定型文追加
		//定型文を追加ボタン押下時に呼び出し
		function coladd() {
			//10行未満の時
			if (rowCount < 10) {
				//テーブルを取得
				var table = document.getElementById("table");
				// 行を行末に追加
				var row = table.insertRow(rowCount);
				//td分追加
				var cell1 = row.insertCell();
				var cell2 = row.insertCell();
				var cell3 = row.insertCell();
				//クラスを追加
				cell1.className = 'template_Column';
				//セル結合
				cell1.colSpan = 3;

				//行数と
				rowCount++;

				// セルの内容入力
				if(deleteList.length>0){
					deleteList.sort();
					cell1.innerHTML = tempName[deleteList[0]];
					cell2.innerHTML = '<input type="button" value="編集" class="list_edit"  id='+deleteList[0]+' onclick="edit(this)">';
					cell3.innerHTML =  '<input type="button" value="削除" class="list_delete" id='+deleteList[0]+' onclick="popUp(this)">';
					deleteList.shift();
				}else{
					if(tempName["edit"+rowCount]=="null"){
						cell1.innerHTML =  '<input type="text" placeholder="定型文名前" readonly id='+"edit"+rowCount+"name"+' >';
					}else{
						cell1.innerHTML =  '<input type="text" placeholder="定型文名前" value="'+tempName['edit'+rowCount]+'" readonly id='+"edit"+rowCount+"name"+' >';
					}
					cell2.innerHTML = '<input type="button" value="編集" class="list_edit" id='+"edit"+rowCount+' onclick="edit(this)">';
					cell3.innerHTML =  '<input type="button" value="削除" class="list_delete" id='+"edit"+rowCount+' onclick="popUp(this)">';
				}
			}
		}

		//編集定型文切り替え
		//定型文の編集ボタン押下時に呼び出し
		function edit(obj){
			selectEdit=obj;
			var title=document.getElementById("template_title");
			if(tempName[selectEdit.id]=="null"){
				title.value="";
			}else{
				title.value=tempName[selectEdit.id];
			}

			var content=document.getElementById("template_content");
			if(tempContent[selectEdit.id]=="null"){
				content.value="";
			}else{
				content.value=tempContent[selectEdit.id+""];
			}

		}

/* 		//定型文削除
		//削除ポップアップ時にOKボタン押下時に呼び出し
		function coldel() {
			rowCount--;
			// 削除ボタンを押下された行を取得
			deletetTr = selectDelete.parentNode.parentNode;

			deleteList.push(selectDelete.id);

			// trのインデックスを取得して行を削除する
			deletetTr.parentNode.deleteRow(deletetTr.sectionRowIndex);
			// 現在編集中の定型文行を取得
			editTr = selectEdit.parentNode.parentNode;

			if(deletetTr==editTr){
				var title=document.getElementById("template_title");
				title.value=tempName["edit1"];
				var content=document.getElementById("template_content");
				content.value=tempContent["edit1"];
			}
			selectDelete=null;
			popUpClose();//ポップアップ閉じる
		} */

		//削除ポップアップ表示
		//定型文の削除ボタン押下時に呼び出し
		function popUp(obj) {
			selectDelete=obj;
			$('.js-modal').fadeIn();
		}

		function popUpClose() {
			$('.js-modal').fadeOut();
		}

		$(function() {
			$('.js-modal-close').on('click', function() {

				$('.js-modal').fadeOut();
				return false;
			});
		});

	</script>

</body>
</html>