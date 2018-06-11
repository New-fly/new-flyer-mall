var email= document.getElementById("user_mail");

function test() {

    //对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (email.value!="") {
        if (myreg.test(email.value)) {
            document.getElementsByClassName("error")[0].innerHTML = "";
            return true;
        } else {
            document.getElementsByClassName("error")[0].innerHTML = "请输入有效的E_mail！";
            return false;
        }
    }
}

//点击获取验证码
var countdown=60;
// if(test() == true) {
    function settime(obj) {
        if (countdown === 0) {
            obj.removeAttribute("disabled");
            obj.value = "获取验证码";
            countdown = 60;
            return;
        } else {
            obj.setAttribute("disabled", true);
            obj.value = "重新发送(" + countdown + ")";
            countdown--;
        }
        setTimeout(function () {
                settime(obj)
            }
            , 1000)
    // }
}

//邮箱和验证码全部输入才能进入
var aff=document.getElementById("aff");
var email=document.getElementById("user_mail");
var usercode=document.getElementById("user_code");

aff.onclick=function (){
    if(email.value==""){
        document.getElementsByClassName("error")[0].innerHTML="邮箱不能为空！";
        return false;
    }if(usercode.value==""){
        document.getElementsByClassName("error")[1].innerHTML="验证码不能为空！";
        return false;
    } else{
        document.getElementsByClassName("error")[2].innerHTML="输入成功";
        return true;
    }
}
//点击验证码按钮，获取邮箱号
$(document).ready(function() {
    // if(test()==true) {
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
    // }
});