//获取所有a标签
var allas=document.getElementsByTagName("aa1");
function delA() {

        var tr=this.parentNode.parentNode;
        //获取要删除的名字
        var name=tr.children[0].innerHTML;
        var flag=confirm("确定要删除"+name+"吗？");
        if(flag==true){
            tr.parentNode.removeChild(tr);
        }
}
//console.log(allas);
//为每个button绑定一个单击相应函数
for(i=0;i<allas.length;i++){
    allas[i].onclick=delA;
}
//添加
//var addbtn1=document.getElementsByClassName("btn btn-primary add")[0];
    var ousername = document.getElementById('username');
    var userrole = document.getElementById('userrole');
    var userphone = document.getElementById('userphone');
    var pwd = document.getElementById('pwd');
    var email = document.getElementById('email');
function block1() {
    var modifi = document.getElementsByClassName("windoww")[0];
    modifi.style.display = "block";
}
//添加的值不能为空
function dd(){
    if(ousername.value==''){
        return false;
    }if(userrole.value=='' ){
        return false;
    }if(userphone.value==''){
        return false;
    }if(pwd.value==''){
        return false;
    }if(email.value=='') {
        return false;
    }else{
        document.getElementById('add').setAttribute('type','submit');
        return true;
    }

}





