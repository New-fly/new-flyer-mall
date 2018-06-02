window.onload=function(){
        document.getElementById('add').onclick = function(){
		var num = document.getElementById("number").value;
		num *= 1;
		num = num + 1;
		document.getElementById("number").value = num;
	};
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
    var long = document.getElementsByClassName("berelated-goods-long")[0].clientWidth;
    console.log(long);
    document.getElementsByClassName("left")[0].onclick=function(){
		num=num+220;
		if(num>0){num=0;document.getElementsByClassName("left")[0].style.cursor="not-allowed";}
		document.getElementsByClassName("berelated-goods-long")[0].style.left = num + "px";
    };
    document.getElementsByClassName("right")[0].onclick=function(){
		num=num-220;
		if(num<(-1)*long+900){num=(-1)*long+900;document.getElementsByClassName("right")[0].style.cursor="not-allowed";}
		document.getElementsByClassName("berelated-goods-long")[0].style.left = num + "px";
    };
    var productId=document.getElementsByClassName("productId")[0].value;
    var userId=document.getElementsByClassName("userId")[0].value;
    var shoppingCart_count=document.getElementsByClassName("shoppingCart_count")[0].value;
    document.getElementsByClassName("shoppingcart")[0].onclick=function(){
        if(userId.length>0) {
            $.ajax({
                url: "/ProductListController/addShoppingProduct",
                dataType: "TEXT",
                data: {
                    "productId": productId,
                    "userId": userId,
                    "shoppingCart_count": shoppingCart_count
                },
                type: "POST",
                success: function (data) {
                    document.getElementsByClassName("secess")[0].innerHTML = data;
                    document.getElementsByClassName("secess")[0].style.display = "block";
                    setTimeout(function () {
                        document.getElementsByClassName("secess")[0].style.display = "none";
                    }, 1000)
                },
                error: function () {
                    document.getElementsByClassName("secess")[0].innerHTML = "添加失败";
                    document.getElementsByClassName("secess")[0].style.display = "block";
                    setTimeout(function () {
                        document.getElementsByClassName("secess")[0].style.display = "none";
                    }, 1000)
                },
            });
        }else{
            window.location.href="/toLogin";
        }
    };
    var image = document.getElementsByClassName("img-sm");
    for(let i=0;i<image.length;i++){
        image[i].onmousemove=function(){
            var src=this.children[0].src;
            document.getElementsByClassName("img-big")[0].children[0].src=src;
            var asd=document.getElementsByClassName("img-sm");
            for(i=1;i<=asd.length;i++){
                this.parentNode.children[i].style.border="solid 2px white";
            }
            this.style.border="solid 2px red";
        }
    }
};