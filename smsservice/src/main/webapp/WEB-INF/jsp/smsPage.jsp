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
    <link rel="stylesheet" href="/smsservice/staticResource/css/ajaxfileupload.css" type="text/css" />
    <link rel="stylesheet" href="/smsservice/staticResource/css/mycss.css" type="text/css" />
    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/smsPage.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/utils.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/ajaxfileupload.js" type="text/javascript"></script>
</head>
<body>
<div><br>

    <table align="left" width="100%" class="divborder">
        <tr>
            <td>发送短信：&nbsp;&nbsp;</td>
            <td>手机号码<input id="phoneNo" name="phoneNo" onkeypress="return inputOnlyNUM(event,this)"/></td>
            <td>信息内容<input id="msg" name="msg"/></td>
            <td><button type="button" onclick="submitSms()" value="提交">提交</button></td>
        </tr>
    </table>

    <table align="left" width="100%" class="divborder"  style="margin-top: 50px">
        <tr>
            <td>批量发送短信：&nbsp;&nbsp;</td>
            <td>导入号码<input type="file" id="multipartFiles" name="multipartFiles"/></td>
            <td>信息内容<input id="msg2" name="msg"/></td>
            <td><button type="button" onclick="batchSubmitSms()" value="提交">提交</button></td>
        </tr>
    </table>

    <table align="left" width="100%" class="divborder"  style="margin-top: 50px">
        <tr>
            <td>根据组号发送短信：&nbsp;&nbsp;</td>
            <td>信息内容<input id="msg3" name="msg"/></td>
            <td>组号(1-141)<input id="phoneNo3" name="phoneNo" /></td>
            <td><button type="button" onclick="SubmitSmsByGroupNo()" value="提交">提交</button></td>
        </tr>
    </table>

    <table align="left" width="100%" class="divborder" style="margin-top: 50px">
        <tr>
            <td>增加业务：&nbsp;&nbsp;</td>
            <td>业务代码&nbsp;GPS<input id="hdid" /></td>
            <td>业务提示内容<input id="amsg" /> </td>

        </tr>
        <tr>
            <td></td>
            <td>商家id<input id="user_id" /> </td>
            <td><button type="button" onclick="insertActive()" value="提交">提交</button></td>
        </tr>
    </table>
</div>
</body>
</html>