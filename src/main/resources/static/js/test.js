$(window).load(function(){
    //1.修改用户名请求
    var span1=document.getElementsByClassName("span1")[0];
    $(".modify0").click(function (){
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
    })
   // 2.修改密码的请求
    var change_password=document.getElementsByClassName("span2")[0];
    $(".modify1").click(function (){
        var prepassword=document.getElementById("prepassword").value;
        var repassword=document.getElementById("repassword").value;
        $.ajax({
            url:"/UserManagementController/checkPassword",
            type:'POST',
            data:{
                "user_password":prepassword,
                "user_rePassword":repassword
            },
            dataType:"text",
            success:function(data){
                document.getElementsByClassName("black")[1].style.width="0";
                document.getElementsByClassName("black")[1].style.height="0";
                document.getElementsByClassName("black")[1].style.top="50%";
                document.getElementsByClassName("black")[1].style.left="50%";
                document.getElementsByClassName("prompt")[0].innerHTML="修改成功";
                document.getElementsByClassName("prompt")[0].style.display="block";
                change_password.innerHTML=repassword;
                setTimeout(function(){
                    document.getElementsByClassName("prompt")[0].style.display="none";
                },1000)

            },
            error:function () {
                alert("原密码输入错误");
            }
        });
    });
    //3.修改邮箱的请求
    var change_mail=document.getElementsByClassName("span3")[0];
    $(".modify2").click(function (){
        var new_mail=document.getElementsByClassName("new_mail")[0].value;
        $.ajax({
            url:"/UserManagementController/changeEmail",
            type:'POST',
            data:{
                "user_mail":new_mail
            },
            dataType:"text",
            success:function(data){
                document.getElementsByClassName("black")[2].style.width="0";
                document.getElementsByClassName("black")[2].style.height="0";
                document.getElementsByClassName("black")[2].style.top="50%";
                document.getElementsByClassName("black")[2].style.left="50%";
                document.getElementsByClassName("prompt")[0].innerHTML="修改成功";
                document.getElementsByClassName("prompt")[0].style.display="block";
                change_mail.innerHTML=new_mail;
                setTimeout(function(){
                    document.getElementsByClassName("prompt")[0].style.display="none";
                },1000)

            },
            error:function () {
                alert("邮箱格式不正确");
            }
        });
    })
//判断原密码与新密码是否一致
    function judge(){
        var repassword=document.getElementById("repassword").value;
        var again=document.getElementById("again").value;
        if(repassword==again){
            return true;
        }else{
            document.getElementById("error").innerHTML="请保持密码输入一致";
        }

    }





});
