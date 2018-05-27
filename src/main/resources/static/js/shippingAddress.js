window.onload=function(){
	var address_allone=document.getElementsByClassName("address_allone");
	//修改收货地址
	for(let i = 0 ; i < address_allone.length ; i++ ){
		document.getElementsByClassName("operate_change")[i].onclick=function(){
			document.getElementsByClassName("black")[0].style.width="100%";
			document.getElementsByClassName("black")[0].style.height="100%";
			document.getElementsByClassName("black")[0].style.top="0";
			document.getElementsByClassName("black")[0].style.left="0";
			document.getElementsByClassName("definite")[0].innerHTML="修改";
			var name=document.getElementsByClassName("consignee")[i].innerHTML;
			document.getElementsByClassName("revise_name")[0].value=name;
			var mobile=document.getElementsByClassName("mobile")[i].innerHTML;
			document.getElementsByClassName("revise_phone")[0].value=mobile;
			var residence=document.getElementsByClassName("residence")[i].innerHTML;
			document.getElementsByClassName("revise_address")[0].value=residence;
            var id=document.getElementsByClassName("id")[i].innerHTML;
            document.getElementsByClassName("revise_id")[0].value=id;
            var is=document.getElementsByClassName("is")[i].innerHTML;
            document.getElementsByClassName("revise_is")[0].value=is;
            var username=document.getElementsByClassName("username")[0].innerHTML;
            document.getElementsByClassName("revise_username")[0].value=username;
            //点击修改收货地址内容
            document.getElementsByClassName("definite")[0].onclick=function () {
                var revise_name=document.getElementsByClassName("revise_name")[0].value;
                var revise_phone=document.getElementsByClassName("revise_phone")[0].value;
                var revise_address=document.getElementsByClassName("revise_address")[0].value;
                var revise_id=document.getElementsByClassName("revise_id")[0].value;
                var revise_is=document.getElementsByClassName("revise_is")[0].value;
                var revise_user_name=document.getElementsByClassName("revise_username")[0].value;
                $.ajax({
                    url:"/UserAddressController/shippingAddresslists",
                    type:'POST',
                    data:{
                        "addressId":revise_id,
                        "address_shipping":revise_address,
                        "address_name":revise_name,
                        "address_phone":revise_phone,
                        "address_is_default":revise_is,
                        "user_name":revise_user_name
                    },
                    dataType:"text",
                    success:function(){
                        document.getElementsByClassName("black")[0].style.width="0";
                        document.getElementsByClassName("black")[0].style.height="0";
                        document.getElementsByClassName("black")[0].style.top="50%";
                        document.getElementsByClassName("black")[0].style.left="50%";
                        document.getElementsByClassName("consignee")[i].innerHTML=revise_name;
                        document.getElementsByClassName("mobile")[i].innerHTML=revise_phone;
                        document.getElementsByClassName("residence")[i].innerHTML=revise_address;
                        document.getElementsByClassName("prompt")[0].innerHTML="修改成功";
                        document.getElementsByClassName("prompt")[0].style.display="block";
                        setTimeout(function(){
                            document.getElementsByClassName("prompt")[0].style.display="none";
                        },1000)
                    },
                    error:function(){
                        alert("请求失败")
                    }
                });
            };
		};
	}
	//取消关闭弹窗
	document.getElementsByClassName("cancel")[0].onclick=function(){
		document.getElementsByClassName("black")[0].style.width="0";
		document.getElementsByClassName("black")[0].style.height="0";
		document.getElementsByClassName("black")[0].style.top="50%";
		document.getElementsByClassName("black")[0].style.left="50%";
	};
	//差号关闭弹窗
	document.getElementsByClassName("close")[0].onclick=function(){
		document.getElementsByClassName("black")[0].style.width="0";
		document.getElementsByClassName("black")[0].style.height="0";
		document.getElementsByClassName("black")[0].style.top="50%";
		document.getElementsByClassName("black")[0].style.left="50%";
	};
	//添加收货地址
	document.getElementsByClassName("add_address")[0].onclick=function() {
        document.getElementsByClassName("black")[0].style.width = "100%";
        document.getElementsByClassName("black")[0].style.height = "100%";
        document.getElementsByClassName("black")[0].style.top = "0";
        document.getElementsByClassName("black")[0].style.left = "0";
        document.getElementsByClassName("definite")[0].innerHTML = "添加";
        document.getElementsByClassName("revise_name")[0].value = "";
        document.getElementsByClassName("revise_phone")[0].value = "";
        document.getElementsByClassName("revise_address")[0].value = "";
        document.getElementsByClassName("revise_id")[0].value = "";
        document.getElementsByClassName("revise_is")[0].value = "";
        document.getElementsByClassName("revise_username")[0].value = "";

        document.getElementsByClassName("definite")[0].onclick=function(){
            var add_name=document.getElementsByClassName("revise_name")[0].value;
            var add_phone=document.getElementsByClassName("revise_phone")[0].value;
            var add_address=document.getElementsByClassName("revise_address")[0].value;
            var username=document.getElementsByClassName("username")[0].innerHTML;
            $.ajax({
                url:"/UserAddressController/addShippingAddress",
                type:'POST',
                data:{
                    "address_shipping":add_address,
                    "address_name":add_name,
                    "address_phone":add_phone,
                    "user_name":username,
                },
                dataType:"text",
                success:function(){
                    document.getElementsByClassName("black")[0].style.width="0";
                    document.getElementsByClassName("black")[0].style.height="0";
                    document.getElementsByClassName("black")[0].style.top="50%";
                    document.getElementsByClassName("black")[0].style.left="50%";
                    document.getElementsByClassName("prompt")[0].innerHTML="添加成功";
                    document.getElementsByClassName("prompt")[0].style.display="block";
                    setTimeout(function(){
                        document.getElementsByClassName("prompt")[0].style.display="none";
                    },1000)
                },
                error:function(){
                    alert("请求失败")
                }
            });
        }
    };
	//删除收货地址
    var length=document.getElementsByClassName("address_allone");
    for(let i=0;i<length.length;i++){
        document.getElementsByClassName("operrate_deleat")[i].onclick=function(){
            var deleat_address=document.getElementsByClassName("id")[i].innerHTML;
            var username=document.getElementsByClassName("revise_username")[0].value=username;
            $.ajax({
                url:"/UserAddressController/deleteShippingAddress",
                type:'POST',
                data:{
                    "addressId":deleat_address,
                    "user_name":username
                },
                dataType:"text",
                success:function(){
                    var parent=document.getElementsByClassName("all_address")[0];
                    var child=document.getElementsByClassName("address_allone")[i];
                    parent.removeChild(child);
                    document.getElementsByClassName("black")[0].style.width="0";
                    document.getElementsByClassName("black")[0].style.height="0";
                    document.getElementsByClassName("black")[0].style.top="50%";
                    document.getElementsByClassName("black")[0].style.left="50%";
                    document.getElementsByClassName("prompt")[0].innerHTML="删除成功";
                    document.getElementsByClassName("prompt")[0].style.display="block";
                    setTimeout(function(){
                        document.getElementsByClassName("prompt")[0].style.display="none";
                    },1000)
                },
                error:function(){
                    alert("请求失败")
                }
            });
        }
    }
};
