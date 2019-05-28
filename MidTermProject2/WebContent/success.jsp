<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>觀測站資料新增成功</title>

<link rel="stylesheet" href="style.css">
</head>
<body>
<h1>觀測站資料${status}成功</h1>
<div class="showbox">
<p>觀測站id:${AQIBean.siteid}</p><br>
<p>觀測站地點:${AQIbean.siteName}</p><br>
<p>地區:${AQIbean.country}</p><br>
<p>AQI:${AQIbean.AQI}</p><br>
<p>狀態:${AQIbean.status}</p><br>
<p>PM2.5:${AQIbean.PM25}</p><br>
<p>發布時間:${AQIbean.publishTime}</p><br>
</div>
<div class="back">
<a href="insertAQI.jsp">回資料輸入頁</a>
</div>>
</body>
</html>