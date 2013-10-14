<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 13-10-9
  Time: 下午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="/smsservice/staticResource/loginPageRes/main.css" />
    <link rel="stylesheet" type="text/css" href="/smsservice/staticResource/loginPageRes/bgstretcher.css" />

    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/smsservice/staticResource/loginPageRes/bgstretcher.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){

            var pathimg='/smsservice/staticResource/loginPageRes/';
            $('BODY').bgStretcher({
                images: [pathimg+'images/sample-1.jpg',
                    pathimg+'images/sample-2.jpg', pathimg+'images/sample-3.jpg',
                    pathimg+'images/sample-4.jpg', pathimg+'images/sample-5.jpg', pathimg+'images/sample-6.jpg'],
                imageWidth: 1024,
                imageHeight: 768,
                slideDirection: 'N',
                slideShowSpeed: 1000,
                transitionEffect: 'fade',
                sequenceMode: 'normal',
                buttonPrev: '#prev',
                buttonNext: '#next',
                pagination: '#nav',
                anchoring: 'left center',
                anchoringImg: 'left center'
            });

        });
    </script>
    <script type="text/javascript" src="/smsservice/staticResource/loginPageRes/main.js"></script>
</head>
<body>
<div id="page" style="display: none_;">
    <div class="page-top">&nbsp;</div>
    <div class="page-content">
        <form action="/smsservice/login/loginUser" method="post" >
            <table >
                <tr>
                    <td><img src="/smsservice/staticResource/loginPageRes/images/unicom.png" width="200" height="137" /></td>
                    <td><input id="userId" name="userId"/></td>
                    <td><input id="passWord" name="passWord" type="password"/></td>
                    <td><button type="submit">登录</button></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="page-bottom">&nbsp;</div>
</div>


<div style="text-align:center;clear:both">

</div>



</body>
</html>