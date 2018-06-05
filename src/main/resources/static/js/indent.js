window.onload=function(){
    var num=0;
	document.getElementById("all").onclick=function(){
		if(num%2==0){
			document.getElementsByClassName("address")[0].style.height="auto";
			document.getElementById("all").innerHTML="收起";
			num+=1;
		}else{
			document.getElementsByClassName("address")[0].style.height=130+"px";
			document.getElementById("all").innerHTML="查看所有收货地址";
			num+=1;
		}
	};
	var address_name=" ";
	var address_phone=" ";
	var address_shipping=" ";
    var shoppingaddress=document.getElementsByClassName("shoppingaddress");
    for(let i=0;i<shoppingaddress.length;i++){
        shoppingaddress[i].onclick=function(){
            for(let j=0;j<shoppingaddress.length;j++){
                this.parentNode.children[j].style.border="dashed 3px gray";
                this.parentNode.children[j].style.backgroundColor="white";
                this.parentNode.children[j].children[0].style.backgroundColor="white";
                this.parentNode.children[j].children[2].style.backgroundColor="white";
                this.parentNode.children[j].children[3].style.backgroundColor="white";
            }
            this.style.border="dashed 3px red";
            this.style.backgroundColor="pink";
            this.children[0].style.backgroundColor="pink";
            this.children[2].style.backgroundColor="pink";
            this.children[3].style.backgroundColor="pink";
            //将下面的信息改为选择内容
            address_name=document.getElementsByClassName("address_name")[i].value;
            address_phone=document.getElementsByClassName("address_phone")[i].value;
            address_shipping=document.getElementsByClassName("address_shipping")[i].value;
            document.getElementsByClassName("addressname")[0].value=address_name;
            document.getElementsByClassName("addressphone")[0].value=address_phone;
            document.getElementsByClassName("addressshipping")[0].value=address_shipping;
        }
    }
	var count=document.getElementsByClassName("count");
	var  money = "0";
	money = money*1;
	for(i=0;i<count.length;i++){
		money=count[i].value*1+money;
	}
	document.getElementsByClassName("total_amount")[0].value=money;
	var allpid=" ";
	var length=document.getElementsByClassName("pid");
	for(let K=0;K<length.length;K++){
	    allpid=allpid+length[K].value+",";
	}
	document.getElementsByClassName("productId")[0].value=allpid;
    var allcount=" ";
    var length2=document.getElementsByClassName("count1");
    for(let j=0;j<length2.length;j++){
        allcount=allcount+length2[j].value+",";
    }
    document.getElementsByClassName("shoppingCount")[0].value=allcount;
    var top=document.getElementById("top").value;
    var rebate=document.getElementById("rebate").value;
    var bottom=top*rebate/10;
    document.getElementById("bottom").value=bottom;
};
