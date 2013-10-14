<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 13-10-14
  Time: 下午4:50
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="/smsservice/staticResource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <style type="text/css">
            /* common styling */
            /* set up the overall width of the menu div, the font and the margins */
        .menu {
            font-family: arial, sans-serif;
            width:750px;
            margin-top:50px;
            margin-left: 0px;
            margin-bottom: 2px;
        }
            /* remove the bullets and set the margin and padding to zero for the unordered list */
        .menu ul {
            padding:0;
            margin:0;
            list-style-type: none;
        }
            /* float the list so that the items are in a line and their position relative so that the drop down list will appear in the right place underneath each list item */
        .menu ul li {
            float:left;
            position:relative;
        }
            /* style the links to be 104px wide by 30px high with a top and right border 1px solid white. Set the background color and the font size. */
        .menu ul li a, .menu ul li a:visited {
            display:block;
            text-align:center;
            text-decoration:none;
            width:104px;
            height:30px;
            color:#000;
            border:1px solid #fff;
            border-width:1px 1px 0 0;
            background:#c9c9a7;
            line-height:30px;
            font-size:11px;
        }
            /* make the dropdown ul invisible */
        .menu ul li ul {
            display: none;
        }
            /* specific to non IE browsers */
            /* set the background and foreground color of the main menu li on hover */
        .menu ul li:hover a {
            color:#fff;
            background:#b3ab79;
        }
            /* make the sub menu ul visible and position it beneath the main menu list item */
        .menu ul li:hover ul {
            display:block;
            position:absolute;
            top:31px;
            left:0;
            width:105px;
        }
            /* style the background and foreground color of the submenu links */
        .menu ul li:hover ul li a {
            display:block;
            background:#faeec7;
            color:#000;
        }
            /* style the background and forground colors of the links on hover */
        .menu ul li:hover ul li a:hover {
            background:#dfc184;
            color:#000;
        }
    </style>
    <!--[if lte IE 6]>
    <style type="text/css">
            /* styling specific to Internet Explorer IE5.5 and IE6. Yet to see if IE7 handles li:hover */
            /* Get rid of any default table style */
        table {
            border-collapse:collapse;
            margin:0;
            padding:0;
        }
            /* ignore the link used by 'other browsers' */
        .menu ul li a.hide, .menu ul li a:visited.hide {
            display:none;
        }
            /* set the background and foreground color of the main menu link on hover */
        .menu ul li a:hover {
            color:#fff;
            background:#b3ab79;
        }
            /* make the sub menu ul visible and position it beneath the main menu list item */
        .menu ul li a:hover ul {
            display:block;
            position:absolute;
            top:32px;
            left:0;
            width:105px;
        }
            /* style the background and foreground color of the submenu links */
        .menu ul li a:hover ul li a {
            background:#faeec7;
            color:#000;
        }
            /* style the background and forground colors of the links on hover */
        .menu ul li a:hover ul li a:hover {
            background:#dfc184;
            color:#000;
        }
    </style>
    <![endif]-->
</head>
<body>

<div class="menu" style="float: left">
    <ul>
        <li><a class="hide" >短信测试</a>
            <!--[if lte IE 6]>
            <a >短信测试
                <table><tr><td>
            <![endif]-->
            <ul>
                <li><a addr="/smsservice/sms/smsPage" title="单条测试" onclick="menuChange(this)">单条测试</a></li>
            </ul>
            <!--[if lte IE 6]>
            </td></tr></table>
            </a>
            <![endif]-->
        </li>

        <li><a class="hide" >##</a>
            <!--[if lte IE 6]>
            <a >##
                <table><tr><td>
            <![endif]-->
            <ul>
                <li><a href="#" title="#">#</a></li>
            </ul>
            <!--[if lte IE 6]>
            </td></tr></table>
            </a>
            <![endif]-->
        </li>
        <li><a class="hide" >##</a>
            <!--[if lte IE 6]>
            <a >##
                <table><tr><td>
            <![endif]-->
            <ul>
                <li><a href="#" title="#">#</a></li>
            </ul>
            <!--[if lte IE 6]>
            </td></tr></table>
            </a>
            <![endif]-->
        </li>
        <li><a class="hide" >##</a>
            <!--[if lte IE 6]>
            <a >##
                <table><tr><td>
            <![endif]-->
            <ul>
                <li><a href="#" title="#">#</a></li>
            </ul>
            <!--[if lte IE 6]>
            </td></tr></table>
            </a>
            <![endif]-->
        </li>
        <li><a class="hide" >##</a>
            <!--[if lte IE 6]>
            <a >##
                <table><tr><td>
            <![endif]-->
            <ul>
                <li><a href="#" title="#">#</a></li>
            </ul>
            <!--[if lte IE 6]>
            </td></tr></table>
            </a>
            <![endif]-->
        </li>
    </ul>

    <div class="clear"> </div>
</div>
<div>
    <iframe width="750px" height="800px" frameborder="0px" scrolling="no" id="frame12"></iframe>
</div>
</body>

<script type="text/javascript">
    function menuChange(obj){
       var addr=$(obj).attr("addr");
       $('#frame12').prop("src",addr);
    }
</script>

</html>