<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
String path = request.getContextPath();    
// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量    
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;    
// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。    
pageContext.setAttribute("basePath",basePath);    
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<script type="text/javascript" src="<%=basePath%>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function doUpload() {  
    var formData = new FormData($( "#uploadForm" )[0]);  
    $.ajax({  
         url: '/excel/uploadExcelData',  
         type: 'POST', 
         enctype: 'multipart/form-data',
         data: formData,  
         async: false,  
         cache: false,  
         contentType: false,  
         processData: false,
         timeout: 180000,//单位秒
         success: function (returnData) {  
             alert(returnData.message);  
         },  
         error: function (returnData) {  
             alert(returnData.message);  
         }  
    });  
}  
</script>
</head>
<body>
    <form id="uploadForm">
        <input type="file" name="file" value="上传Excel数据"/>
        <input type="button" value="上传" onclick="doUpload()"/>
        <br/><br/>
    </form>

</body>
</html>