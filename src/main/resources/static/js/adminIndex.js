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
	var boo2 = true;
	document.getElementById('list-3').onclick = function(){
			if(boo2){
			document.getElementById('jiantou2').style.transform = 'rotate(90deg)';
			document.getElementById("jiantou1").style.transform = 'rotate(0deg)';
			document.getElementById("jiantou3").style.transform = 'rotate(0deg)';
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
			document.getElementById("jiantou1").style.transform = 'rotate(0deg)';
			boo2=true;
			boo1=true;
			boo3 = !boo3;
			}else{
			document.getElementById('jiantou3').style.transform = 'rotate(0deg)';
			boo3 = !boo3;
			}
		}
	$("#src1").click(function(){
		$(".frame").attr("src","/SearchShieldController/toProductSensitive")
	})
	$("#src2").click(function(){
		$(".frame").attr("src","/AdminProductController/productCategoryList")
	})
	$("#src3").click(function(){
		$(".frame").attr("src","/AdminUserController/adminUserList")
	})
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
		$(".frame").attr("src","..............")
	})
})
