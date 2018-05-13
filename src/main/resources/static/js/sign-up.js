
var email= document.getElementById("user_mail");
function test()
{

    //对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!myreg.test(email.value))
    {
        document.getElementsByClassName("error")[0].innerHTML="请输入有效的E_mail！";
        return false;
    }else{
        document.getElementsByClassName("error")[0].innerHTML="";
        return true;
    }
}

//点击获取验证码
var countdown=60;
function settime(obj) {
    if (countdown == 0) {
        obj.removeAttribute("disabled");
        obj.value="获取验证码";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.value="重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function() {
            settime(obj) }
        ,1000)
}

//对用户名的验证
var user_name=document.getElementById("user_name");

// function test2() {
//     //用户名正则，4到16位（字母，数字，下划线，减号）
//     var uPattern = /^[a-zA-Z0-9_-]{4,16}$/;
//     if(!uPattern.test(user_name.value)){
//         //alert("请输入有效的用户名！");
//         document.getElementsByClassName("error")[0].innerHTML="请输入有效的用户名";
//         return false;
//     }else{
//         document.getElementsByClassName("error")[0].innerHTML="";
//     }
// }

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

//点击验证码按钮，获取邮箱号
    $(document).ready(function() {
        //点击验证码按钮，获取邮箱号
        $("#btn").click(function () {
            var user_mail = $("#user_mail").val();
            var postData = {
                "user_mail": user_mail
            }
            // postData = JSON.stringify(postData);
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: '/sendEmail',
                data: postData,
                error: function () {
                    alert("123")
                },
                success: function (data) {
                    alert("成功")
                }
            });
        });
    })
    //判断用户名是否已存在
    // $(function(){
    //     $("#user_name").blur(function(){
    //         var user_name=$(this).val();
    //         var postData = {
    //             "user_name": user_name
    //         }
    //         if(user_name==""){
    //             $("#remind").html("用户名不能为空！");
    //         }else{
    //             $.ajax({
    //                 async: false,
    //                 cache: false,
    //                 type: 'POST',
    //                 url: '',
    //                 data: postData,
    //                 error: function () {
    //                     alert("失败")
    //                 },
    //                 success: function (data) {
    //                     alert("123456")
    //                 }
    //             });
    //         }
    //     })
    // })

})
//判断输入框信息是否正确

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
//
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









