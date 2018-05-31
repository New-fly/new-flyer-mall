window.onload = function() {
	//实现购物车内数量的增加
    var count = 0;
    var zongjiage = document.getElementsByClassName('zongjiage')[0];
    var olist=document.getElementsByClassName('list')
		for(let i = 0; i < olist.length; i++) {
			document.getElementsByClassName("add")[i].onclick = function() {
				var num = document.getElementsByClassName("number")[i].value;
				num *= 1;
				num = num + 1;
				document.getElementsByClassName("number")[i].value = num;
                var shopcartid=document.getElementsByClassName("aa")[i].value;
                var qqqq=document.getElementsByClassName("qqqq")[i].value;
                qqqq=parseInt(qqqq);
                var renminbi=qqqq*(num);
                $.ajax({
                    url:"/ProductListController/changeShoppingCarCount",
                    data:{
                        "shopping_cart_id":shopcartid,
                        "shopping_cart_count":num,
                    },
                    dataType:"text",
                    success:function(data){
                        document.getElementsByClassName("qian")[i].value=renminbi;
                    },
                    error:function () {
                        alert("请求失败")
                    }
                });
			};
			//实现购物车内数量的减少
			document.getElementsByClassName("subtract")[i].onclick = function() {
				var num = document.getElementsByClassName("number")[i].value;
				num *= 1;
				num = num - 1;
				if(num < 1) {
					num = 1
				}
				document.getElementsByClassName("number")[i].value = num;
                var shopcartid=document.getElementsByClassName("aa")[i].value;
                var shopcartid=document.getElementsByClassName("aa")[i].value;
                var qqqq=document.getElementsByClassName("qqqq")[i].value;
                qqqq=parseInt(qqqq);
                var renminbi=qqqq*(num);
                $.ajax({
                    url:"/ProductListController/changeShoppingCarCount",
                    data:{
                        "shopping_cart_id":shopcartid,
                        "shopping_cart_count":num,
                    },
                    // async:true,
                    // cache:true,
                    // traditional:false,
                    dataType:"text",
                    success:function(data){
                        document.getElementsByClassName("qian")[i].value=renminbi;
                    },
                    error:function () {
                        alert("请求失败")
                    }
                });
			}
		}
		document.getElementById("all").onclick=function(){  
        var all=document.getElementById("all");//获取到点击全选的那个复选框 
        var one=document.getElementsByName('aaa');//获取到复选框的名称
        if(all.checked==true){//因为获得的是数组，所以要循环 为每一个checked赋值
            count = 0;
            for(var i=0;i<olist.length;i++){  
                one[i].checked=true;
                document.getElementsByClassName("single")[i].style.backgroundColor="papayawhip";
                var value = one[i].parentNode.getElementsByClassName("single-text")[0].children[0].value;
                value = parseInt(value);
                count += value;
                zongjiage.value = count;
            }
            $(this).parent().parent().parent().find("input.yanse").css("background-color","papayawhip");
            color();
        }else{  
            for(var j=0;j<olist.length;j++){  
                one[j].checked=false;
                document.getElementsByClassName("single")[j].style.backgroundColor="#FCFCFC";
                var value = one[j].parentNode.getElementsByClassName("single-text")[0].children[0].value;
                value = parseInt(value);
                count -= value;
                zongjiage.value = count;
            }
            $(this).parent().parent().parent().find("input.yanse").css("background-color","#FCFCFC");
            color();
            $(this).parent().find("input").css("background-color","#FCFCFC");
        }  
    }
	var one=document.getElementsByName('aaa');
	for(let k=0;k<olist.length;k++){
	one[k].onchange=function(){
			if(one[k].checked==true){
				this.parentNode.style.backgroundColor="papayawhip";
				var shoppingCartId=this.parentNode.childNodes[1].value;
				var userId=this.parentNode.childNodes[3].value;
				var value = this.parentNode.getElementsByClassName("single-text")[0].children[0].value;
				value = parseInt(value);
				count += value;
				$(this).parent().find("input.yanse").css("background-color","papayawhip");
            }
			else{
				this.parentNode.style.backgroundColor="#FCFCFC";
                var value = this.parentNode.getElementsByClassName("single-text")[0].children[0].value;
                value = parseInt(value);
				count -= value;
                all.checked=false;
                $(this).parent().find("input.yanse").css("background-color","#FCFCFC");
			}
        zongjiage.value = count;
			color();
			fff();
		}
	}
	//控制提交按钮的颜色
	function color(){
        var number=document.getElementsByClassName("zongjiage")[0].value;
        number = parseInt(number);
        if(number==0){
            document.getElementById("jiesuan").style.backgroundColor="gray";
            document.getElementById("jiesuan").style.border="solid 3px gray";
            document.getElementsByClassName("delet")[0].style.backgroundColor="#AAAAAA";
        }else{
            document.getElementById("jiesuan").style.backgroundColor="red";
            document.getElementById("jiesuan").style.border="solid 3px red";
            document.getElementsByClassName("delet")[0].style.backgroundColor="#FF0000";
        }
    }
    //列出所有提交的id
    function fff() {
        var list=document.getElementsByClassName("list");
        var allid="";
        for( let i = 0 ; i< list.length ; i++ ){
            if(one[i].checked==true){
                allid=allid+document.getElementsByClassName("aa")[i].value+",";
            }
        }
        document.getElementsByClassName("produc")[0].value=allid;
    }
    document.getElementsByClassName("delet")[0].onclick=function(){
	    var shopcartid=document.getElementsByClassName("produc")[0].value;
	    var userid=document.getElementsByClassName("userId")[0].value;
        $.ajax({
            url:"/ProductListController/deleteShoppingProducts",
            data:{
                "shoppingCartId":shopcartid,
                "userId":userid
            },
            dataType:"text",
            success:function(){
                $(":checked").parent().remove();
                document.getElementsByClassName("prompt")[0].innerHTML = "删除成功";
                document.getElementsByClassName("prompt")[0].style.display = "block";
                setTimeout(function () {
                    document.getElementsByClassName("prompt")[0].style.display = "none";
                }, 1000)
            },
            error:function () {
                alert("请求失败")
            }
        });
    }
};