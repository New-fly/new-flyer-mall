//对密码的验证
var password=document.getElementById("password");
function test(){
    ////密码强度正则，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
    // var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/;
    var pPattern=/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g;
    if (!pPattern.test(password.value)){
        document.getElementsByClassName("error")[0].innerHTML="密码必须由6-16个英文字母和数字的字符串组成！";
        return false;
    }else{
        document.getElementsByClassName("error")[0].innerHTML="";
    }

}

//密码和确认密码全部输入才能进入
var password=document.getElementById("password");
var passwordagain=document.getElementById("passwordagain");
var aff=document.getElementById("aff");

aff.onclick=function () {
    if(password.value==""){
        document.getElementsByClassName("error")[0].innerHTML="密码不能为空！";
        return false;
    }if(passwordagain.value==""){
        document.getElementsByClassName("error")[1].innerHTML="确认密码不能为空！";
        return false;
    }if(password.value != passwordagain.value){
        document.getElementsByClassName("error")[1].innerHTML="两次密码须一致";
        return false;
    }else{
        document.getElementsByClassName("error")[2].innerHTML="输入成功";
        return true;
    }
}
