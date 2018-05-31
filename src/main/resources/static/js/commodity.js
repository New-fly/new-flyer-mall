window.onload=function(){
    function deleteProduct(productId) {
        var isDel = confirm("您确认要删除吗？");
        if(isDel){
            //要删除
            location.href = "/AdminProductController/adminDeleteProduct?productId="+productId;
        }
    }

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
    }
};

