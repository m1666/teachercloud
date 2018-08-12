<%@ page language="java"  contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>Hello World!</h2>



springmvc上传文件
<form name="form1" action="/manage/file/upload_file.do" method="post" enctype="multipart/form-data">
    <input type="file" name="filePath" /><br>
    <input type="file" name="mainImage" /><br>
    pathType<input type="text" name="path" /><br>
    courseId <input type="text" name="courseId" /><br>
    classId<input type="text" name="classId" /><br>
    detail<input type="text" name="detail" /><br>
    mainImage<input type="text" name="mainImage" /><br>
    type<input type="text" name="type" /><br>
    <input type="submit" value="springmvc上传文件" /><br>
</form>




springmvc下载文件
<form name="form1" action="/manage/file/download_file.do" method="post" enctype="multipart/form-data">
    type<input type="text" name="type" /><br>
    name<input type="text" name="name" /><br>
    <input type="submit" value="springmvcx下载文件" /><br>
</form>
</body>
</html>
