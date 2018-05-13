


//对密码的验证
var user_password=document.getElementById("user_password");
function test3(){
    ////密码强度正则，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
    // var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/;
    var pPattern=/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g;
    if (!pPattern.test(user_password.value)){
        document.getElementsByClassName("error")[0].innerHTML="你输入的密码太简单，密码必须由6-16个英文字母和数字的字符串组成！";
        return false;
    }else{
        document.getElementsByClassName("error")[0].innerHTML="";
    }

}



//密码和确认密码全部输入才能进入
var aff=document.getElementById("aff");
var userpassword=document.getElementById("user_password");
var text5=document.getElementById("text5");

aff.onclick=function () {
    if(userpassword.value==""){
        document.getElementsByClassName("error")[0].innerHTML="密码不能为空！";
        return false;
    }if(text5.value==""){
        document.getElementsByClassName("error")[1].innerHTML="确认密码不能为空！";
        return false;
    }if(text5.value != userpassword.value){
        document.getElementsByClassName("error")[1].innerHTML="两次密码须一致";
        return false;
    }else{
        document.getElementsByClassName("error")[2].innerHTML="输入成功";
         return true;
         }
}

  //判断用户名是否已存在
$(function(){
    $("#user_name").blur(function(){
        var user_name=$(this).val();
        if(user_name==""){
            $("#remind").html("用户名不能为空！");
        }else{
            $.Ajax({
                url:"",
                type:"post",
                data:"username",
                dataType:"text",
                async:false,
                success:function(result){
                    $("#remind").html(result);
                }
            })
        }
    })
})






    // //判断输入框信息是否正确
    //
    // $("#user_code").blur(function(){
    //     var user_code=$("#user_code").val();
    //     $.ajax({
    //         url:"contrastCode",
    //         data:"user_code="+user_code,
    //         type:"POST",
    //         dataType:"text",
    //         success:function(data){
    //             data=parseInt(data,10);
    //             if (data == 1) {
    //                 $("#error").html("<font color='#339933'>√ 验证码正确，请继续</font>");
    //                 $("correct1").onclick=tab();
    //             } else if (data == 0){
    //                 $("#error").html("<font color='red'>× 验证码有误，请核实后重新填写</font>");
    //             }
    //         }
    //     });
    // });

// //获取用户名和密码给后台
//     var user_name=$("text3").val();
//     var pwd=$("text4").val();
//
//     var postData={
//         "user_name":"user_name",
//         "pwd":"pwd",
//     }
//     $.ajax({
//         async : false,
//         type : 'POST',
//         url:"",
//         data:postData,
//         dataType : "json",
//         error : function() {
//             alert('请求失败 ');
//         },
//         success : function(data) {
//           $("correct2").onclick=tab2();
//         }
//
//     })









