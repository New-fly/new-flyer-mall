window.onload = function(){
    // document.getElementsByClassName("houxuan")[0].onclick=function(){
    //     var word = document.getElementsByClassName("houxuan")[0].innerHTML;
    //     document.getElementsByClassName("int-1").value=word;
    // };
	//轮播翻页效果
    var n = 0;
    var abc=["/images/01.jpg","/images/03.jpg"];
    setInterval(function(){n++;var deg = 180*(n%2);
        document.getElementsByClassName("classify-right")[0].style.transform="rotateX("+deg+"deg)";
        setTimeout(function(){document.getElementsByClassName("classify-right")[0].style.backgroundImage="url("+abc[deg/180]+")";},200)
    },4000);
    //限时抢购倒计时
    function tow(n) {
        return n >= 0 && n < 10 ? '0' + n : '' + n;
    }
    function getDate(){
        var oDate = new Date();
        var oldTime = oDate.getTime();
        var newDate = new Date("2018/5/18 0:00:00");
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
		if(num<-3000){num=-3000}
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
                console.log(data);
                if (data.length > 0) {
                    document.getElementsByClassName("hint")[0].style.display = "block";
                    document.getElementsByClassName("hint")[0].innerHTML = "";
                    for (let i = 0; i < data.length; i++) {
                        let oDiv = document.createElement('div');
                        oDiv.innerHTML = data[i];
                        document.getElementsByClassName("hint")[0].appendChild(oDiv);
                    };
                $(".hint").children().addClass("houxuan");
                $(".houxuan").on("click",function(){
                     $(".ipt-1").val($(this).html());
                    document.getElementsByClassName("hint")[0].style.display = "none";
                })
                }
                else{
                    document.getElementsByClassName("hint")[0].style.display = "none";
                }
            },
            error: function () {
                alert("请求失败");
            }
        })
    }
};