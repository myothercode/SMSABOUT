<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>短信管理系统</title>
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
        }
        -->
    </style>
    <link href="/smsservice/staticResource/css/css.css" rel="stylesheet" type="text/css" />
    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="147" background="/smsservice/staticResource/images/top02.gif"><img src="/smsservice/staticResource/images/top03.gif" width="776" height="147" /></td>
    </tr>
</table>
<table width="562" border="0" align="center" cellpadding="0" cellspacing="0" class="right-table03">
    <tr>
        <td width="221"><table width="95%" border="0" cellpadding="0" cellspacing="0" class="login-text01">

            <tr>
                <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
                    <tr>
                        <td align="center"><img src="/smsservice/staticResource/images/ico13.gif" width="107" height="97" /></td>
                    </tr>
                    <tr>
                        <td height="40" align="center">&nbsp;</td>
                    </tr>

                </table></td>
                <td><img src="/smsservice/staticResource/images/line01.gif" width="5" height="292" /></td>
            </tr>
        </table></td>
        <td>
            <form action="/smsservice/login/loginUser" method="post" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="31%" height="35" class="login-text02">用户名称：<br /></td>
                <td width="69%"><input size="30" id="userId" name="userId"/></td>
            </tr>
            <tr>
                <td height="35" class="login-text02">密　码：<br /></td>
                <td><input id="passWord" name="passWord" type="password" size="30"/></td>
            </tr>


            <tr>
                <td height="35">&nbsp;</td>
                <td><input name="Submit2" type="submit" class="right-button01" value="确认登陆"  />
                    <input name="Submit232" type="reset" class="right-button02" value="重 置" /></td>
            </tr>
        </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>