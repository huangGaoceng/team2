<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/15
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/fileservlet" enctype="multipart/form-data" method="post">
    姓名：<input type="text" name="name"><br>
    文件上传：<input type="file" name="pic"><br>
    <input type="submit" value="提交">
  </form>
  <hr>
  <h2>文件下载</h2>
  <a href="${pageContext.request.contextPath}/fileservlet?fname=hdfghdf.png">下载</a>
  </body>
</html>
