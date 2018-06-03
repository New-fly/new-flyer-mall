window.onload=function() {
    //生成Pager，当前页码, 总页数, 回调function
    $.fn.pager = function (page, total, callback) {
        var html = '';
        html += '<a class="first" href="javascript:;">首页</a>';
        html += '<a class="first" href="javascript:;">上一页</a>';
        var start = page - 5 < 0 ? 0 : page - 5;
        var end = page + 5 < total ? page + 5 : total;
        for (var i = start; i < end; i++) {
            html += i == page - 1 ? '<span>' + (i + 1) + '</span>' : '<a href="javascript:;">' + (i + 1) + '</a>';
        }
        html += '<a class="first" href="javascript:;">下一页</a>';
        html += '<a class="last" href="javascript:;">末页</a>';
        $(this).html(html).find('a').click(function () {
            var p = $(this).text();
            if (p == '上一页') p = page == 1 ? 1 : page - 1;
            if (p == '下一页') p = page == total ? total : page + 1;
            if (p == '首页') p = 1;
            if (p == '末页') p = total;
            if (p != page) callback(parseInt(p));
        });
    };
    onload = function () {
        //用用回调
        function go(p) {
            $('.pager').pager(p, 10, go);
        }

        $('.pager').pager(1, 10, go);
    };

//返回顶部(缓慢滑动)
    document.getElementsByClassName("back-to-top")[0].onclick=pageScroll;
    function pageScroll() {
        //把内容滚动指定的像素数（第一个参数是向右滚动的像素数，第二个参数是向下滚动的像素数）
        window.scrollBy(0, -500);
        //延时递归调用，模拟滚动向上效果
        scrolldelay = setTimeout(pageScroll, 100);
        //获取scrollTop值，声明了DTD的标准网页取document.documentElement.scrollTop，否则取document.body.scrollTop；因为二者只有一个会生效，另一个就恒为0，所以取和值可以得到网页的真正的scrollTop值
        var sTop = document.documentElement.scrollTop + document.body.scrollTop;
        console.log(sTop);
        //判断当页面到达顶部，取消延时代码（否则页面滚动到顶部会无法再向下正常浏览页面）
        if (sTop == 0) clearTimeout(scrolldelay);
    }
//顶部吸顶盒
    var oDiv = document.getElementsByClassName("top-search")[0];
    var searchLinks=document.getElementsByClassName("search-links")[0];
    var topSearch=document.getElementsByClassName("top-search")[0];
    var searchBox=document.getElementsByClassName("search-box")[0];
    var xflogo=document.getElementsByClassName("xflogo")[0];
    var divT = oDiv.offsetTop;
    window.onscroll = function () {
        var scrollT = document.documentElement.scrollTop - 400;
        if (scrollT > divT) {
            oDiv.style.position = "fixed";
            oDiv.style.top = 0;
            searchLinks.style.display="none";
            topSearch.style.height="65px";
            searchBox.style.marginTop="10px";
            xflogo.style.marginTop="-3px";
        } else {
            oDiv.style.position = "";
            searchLinks.style.display="block";
            topSearch.style.height="113px";
            searchBox.style.marginTop="35px";
            xflogo.style.marginTop="15px";
        }
    };

    //推荐翻页
    var number = 0;
    var length = document.getElementsByClassName("long-hotsell")[0].offsetWidth;
    document.getElementsByClassName("nextone")[0].onclick = function () {
        number -= 298;
        if (number < (-1) * length + 1190) {
            number = (-1) * length + 1190;
        }
        document.getElementsByClassName("long-hotsell")[0].style.left = number + "px";
    };
    document.getElementsByClassName("lastone")[0].onclick = function () {
        number += 298;
        if (number > 0) {
            number = 0;
        }
        document.getElementsByClassName("long-hotsell")[0].style.left = number + "px";
    };
};

