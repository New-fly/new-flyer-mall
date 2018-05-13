var prov=$("#prov");
var city=$("#city");
var dist=$("#dist");
var text = $("#text");
var btn=document.getElementById("btn1");
btn.onclick=function () {
    var sb;
    sb=prov.val()+' '+city.val()+' '+dist.val()+' '+text.val();
    // console.log(sb);

    var text2=$("#text2");
     text2.val(sb);
    // document.getElementById('text2').value=sb;
    // console.log(text2.value);

}