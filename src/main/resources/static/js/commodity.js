//1.获取所有删除按钮标签
var adelbtns=document.getElementsByClassName("aa2");
function delA(){
    var tr=this.parentNode.parentNode;
    //获取要删除的名字
    var name=tr.children[0].innerHTML;
    var flag=confirm("确定要删除"+name+"吗？");
    if(flag){
        tr.parentNode.removeChild(tr);
    }
}
for(i=0;i<adelbtns.length;i++){
    adelbtns[i].onclick=delA;
}
//2.点击添加出现弹窗
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
