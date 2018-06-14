$(window).load(function(){
    //1.修改用户名请求
    var span1=document.getElementsByClassName("span1")[0];
    $(".modify0").click(function (){
        if(user_test()==true){
        var user_name=document.getElementsByClassName("new_name")[0].value;
        $.ajax({
            url:"/UserManagementController/changeInformation",
            type:'POST',
            data:{
                /*"userId":userId,*/
                "user_name":user_name
            },
            dataType:"text",
            success:function(){
                document.getElementsByClassName("black")[0].style.width="0";
                document.getElementsByClassName("black")[0].style.height="0";
                document.getElementsByClassName("black")[0].style.top="50%";
                document.getElementsByClassName("black")[0].style.left="50%";
                document.getElementsByClassName("prompt")[0].innerHTML="修改成功";
                document.getElementsByClassName("prompt")[0].style.display="block";
                span1.innerHTML=user_name;
                setTimeout(function(){
                    document.getElementsByClassName("prompt")[0].style.display="none";
                },1000)
            },
            error:function () {
                alert("请求失败");
            }
        });
        }
    });
   // 2.修改密码的请求
   //  var change_password=document.getElementsByClassName("span2")[0];
    $(".modify1").click(function (){
        if(judge()==true) {
            var prepassword = document.getElementById("prepassword").value;
            var repassword = document.getElementById("repassword").value;
            $.ajax({
                url: "/UserManagementController/checkPassword",
                type: 'POST',
                data: {
                    "user_password": prepassword,
                    "user_rePassword": repassword
                },
                dataType: "text",
                success: function (data) {
                    document.getElementsByClassName("black")[1].style.width = "0";
                    document.getElementsByClassName("black")[1].style.height = "0";
                    document.getElementsByClassName("black")[1].style.top = "50%";
                    document.getElementsByClassName("black")[1].style.left = "50%";
                    document.getElementsByClassName("prompt")[0].innerHTML = "修改成功";
                    document.getElementsByClassName("prompt")[0].style.display = "block";
                    // change_password.innerHTML=repassword;
                    setTimeout(function () {
                        document.getElementsByClassName("prompt")[0].style.display = "none";
                    }, 1000)

                },
                error: function () {
                    alert("原密码输入错误");
                }
            });
        }
    });
    // //3.修改邮箱的请求
    // var change_mail=document.getElementsByClassName("span3")[0];
    // $(".modify2").click(function (){
    //     if(test()==true) {
    //         var new_mail = document.getElementsByClassName("new_mail")[0].value;
    //         $.ajax({
    //             url: "/UserManagementController/changeEmail",
    //             type: 'POST',
    //             data: {
    //                 "user_mail": new_mail
    //             },
    //             dataType: "text",
    //             success: function (data) {
    //                 document.getElementsByClassName("black")[2].style.width = "0";
    //                 document.getElementsByClassName("black")[2].style.height = "0";
    //                 document.getElementsByClassName("black")[2].style.top = "50%";
    //                 document.getElementsByClassName("black")[2].style.left = "50%";
    //                 document.getElementsByClassName("prompt")[0].innerHTML = "修改成功";
    //                 document.getElementsByClassName("prompt")[0].style.display = "block";
    //                 change_mail.innerHTML = new_mail;
    //                 setTimeout(function () {
    //                     document.getElementsByClassName("prompt")[0].style.display = "none";
    //                 }, 1000)
    //
    //             },
    //
    //             error: function () {
    //                 alert("邮箱格式不正确");
    //             }
    //         });
    //     }
    // })
//发送验证码
    $(document).ready(function() {
        // if(test()==true) {
            document.getElementsByClassName("change_mail")[0].onclick=function(){
            //获取原邮箱号
            var pre_mail=document.getElementById("premail").innerHTML;
            console.log(pre_mail);
            $.ajax({
                type: 'POST',
                url: '/UserManagementController/sendChangeEmail',
                data:{
                    "user_mail":pre_mail
                },
                error: function ( ) {
                    alert("错误")
                },
                success: function (data) {
                    document.getElementsByClassName("black_oldmail")[0].style.width="100%";
                    document.getElementsByClassName("black_oldmail")[0].style.height="100%";
                    document.getElementsByClassName("black_oldmail")[0].style.top="0";
                    document.getElementsByClassName("black_oldmail")[0].style.left="0";
                }
            });
        };
    });
// //4.修改头像的请求
// //     var user_img2=document.getElementsByClassName("user_img2")[0];
//     var user_name=document.getElementById("user_name");
//     $(".modify3").click(function (){
//             var new_img = document.getElementById("file").value;
//             $.ajax({
//                 url: "/UserManagementController/ModifyAvatar",
//                 type: 'POST',
//                 data: {
//                     "user_avatar":new_img,
//                     "user_name":user_name
//                 },
//                 dataType: "text",
//                 success: function (data) {
//                     alert("111");
//                     document.getElementsByClassName("black")[2].style.width = "0";
//                     document.getElementsByClassName("black")[2].style.height = "0";
//                     document.getElementsByClassName("black")[2].style.top = "50%";
//                     document.getElementsByClassName("black")[2].style.left = "50%";
//                     document.getElementsByClassName("prompt")[0].innerHTML = "修改成功";
//                     document.getElementsByClassName("prompt")[0].style.display = "block";
//                     setTimeout(function () {
//                         document.getElementsByClassName("prompt")[0].style.display = "none";
//                     }, 1000)
//                 },
//                 error: function () {
//                     alert("no");
//                 }
//             });
    });




