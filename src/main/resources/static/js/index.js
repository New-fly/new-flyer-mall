window.onload = function(){
    var place=0;
    setInterval(function(){
        if(place*(-1)==0||place*(-1)==4000){
            color();
            document.getElementsByClassName('footnav')[0].style.backgroundColor="red";
        }
        if(place*(-1)==1000){
            color();
            document.getElementsByClassName('footnav')[1].style.backgroundColor="red";
        }
        if(place*(-1)==2000){
            color();
            document.getElementsByClassName('footnav')[2].style.backgroundColor="red";
        }
        if(place*(-1)==3000){
            color();
            document.getElementsByClassName('footnav')[3].style.backgroundColor="red";
        }
        },100);
    setInterval(function(){
        place=place-1000;
        if(place<-4000){
            document.getElementsByClassName("classify-right-long")[0].style.transition="0s";
            place=0;
            document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        }
        else{
            document.getElementsByClassName("classify-right-long")[0].style.transition="2s";
            document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        };
    },5000);
    document.getElementsByClassName("last_1")[0].onclick=function(){
        place=place-1000;
        if(place<-4000){
            document.getElementsByClassName("classify-right-long")[0].style.transition="0s";
            place=0; document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        }else {
            document.getElementsByClassName("classify-right-long")[0].style.transition="2s";
            document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        }
    };
    document.getElementsByClassName("next_1")[0].onclick=function(){
        place=place+1000;
        if(place>-1000) {
            document.getElementsByClassName("classify-right-long")[0].style.transition = "0s";
            place = -4000; document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        }else{
            document.getElementsByClassName("classify-right-long")[0].style.transition="2s";
            document.getElementsByClassName("classify-right-long")[0].style.left=place+"px";
        }
    };
    //限时抢购倒计时
    function tow(n) {
        return n >= 0 && n < 10 ? '0' + n : '' + n;
    }
    function getDate(){
        var oDate = new Date();
        var oldTime = oDate.getTime();
        var newDate = new Date("2018/5/28 0:00:00");
        var newTime = newDate.getTime();
        var second = Math.floor((newTime - oldTime)/1000);
        // var day = Math.floor(second/86400);
        second = second % 86400;
        var hour = Math.floor(second/3600);
        second %= 3600;
        var minute = Math.floor(second/60);
        second %= 60;
        document.getElementById("time1").innerHTML=tow(hour);
        document.getElementById("time2").innerHTML=tow(minute);
        document.getElementById("time3").innerHTML=tow(second);
    }
    getDate();
    setInterval(getDate, 1000);
	//控制分页
	var num = 0;
	document.getElementById("button-last").onclick=function(){
		num=num+1000;
		if(num>0){num=0}
		document.getElementById("all-rob-long").style.left = num + "px";
	};
	document.getElementById("button-next").onclick=function(){
		num=num-1000;
		if(num<-1600){num=-1600}
		document.getElementById("all-rob-long").style.left = num + "px";
	};
   document.getElementsByClassName("ipt-1")[0].onblur=function() {
       setTimeout(function(){$('.hint').hide()},1000)
   };
    document.getElementsByClassName("ipt-1")[0].onkeyup=function() {

        var product_keywords = document.getElementsByClassName("ipt-1")[0].value;
        $.ajax({
            url: "/ProductListController/searchWord",
            data: {
                "product_keywords": product_keywords
            },
            dataType: "JSON",
            success: function (data) {
                if (data.length > 0) {
                    document.getElementsByClassName("hint")[0].style.display = "block";
                    document.getElementsByClassName("hint")[0].innerHTML = "";
                    for (let i = 0; i < data.length; i++) {
                        let oDiv = document.createElement('div');
                        oDiv.innerHTML = data[i];
                        document.getElementsByClassName("hint")[0].appendChild(oDiv);
                    }
                $(".hint").children().addClass("houxuan");
                $(".houxuan").on("click",function(){
                     $(".ipt-1").val($(this).html());
                $(".houxuan").css("overflow","hidden")
                    document.getElementsByClassName("hint")[0].style.display = "none";
                })
                }
                else{
                    document.getElementsByClassName("hint")[0].style.display = "none";
                }
            },
            error: function () {
                alert("请求失败！！")
            }
        })
    }
    function color(){
        var allfootnav=document.getElementsByClassName("footnav")
        for(let i=0;i<allfootnav.length;i++){
            allfootnav[i].style.backgroundColor="white";
        }
    }
};