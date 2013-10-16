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
