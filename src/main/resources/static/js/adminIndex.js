$(document).ready(function(){
    //设置高亮
    $(".addColor").on('click',function () {
        $(".addColor").css("backgroundColor","#282B33");
        $(this).css("backgroundColor","#009688");
    })
    //设置滑动
    $(".list_one").click(function(){
        $(this).siblings(".list_two").slideToggle("1s");
        $(this).parent().siblings().children(".list_two").slideUp("1s");
    });

//设置箭头下滑
    var flag1=true,flag2=true;
    $("#one").click(function(){
        if(flag1==true) {
            $("#jiantou1").css("transform", "rotate(90deg)");
            flag1=false;
            flag2=true;
        }else{
            $("#jiantou1").css("transform", "rotate(0deg)");
            flag1=true;

        }
    });
    $("#two").click(function(){
        if(flag2==true) {
            $("#jiantou2").css("transform", "rotate(90deg)");
            flag2=false;
            flag1=true;
        }else{
            $("#jiantou2").css("transform", "rotate(0deg)");
            flag2=true;

        }
    });

//点击切换页面
    $("#src5").click(function(){
        $(".frame").attr("src","/SearchShieldController/toProductSensitive");
    })
    $("#src7").click(function(){
        $(".frame").attr("src","/AdminProductController/productCategoryList")
    })
    $("#src6").click(function(){
        $(".frame").attr("src","/AdminUserController/adminUserList")
    })
    $("#src1").click(function(){
        $(".frame").attr("src","/AdminProductController/adminProductList")
    })
    $("#src2").click(function(){
        $(".frame").attr("src","/OrderController/allProductOrdersLists")
    })
    $("#src3").click(function(){
        $(".frame").attr("src","/AdminActivityController/activityProducts")
    })
    $("#src4").click(function(){
        $(".frame").attr("src","/AdminActivityController/activity")
    })




});
