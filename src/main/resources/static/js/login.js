$(".divbtn").click(function () {
    var zhanghao = $('.ipt1').val();
    var mima = $('.ipt2').val();
    $.ajax({
        url: "/toLogin", //请求的url地址
        dataType: "json", //返回格式为json
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性
        data: {
            "user_name": zhanghao,
            "user_password":mima
        }, //参数值
        type: "post", //请求方式
        success: function(userlist) {
            alert("成功")
        },
        error: function() {
            alert("失败")
        }
    });
});
if (window != top){
    top.location.href = location.href;
}
//输入错误提示
// var ipt1=document.getElementsByName("user_name")[0];
// var ipt2=document.getElementsByName("user_password")[0];
// if(ipt1){
//     document.getElementsByClassName("error")[0].innerHTML="";
// }else{
//     document.getElementsByClassName("error")[0].innerHTML="没有此用户哦";
// }
// if(ipt2){
//     document.getElementsByClassName("error")[1].innerHTML="";
// }else{
//     document.getElementsByClassName("error")[1].innerHTML="密码有误";
// }