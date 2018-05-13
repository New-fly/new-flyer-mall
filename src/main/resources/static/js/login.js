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

