<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<form action="../uploadDate.do" method="post" enctype="multipart/form-data">
	<br/>
	<h1>上传文件测试</h1>
	<br/>
	文件名：<input type="text" name="filePath" value="img/123.jpg" /><br/>
	文件：<input type="file" name="fileData"  /><br/>
	<br/>
	<input type="submit" value="上传" />
	<br/>
</form>

</body>
</html>