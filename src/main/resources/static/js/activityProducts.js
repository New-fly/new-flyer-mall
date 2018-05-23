function tow(n) {
    return n >= 0 && n < 10 ? '0' + n : '' + n;
}
function getDate(){
    var oDate = new Date();
    var oldTime = oDate.getTime();
    // var newDate = new Date("2018/5/25 00:00:00");
    var newDate= new Date(document.getElementsByClassName("activity_time")[0].value);
    var newTime = newDate.getTime();
    var second = Math.floor((newTime - oldTime)/1000);
    var day = Math.floor(second/86400);
    second = second % 86400;
    var hour = Math.floor(second/3600);
    second %= 3600;
    var minute = Math.floor(second/60);
    second %= 60;
    document.getElementById("day").innerHTML=tow(day);
    document.getElementById("hour").innerHTML=tow(hour);
    document.getElementById("minute").innerHTML=tow(minute);
    document.getElementById("second").innerHTML=tow(second);
}
getDate();
setInterval(getDate, 1000);