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
    },6000);
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
        var newDate= new Date(document.getElementsByClassName("activity_time")[0].value);
        var newTime = newDate.getTime();
        var second = Math.floor((newTime - oldTime)/1000);
        var day = Math.floor(second/86400);
        second = second % 86400;
        var hour = Math.floor(second/3600);
        second %= 3600;
        var minute = Math.floor(second/60);
        second %= 60;
        document.getElementById("time1").innerHTML=tow(hour);
        document.getElementById("time2").innerHTML=tow(minute);
        document.getElementById("time3").innerHTML=tow(second);
        document.getElementById("time4").innerHTML=tow(day);
    }
    getDate();
    setInterval(getDate, 1000);
	//控制抢购翻页
    var length=document.getElementsByClassName("all-rob-long")[0].offsetWidth;
	var num = 0;
	document.getElementById("button-next").onclick=function(){
		num=num-200;
		if(num<(-1)*length+1000){num=(-1)*length+1000;}
		document.getElementById("all-rob-long").style.left = num + "px";
	};
	document.getElementById("button-last").onclick=function(){
		num=num+200;
		if(num>0){num=0}
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
    };
    function color(){
        var allfootnav=document.getElementsByClassName("footnav")
        for(let i=0;i<allfootnav.length;i++){
            allfootnav[i].style.backgroundColor="white";
        }
    }
    document.getElementsByClassName("totop")[0].onclick=Totop;
    function Totop() {
        //把内容滚动指定的像素数（第一个参数是向右滚动的像素数，第二个参数是向下滚动的像素数）
        window.scrollBy(0, -50);
        //延时递归调用，模拟滚动向上效果
        var scrolldelay = setTimeout(Totop, 10);
        //获取scrollTop值，声明了DTD的标准网页取document.documentElement.scrollTop，否则取document.body.scrollTop；因为二者只有一个会生效，另一个就恒为0，所以取和值可以得到网页的真正的scrollTop值
        var sTop = document.documentElement.scrollTop + document.body.scrollTop;
        //判断当页面到达顶部，取消延时代码（否则页面滚动到顶部会无法再向下正常浏览页面）
        if (sTop == 0) clearTimeout(scrolldelay);
    }
};