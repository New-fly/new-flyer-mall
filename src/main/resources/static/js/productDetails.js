window.onload = function() {
	document.getElementById("add").onclick = function() {
		var num = document.getElementById("number").value;
		num *= 1;
		num = num + 1;
		document.getElementById("number").value = num;
	}
	document.getElementById("subtract").onclick = function() {
		var num = document.getElementById("number").value;
		num *= 1;
		num = num - 1;
		if(num < 1) {
			num = 1
		}
		document.getElementById("number").value = num;
	};
	var num = 0;
	long=document.getElementsByClassName("berelated-goods-long")[0].clientWidth;
	document.getElementsByClassName("left")[0].onclick=function(){
		num=num+220;
		if(num>0){num=0;document.getElementsByClassName("left")[0].style.cursor="not-allowed";}
		document.getElementsByClassName("berelated-goods-long")[0].style.left = num + "px";
	};
	document.getElementsByClassName("right")[0].onclick=function(){
		num=num-220;
		if(num<(-1)*long){num=(-1)*long;document.getElementsByClassName("right")[0].style.cursor="not-allowed";}
		document.getElementsByClassName("berelated-goods-long")[0].style.left = num + "px";
	};
	var productId=document.getElementsByClassName("productId")[0].value;
    var userId=document.getElementsByClassName("userId")[0].value;
    var shoppingCart_count=document.getElementsByClassName("shoppingCart_count")[0].value;
	document.getElementsByClassName("shoppingcart")[0].onclick=function(){
	    console.log(userId);
        console.log(productId);
        $.ajax({
            url:"/ProductListController/addShoppingProduct",
            dataType:"JSON",
            data:{
                "productId":productId,
                "userId":userId,
                "shoppingCart_count":shoppingCart_count
            },
            type:"POST",
        });
    }
};