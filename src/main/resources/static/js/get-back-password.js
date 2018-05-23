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
//验证码不能为空
user_code=$("#user_code");
console.log(user_code);
$(document).ready(function(){
    console.log("111");
    $("#aff").click(function(){
        if(user_code.val()==""|| user_code.val()==null){
            $(".error")[1].innerHTML="验证码不能为空！";
            }
    })
//点击验证码按钮，获取邮箱号
    // if(test()==true) {
    //点击验证码按钮，获取邮箱号
    $("#btn").click(function () {
        var user_mail = $("#user_mail").val();
        console.log(user_mail);
        var postData = {
            "user_mail": user_mail
        }
        // postData = JSON.stringify(postData);
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: '/UserManagementController/sendRetrievePassword',
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
})