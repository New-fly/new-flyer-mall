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
    var shoppingaddress=document.getElementsByClassName("shoppingaddress");
    for(let i=0;i<shoppingaddress.length;i++){
        shoppingaddress[i].onclick=function(){
            for(let j=0;j<shoppingaddress.length-1;j++){
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
        }
    }
	var count=document.getElementsByClassName("count");
	var  money = "0";
	money = money*1;
	for(i=0;i<count.length;i++){
		money=count[i].value*1+money;
	}
	document.getElementsByClassName("total_amount")[0].value=money;

}
