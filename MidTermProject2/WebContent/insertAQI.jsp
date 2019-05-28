<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Midterm Project 2</title>
<link rel="stylesheet" href="style.css">

<script>
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("theid").addEventListener("blur", checkid);
    document.getElementById("aqi").addEventListener("blur", checkaqi);
    document.getElementById("pm").addEventListener("blur", checkpm);
    document.getElementById("sitename").addEventListener("blur", checkName);
    document.getElementById("ptime").addEventListener("blur", checktime);
    
});
	function theinsert(){
		document.forms[0].action="AQI.do?doaction=1" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
		}
	
	function theupdate(){
		document.forms[0].action="AQI.do?doaction=2" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	}
	function thedelete(){
		document.forms[0].action="AQI.do?doaction=3" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	}
	function thequery(){
		document.forms[0].action="AQI.do?doaction=4" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	}

	function checkid(){
		let idobj = document.getElementById("theid");
		let idvalue= idobj.value;
		document.getElementById("jid").innerHTML="";
		if(idvalue==""){
			document.getElementById("iderr").innerHTML="錯誤！觀測站ID不可為空白";
		}else if(isNaN(parseInt(idvalue))){
			document.getElementById("iderr").innerHTML="錯誤！觀測站ID需為整數";
		}else{
			document.getElementById("iderr").innerHTML="輸入正確";
		}
		}
	function checkaqi(){
		let aqiobj = document.getElementById("aqi");
		let aqivalue= aqiobj.value;
		let aqii =parseInt(aqivalue);
		document.getElementById("aqier").innerHTML="";
		if(aqivalue==""){
			document.getElementById("aqierr").innerHTML="錯誤！AQI不可為空白";
		}else if(isNaN(aqii)){
			document.getElementById("aqierr").innerHTML="錯誤！AQI需為整數";
		}else{
			document.getElementById("aqierr").innerHTML="輸入正確";
		}
		if(aqii<=50){
			document.getElementById("sta").value="良好";
		}else if(aqii>50 && aqii<=100){
			document.getElementById("sta").value="普通";
		}else if(aqii>100 && aqii<=150){
			document.getElementById("sta").value="對敏感族群不健康";
		}else if(aqii>150 && aqii<=200){
			document.getElementById("sta").value="對所有族群不健康";
		}else if(aqii>200 && aqii<=300){
			document.getElementById("sta").value="非常不健康";
		}else if(aqii>300 && aqii<=500){
			document.getElementById("sta").value="危害";
		}else{
			document.getElementById("aqierr").innerHTML="錯誤！請重新輸入";
		}
		}
	function checkpm(){
		let pmobj = document.getElementById("pm");
		let pmvalue= pmobj.value;
		document.getElementById("pmerr").innerHTML="";
		if(isNaN(parseInt(pmvalue))){
			document.getElementById("pmer").innerHTML="錯誤！PM2.5需為整數";
		}else{
			document.getElementById("pmer").innerHTML="輸入正確";
		}
		}
	function checkName() {
        let theNameObj = document.getElementById("sitename");
        let theNameObjVal = theNameObj.value;
        document.getElementById("siten").innerHTML="";
        if (theNameObjVal == "")
            document.getElementById("sitenameerr").innerHTML = "錯誤！觀測站地點不可為空白";
        else if (theNameObjVal.length < 2) {
            document.getElementById("sitenameerr").innerHTML = "錯誤！觀測站名長度不可小於兩中文字";
        }
        else if (theNameObjVal.length >= 2) {
            for (let i = 0; i <= theNameObjVal.length; i++) {
                Namechr = theNameObjVal.charCodeAt(i);
                if (Namechr < 0x4E00 || Namechr > 0x9FA5) {
                    document.getElementById("sitenameerr").innerHTML = "錯誤！輸入非中文字";
                    break;
                } else {
                    document.getElementById("sitenameerr").innerHTML = "輸入正確";
                }
            }
        }
    }
	
	function checktime(){

            let datevalue = document.getElementById("ptime").value;
            let re = /^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/;
            document.getElementById("tierr").innerHTML=="";
        	if (re.test(datevalue)) {
               document.getElementById("pter").innerHTML ="輸入正確";
            }else{
            	document.getElementById("pter").innerHTML ="錯誤！格式錯誤請輸入正確日期與時間";
            }

	}
	function getTime(){
		let nowdate= new Date();
		
		let yy= nowdate.getFullYear();
		let MM = nowdate.getMonth()+1;
		let dd = nowdate.getDate();
		let hh = nowdate.getHours();
		let mm = nowdate.getMinutes();
		let ss = nowdate.getSeconds();
		if(MM<10){
			 MM = "0"+MM.toString();
		}
		if(dd<10){
			 dd = "0"+dd.toString();
		}
		if(hh<10){
			hh = "0"+hh.toString();
		}
		if(mm<10){
			mm = "0"+mm.toString();
		}
		if(ss<10){
			ss = "0"+ss.toString();
		}
		str = yy+"-"+MM+"-"+dd+" "+hh+":"+mm+":"+ss;
		document.getElementById("ptime").value=str;
	}
		
