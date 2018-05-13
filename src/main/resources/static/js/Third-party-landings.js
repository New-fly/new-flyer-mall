var email= document.getElementById("email");
var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
function test() {

    //对电子邮件的验证

    if (email.value != "") {
        if (myreg.test(email.value)) {
            document.getElementsByClassName("error")[0].innerHTML = "";
            return true;
        } else {
            document.getElementsByClassName("error")[0].innerHTML = "请输入有效的E_mail！";
            return false;
        }
    }
}
var password=document.getElementById("password");
var pPattern=/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g;
function test2(){


    if (!pPattern.test(password.value)){
        document.getElementsByClassName("error")[1].innerHTML="密码必须由6-16个英文字母和数字的字符串组成！";
        return false;
    }else{
        document.getElementsByClassName("error")[1].innerHTML="";
    }

}
var password2=document.getElementById("password2");
var pPattern=/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g;
function test3(){
    ////密码强度正则，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
    // var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/;

    if (!pPattern.test(password2.value)){
        document.getElementsByClassName("error")[3].innerHTML="密码必须由6-16个英文字母和数字的字符串组成！";
        return false;
    }else{
        document.getElementsByClassName("error")[3].innerHTML="";
    }

}
var username = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
var user_name=document.getElementById('user_name');
function test4(){

    if(user_name.value!=""){
        if(username.test(user_name.value)){
            document.getElementsByClassName("error")[2].innerHTML="";
            return true;
        }else{
            document.getElementsByClassName("error")[2].innerHTML="请输入有效的名字！";
            return false;

        }
    }

}
var submit=document.getElementById('submit');
submit.onclick=function () {
    if(email.value!=''&& password.value!=''){
        document.getElementsByClassName("error")[1].innerHTML="";
        return true;
    }else{
        document.getElementsByClassName("error")[1].innerHTML="输入框不能为空！";
        return false;
    }
}
var bangding=document.getElementById('bangding');
bangding.onclick=function () {
    if(user_name.value!=''&& password2.value!=''){
        document.getElementsByClassName("error")[3].innerHTML="";
        return true;
    }else{
        document.getElementsByClassName("error")[3].innerHTML="输入框不能为空！";
        return false;
    }

}