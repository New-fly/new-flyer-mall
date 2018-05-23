$(document).ready(function(){
	$(".list-2").click(function(){
		$(".list-6").slideUp();
		$(".list-7").slideUp();
		$(".list-5").slideToggle();
	})
	$(".list-3").click(function(){
		$(".list-5").slideUp();
		$(".list-7").slideUp();
		$(".list-6").slideToggle();
	})
	$(".list-4").click(function(){
		$(".list-5").slideUp();
		$(".list-6").slideUp();
		$(".list-7").slideToggle();
	})
	var boo1 = true;
    var user = document.getElementById("data").value;
    if(user=="超级管理员"){
        document.getElementById('list-3').onclick = function(){
            document.getElementById("jiantou3").style.transform = 'rotate(0deg)';
            document.getElementById("jiantou1").style.transform = 'rotate(0deg)';
		}
        document.getElementById('list-4').onclick = function(){
            document.getElementById("jiantou1").style.transform = 'rotate(0deg)';
        }
        document.getElementById('list-2').onclick = function(){
            if(boo1){
                document.getElementById('jiantou1').style.transform = 'rotate(90deg)';
                document.getElementById("jiantou2").style.transform = 'rotate(0deg)';
                document.getElementById("jiantou3").style.transform = 'rotate(0deg)';
                boo2=true;
                boo3=true;
                boo1=!boo1;
            }else{
                document.getElementById('jiantou1').style.transform = 'rotate(0deg)';
                boo1=!boo1;
            }
        }
    }
	var boo2 = true;
	document.getElementById('list-3').onclick = function(){
			if(boo2){
			document.getElementById('jiantou2').style.transform = 'rotate(90deg)';

			boo1=true;
			boo3=true;
			boo2 = !boo2;
			}else{
			document.getElementById('jiantou2').style.transform = 'rotate(0deg)';
			boo2 = !boo2;
			}
		}
	var boo3 = true;
	document.getElementById('list-4').onclick = function(){
			if(boo3){
			document.getElementById('jiantou3').style.transform = 'rotate(90deg)';
			document.getElementById("jiantou2").style.transform = 'rotate(0deg)';
			boo2=true;
			boo1=true;
			boo3 = !boo3;
			}else{
			document.getElementById('jiantou3').style.transform = 'rotate(0deg)';
			boo3 = !boo3;
			}
		}
    if(user=="超级管理员"){
        $("#src1").click(function(){
            $(".frame").attr("src","/SearchShieldController/toProductSensitive");
        })
        $("#src2").click(function(){
            $(".frame").attr("src","/AdminProductController/productCategoryList")
        })
        $("#src3").click(function(){
            $(".frame").attr("src","/AdminUserController/adminUserList")
        })
    }
	$("#src4").click(function(){
		$(".frame").attr("src","/AdminProductController/adminProductList")
	})
	$("#src5").click(function(){
		$(".frame").attr("src","/OrderController/allProductOrdersLists")
	})
	$("#src6").click(function(){
		$(".frame").attr("src","/AdminActivityController/activityProducts")
	})
	$("#src7").click(function(){
		$(".frame").attr("src","/AdminActivityController/activity")
	})


    //下拉框选中切换
    if(user=="超级管理员"){
        $("#sensitive").click(function(){
            $(".frame").attr("src","/SearchShieldController/toProductSensitive")
        })
        $("#Category").click(function(){
            $(".frame").attr("src","/AdminProductController/productCategoryList")
        })
        $("#user").click(function(){
            $(".frame").attr("src","/AdminUserController/adminUserList")
        })
    }
    $("#product").click(function(){
        $(".frame").attr("src","/AdminProductController/adminProductList")
    })
    $("#order").click(function(){
        $(".frame").attr("src","/OrderController/allProductOrdersLists")
    })
    $("#src6").click(function(){
        $(".frame").attr("src","/AdminActivityController/activityProducts")
    })
    $("#src7").click(function(){
        $(".frame").attr("src","/AdminActivityController/activity")
    });
$('#one').click(function () {
	$('.frame').attr("src","/AdminProductController/adminProductList");
})
    $('#two').click(function () {
        $('.frame').attr("src","/AdminUserController/adminUserList");
    })



    // $(".list-2 >.list-1 ").find(".list-1").click(function(){
    //         $(".list-2").find(".list-1").removeClass("active");
    //         $(this).parent(".list-1").addClass("active");
    //     });


})