</script>
</head>
<body>
<h1>歡迎來到空氣品質系統</h1>
<h2 style="color:red;">${statusfail}</h2>
<br>
<br>
<div class="showbox">
<form name="insertAQI">
觀測站id:<input type="text" name="Siteid" id="theid" style="inline-height:20px;"><span id="iderr"></span>
<font id="jid" color='red' size='-1'>${errorMSG.iderror}</font><br>
觀測站名稱:<input type="text" name="SiteName" id="sitename" ><span id="sitenameerr"></span>
<font id="siten" color='red' size='-1'>${errorMSG.sitenameerror}</font><br>
地區:<select name="Country">
	<option value="基隆市">基隆市</option>
	<option value="嘉義市">嘉義市</option>
	<option value="台北市">台北市</option>
	<option value="嘉義縣">嘉義縣</option>
	<option value="新北市">新北市</option>
	<option value="台南市">台南市</option>
	<option value="桃園縣">桃園縣</option>
	<option value="高雄市">高雄市</option>
	<option value="新竹市">新竹市</option>
	<option value="屏東縣">屏東縣</option>
	<option value="新竹縣">新竹縣</option>
	<option value="台東縣">台東縣</option>
    <option value="苗栗縣">苗栗縣</option>
	<option value="花蓮縣">花蓮縣</option>
	<option value="台中市">台中市</option>
	<option value="彰化縣">彰化縣</option>
	<option value="澎湖縣">澎湖縣</option>
	<option value="南投縣">南投縣</option>
	<option value="金門縣">金門縣</option>
	<option value="雲林縣">雲林縣</option>
	<option value="連江縣">連江縣</option>
</select><br>

AQI:<input type="text" name="AQI" id="aqi" ><span id="aqierr"></span>
<font id="aqier" color='red' size='-1'>${errorMSG.aqierror}</font><br>
狀態:<select name="Status" id="sta">
<option value="良好">良好</option>
<option value="普通">普通</option>
<option value="對敏感族群不健康">對敏感族群不健康</option>
<option value="對所有族群不健康">對所有族群不健康</option>
<option value="非常不健康">非常不健康</option>
<option value="危害">危害</option>
</select><br>
PM2.5:<input type="text" name="pm25" id="pm" ><span id="pmer"></span>
<font id="pmerr" color='red' size='-1'>${errorMSG.pmerror}</font><br>
發布時間:<input type="text" name="ptime" id="ptime" ><span id="pter"></span>
<input type="button" onclick="getTime()" id="getptime" value="取得現在時間"><br>
<font color='blue' size="-1">&nbsp;&nbsp;格式為yyyy-MM-dd mm:ss</font>
<font  id="tierr" color='red' size='-1'>${errorMSG.timeerror}</font><br>
<input type="button"  onclick="theinsert()" value="送出新增"  name="insert"/>
<input type="button" onclick="theupdate()" value="送出修改"  name="update"/>
<input type="button" onclick="thedelete()" value="送出刪除"  name="delete"/>
<input type="button" onclick="thequery()" value="送出查詢 "  name="query"/>
</form>
</div>
<h3>${errorMSG.allerrors}</h3>
</body>
</html>
