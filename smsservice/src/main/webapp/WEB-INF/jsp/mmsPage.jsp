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
    <link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.0.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/smsPage.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/utils.js" type="text/javascript"></script>
    <script src="/smsservice/staticResource/js/ajaxfileupload.js" type="text/javascript"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.0.4/bootstrap.min.js"></script>
</head>
<body>
<div><br>

    <%--<table align="left" width="100%" class="divborder"  style="margin-top: 50px">
        <tr>
            <td>批量发送短信：&nbsp;&nbsp;</td>
            <td>选择图片<input type="file" id="multipartFiles" name="multipartFiles"/></td>
            <td>信息内容<input id="msg2" name="msg"/></td>
            <td><button type="button" onclick="submitMms()" value="提交">提交</button></td>
        </tr>
    </table>--%>

    <form class="form-horizontal">
        <fieldset>
            <div id="legend" class="">
                <legend class="">彩信图片发送</legend>
            </div>
            <div class="control-group">
                <label class="control-label">选择图片</label>
                <!-- File Upload -->
                <div class="controls">
                    <input class="input-file" id="multipartFiles1" name="multipartFiles" type="file" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">选择txt文件</label>
                <!-- File Upload -->
                <div class="controls">
                    <input class="input-file" id="multipartFiles2" name="multipartFiles" type="file" />
                </div>
            </div>

            <div class="control-group">

                <!-- Text input-->
                <%--@declare id="input01"--%><label class="control-label" for="input01">手机号码</label>
                <div class="controls">
                    <input type="text" placeholder="手机号码" class="input-xlarge" id="phoneNo" name='phoneNo' />
                    <p class="help-block">-</p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">-</label>

                <!-- Button -->
                <div class="controls">
                    <button class="btn btn-success" type="button" onclick="submitMms()" value="提交">提交</button>
                </div>
            </div>

        </fieldset>
    </form>

</div>
</body>
</html>