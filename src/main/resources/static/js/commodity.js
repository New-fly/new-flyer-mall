function deleteProduct(productId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/AdminProductController/adminDeleteProduct?productId="+productId;
    }
}
window.onload=function(){
// //2.点击添加出现弹窗
    function tanchuang(){
        var modifi = document.getElementsByClassName("windoww")[0];
        modifi.style.display = "block";
    }
//添加的值不能为空
    var keywords=document.getElementById('keywords');
    var condition=document.getElementById('condition');
    var price1=document.getElementById('price1');
    var price2=document.getElementById('price2');
    var picture=document.getElementById('picture');
    var details=document.getElementById('details');
    var kind=document.getElementById('kind');
    var activity=document.getElementById('activity');
    function dd(){
        if(keywords.value==''){
            return false;
        }if(condition.value=='' ){
            return false;
        }if(price1.value==''){
            return false;
        }if(price2.value==''){
            return false;
        }if(picture.value=='') {
            return false;
        }if(details.value==''){
            return false;
        }if(kind.value==''){
            return false;
        }if(activity.value==''){
            return false;
        }else{
            document.getElementById('add').setAttribute('type','submit');
            return true;
        }
    }
    document.getElementsByClassName("arise")[0].onclick=function(){
        var hide=document.getElementsByClassName("hide");
        for(let i = 0 ; i < hide.length ; i++){
            document.getElementsByClassName("hide")[i].style.display="inline-block";
        }
        var hide2=document.getElementsByClassName("hide2");
        for(let i = 0 ; i < hide2.length ; i++){
            document.getElementsByClassName("hide2")[i].style.display="block";
        }
        this.style.display="none";
    };
    var one = document.getElementsByClassName('select');
    for (let i = 0; i < one.length; i++) {
        one[i].onchange = function () {
            var allid = "";
            for (let k = 0; k < one.length; k++) {
                if (one[k].checked == true) {
                    var foot = document.getElementsByClassName("productid")[k].innerHTML;
                    allid = allid + foot + ",";
                }
            }
            allid = allid.substring(0,allid.lastIndexOf(','));
            document.getElementsByClassName("ids")[0].value = allid;
        }
    }
    document.getElementsByClassName("delet")[0].onclick=function(){
        var allid=document.getElementsByClassName("ids")[0].value;
        if(allid.length<1){
            alert("请勾选需要删除的项")
        }else{
            $.ajax({
                url: "/AdminProductController/adminDeleteProducts",
                data: {
                    "productId":allid,
                },
                dataType: "text",
                type:"POST",
                success: function () {
                    $(":checked").parent().parent().parent().remove();
                    var hide=document.getElementsByClassName("hide");
                    for(let i = 0 ; i < hide.length ; i++){
                        document.getElementsByClassName("hide")[i].style.display="none";
                    }
                    var hide2=document.getElementsByClassName("hide2");
                    for(let i = 0 ; i < hide2.length ; i++){
                        document.getElementsByClassName("hide2")[i].style.display="none";
                    }
                    document.getElementsByClassName("arise")[0].style.display="inline-block"
                },
                error: function () {
                    alert("请求失败！！")
                }
            })
        }
    }
};