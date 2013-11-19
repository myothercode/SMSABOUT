/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-13
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
 $(document).ready(function(){
     /*$("message").ajaxError(function(e,xhr,opt){
         alert("提交出错 " + opt.url + ": " + xhr.status + " " + xhr.statusText);
     });*/
 });

/*批量导入短信*/
function batchSubmitSms(){
    var msg2=$('#msg2').val();
    //开始上传文件时显示一个图片,文件上传完成将图片隐藏
    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
    //执行上传文件操作的函数
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
        url:'/smsservice/sms/batchSendSMS',
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:'multipartFiles',           //文件选择框的id属性
        dataType:'text',                       //服务器返回的格式,可以是json或xml等
        data:{msg:msg2},
    success:function(data, status){        //服务器响应成功时的处理函数
        /*data = data.replace("<PRE>", '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
        data = data.replace("</PRE>", '');
        data = data.replace("<pre>", '');
        data = data.replace("</pre>", ''); //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]*/
        alert(data);
        /*if(data.substring(0, 1) == 0){     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
            //$("img[id='uploadImage']").attr("src", data.substring(2));
            //$('#result').html("图片上传成功<br/>");
            alert("导入成功!");
        }else{
            alert("导入失败!");
        }*/
    },
    error:function(data, status, e){ //服务器响应失败时的处理函数
       alert('图片上传失败，请重试！！');
    }

});

}


/*批量导入短信*/
function submitMms(){

    var phoneNo=$('#phoneNo').val();
    var uplist = $("input[name^=multipartFiles]");
    var arrId = [];
    for (var i=0; i< uplist.length; i++){
        if(uplist[i].value){
            arrId[i] = uplist[i].id;
        }
    }

    //开始上传文件时显示一个图片,文件上传完成将图片隐藏
    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
    //执行上传文件操作的函数
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
        url:'/smsservice/mms/mmsSend',
        secureuri:false,                       //是否启用安全提交,默认为false
        //fileElementId:'multipartFiles2',           //文件选择框的id属性
        files:[$('#multipartFiles1'),$('#multipartFiles2')],
        dataType:'text',                       //服务器返回的格式,可以是json或xml等
        method:'post',
        data:{phoneNo:phoneNo},
        success:function(data, status){        //服务器响应成功时的处理函数

            alert(data);

        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            alert('图片上传失败，请重试！！');
        }

    });

}



/*提交短信*/
function submitSms(){
    var phoneNo=$("#phoneNo").val();
    var msg=$("#msg").val();
    if(!strIsNull(phoneNo)&&!strIsNull(msg)){
        $.ajax({
            url: "/smsservice/sms/sendSMS",
            type: "post",
            dataType: "html",
            async: false,
            data: {phoneNo:phoneNo,msg:msg},
            success:function(data){
                $("#phoneNo").val('');
                $("#msg").val('');
                alert(data);
            },
            error:function(Request, textStatus, errorThrown){
                alert("提交出错");
            }
             });

    }else{
        alert("请填入必要信息！")
    }
}

/*添加活动*/
function insertActive(){
    var hdid=$('#hdid').val();
    var amsg=$('#amsg').val();
    var user_id=$('#user_id').val();
    if(!strIsNull(hdid)&&!strIsNull(amsg)&&!strIsNull(user_id)){
        $.ajax({
            url: "/smsservice/sms/insertActive",
            type: "post",
            dataType: "html",
            async: false,
            data: {hdid:hdid,msg:amsg,user_id:user_id},
            success:function(data){
                $("#hdid").val('');
                $("#amsg").val('');
                $("#user_id").val('');
                alert(data);
            },
            error:function(Request, textStatus, errorThrown){
                alert("提交出错");
            }
        });
    }else{
        alert("请填入必要信息！")
    }
}
