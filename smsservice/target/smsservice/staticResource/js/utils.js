/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-13
 * Time: 下午9:00
 * To change this template use File | Settings | File Templates.
 */
/*判断字符串是否为空*/
function strIsNull(str){
   return str==null||str==''||str=='null';
}
function inputOnlyNUM(e, obj)//添加keypress事件  只允许输入数字
{
    if(obj != null && $(obj).val().length >=13) {	//长度不能超过9位
        return false;
    }
    var isie = (document.all) ? true : false;//判断是IE内核还是Mozilla
    var key;
    if (isie)
    {
        key = window.event.keyCode;//IE使用windows.event事件
    }
    else
    {
        key = e.which;//3个按键函数有一个默认的隐藏变量，这里用e来传递。e.which给出一个索引值给Mo内核（注释1）
    }
    if(key==8)
    {
        return true;
    }
    return /[0-9]/.test(String.fromCharCode(key));
}

function inputNUMAndPoint(e,obj,size)//添加keypress事件  只允许输入数字和size位小数点
{
    if(obj != null && $(obj).val().length >=12) {	//整数位加小数位的长度不能超过12位
        return false;
    }
    var isie = (document.all) ? true : false;//判断是IE内核还是Mozilla
    var key;
    if (isie)
    {
        key = window.event.keyCode;//IE使用windows.event事件
    }
    else
    {
        key = e.which;//3个按键函数有一个默认的隐藏变量，这里用e来传递。e.which给出一个索引值给Mo内核（注释1）
    }
    if(key==8)
    {
        return true;
    }

    var temp=(obj.value+String.fromCharCode(key)).match(/[.]/g);
    if(temp!=null)
    {
        if(temp.length>1)
        {
            return false;
        }
        if((obj.value+String.fromCharCode(key)).split(".")[1].length>size)
        {
            return false;
        }
    }
    return /[0-9.]/.test(String.fromCharCode(key));
}

function inputNoChinese(obj,valid)//valid=false 不允许小数点  valid=true 允许小数点 去除中文
{
    if(valid)
    {
        return $(obj).val().replace(/([^0-9\.])/g,'');
    }
    else
    {
        return $(obj).val().replace(/[^u4E00-u9FA5]/g,'');
    }
}
//重写toFixed四舍五入方法
Number.prototype.toFixed = function(d)
{
    var s=this+"";if(!d)d=0;
    if(s.indexOf(".")==-1)s+=".";s+=new Array(d+1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+ (d+1) +"})?)\\d*$").test(s))
    {
        var s="0"+ RegExp.$2, pm=RegExp.$1, a=RegExp.$3.length, b=true;
        if (a==d+2){a=s.match(/\d/g); if (parseInt(a[a.length-1])>4)
        {
            for(var i=a.length-2; i>=0; i--) {a[i] = parseInt(a[i])+1;
                if(a[i]==10){a[i]=0; b=i!=1;} else break;}
        }
            s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
        }if(b)s=s.substr(1);return (pm+s).replace(/\.$/, "");} return this+"";
};