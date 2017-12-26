<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>图片识别</title>
<!-- 引入公共文件 -->
<link rel="stylesheet" href="bootstrap-3.3.0-dist/dist/css/bootstrap.min.css">
<script type="text/javascript" src="jquery-2.1.4.js"></script>
<script type="text/javascript" src="ws-public-uploadAjax.js"></script>
<script type="text/javascript" src="bootstrap-3.3.0-dist/dist/js/bootstrap.min.js"></script>
<style type="text/css">
.form-horizontal .form-group {
    margin-right: 0px !important; 
    margin-left: 0px !important; 
}
.divcss{
    margin: 0px auto;
    background-color: black;
    width: 500px;
    height: 230px;
    margin-left: 245px;
}
p{
 color:white
}
</style>
<script>
$(function(){
	$('#send').click(function(){
		var formData = new FormData($( "#form1" )[0]);
		face.tools.uploadAjaxRequest({
			   type: "POST",
			   url: "http://localhost/face/face/face/pic.action",
			   data: formData,
			   successfun: function(data){
				    $(".divcss").css('height','500');
				    $("#pic1").css({"height":"250","width":"300"});
					$('#pic1').attr("src","http://localhost/face/images/"+data.data.src);
					$('#age').text(data.data.age);
					$('#gender').text(data.data.gender);
					$('#beauty').text(data.data.beauty);
					$('#emotion').text(data.data.emotion);
					$('#ethnicity').text(data.data.ethnicity);
					$('#skinstatus').text(data.data.skinstatus);
					$('#glass').text(data.data.glass);
			   },
			   errorfun:function(){
				 alert("发送失败");
			   }
			});
	});
})
</script>
</head>
<body>
<div>
    <form class="form-horizontal" id="form1" style="margin-top: 20px;" method="post" enctype="multipart/form-data">
	  <div class="form-group">
	    <label for="inputEmail3" class="col-sm-2 control-label">图片</label>
	    <div class="col-sm-5">
	      <input type="file" class="form-control" id="pic" name="pic">
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="button" id="send" class="btn btn-default">发送</button>
	    </div>
	  </div>
	</form>
</div><!-- pageContainer -->
</body>
<div class="divcss">
  <p><img id="pic1"/></p>
  <p>年龄：<span id="age"></span></p>
  <p>性别：<span id="gender"></span></p>
  <p>颜值：<span id="beauty"></span></p>
  <p>情绪：<span id="emotion"></span></p>
  <p>人种：<span id="ethnicity"></span></p>
  <p>面部：<span id="skinstatus"></span></p>
  <p>眼镜：<span id="glass"></span></p>
</div>
</body>
</html>