<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 13-10-9
  Time: 下午11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/smsPage.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/utils.js" type="text/javascript"></script>
</head>
<body>
<div id="message" style="width: 200px"></div>
<form action="" method="post" >
    <table align="left">
        <tr>
            <td>手机号码<input id="phoneNo" name="phoneNo" onkeypress="return inputOnlyNUM(event,this)"/></td>
            <td>信息内容<input id="msg" name="msg"/></td>
            <td><button type="button" onclick="submitSms()" value="提交">提交</button></td>
        </tr>
    </table>
</form>
</body>
</html>