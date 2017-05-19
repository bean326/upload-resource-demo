<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body onload="" style="background-image: url('http://115.159.73.236/resource/img/douqu_sys/beauty_contest_logo.jpg') !important ;background-repeat: no-repeat;background-position: bottom;">

<form action="../batchUploadImg.do" method="post" enctype="multipart/form-data">
	<br/>
	<h1>上传文件测试</h1>
	用户ID：<input type="text" name="guid"  /><br/>
	<br/>
	
	图片封面：<input type="file" name="imgCoverFile"  /><br/>
	视频封面：<input type="file" name="videoCoverFile"  /><br/>
	视频：<input type="file" name="videoFile"  /><br/>
	文件：<input type="file" name="files"  /><br/>
	文件：<input type="file" name="files"  /><br/>
	文件：<input type="file" name="files"  /><br/>
	<br/>
	<input type="submit" value="上传" />
	<br/>
</form>

</body>
</html>