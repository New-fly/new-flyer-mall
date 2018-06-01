//获取所有a标签
/*var adelbtns=document.getElementsByClassName("aa2");
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
}*/
function deleteUser(userId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/AdminUserController/adminDeleteUser?userId="+userId;
    }
}
//添加
//var addbtn1=document.getElementsByClassName("btn btn-primary add")[0];

function block1() {
    var modifi = document.getElementsByClassName("windoww")[0];
    modifi.style.display = "block";
}
//添加的值不能为空
// function dd(){
//     var ousername = document.getElementById('username');
//     var userrole = document.getElementById('userrole');
//     var userphone = document.getElementById('userphone');
//     var pwd = document.getElementById('pwd');
//     var email = document.getElementById('email');
//     if(ousername.value==''){
//         alert("请输入用户名");
//         return false;
//     }if(userrole.value=='' ){
//         alert("请输入角色");
//         return false;
//     }if(userphone.value==''){
//         alert("请输入电话");
//         return false;
//     }if(pwd.value==''){
//         alert("请输入密码");
//         return false;
//     }if(email.value=='') {
//         alert("请输入邮箱");
//         return false;
//     }else{
//         document.getElementById('add').setAttribute('type','submit');
//         return true;
//     }
//
// }
var i;
var opcate=document.getElementsByClassName("opcate")[0];
var del=document.getElementsByClassName("del")[0];
//点选按钮
var checx=document.getElementsByClassName("checx");
var shanchu=document.getElementsByClassName("shanchu")[0];
var radio=document.getElementsByClassName("demo--radio");
console.log(radio.length);
opcate.onclick=function () {
    opcate.style.display="none";
    del.style.display="inline-block";
    del.style.width="54px";
    shanchu.style.display="inline-block";
    for(let i=0;i<radio.length;i++){
        checx[i].style.display="inline-block";
    }
};
//获取要删除用户的id
for (let i = 0; i < radio.length; i++) {
    radio[i].onchange = function () {
        var ids = "";
        for (let k = 0; k < radio.length; k++) {
            if (radio[k].checked == true) {
                var foot = document.getElementsByClassName("activityid")[k].innerHTML;
                ids = ids + foot + ",";
            }
        }
        ids = ids.substring(0,ids.lastIndexOf(','));
        document.getElementsByClassName("ids")[0].value = ids;
    }
}
//删除用户
del.onclick=function () {
    var ids = document.getElementsByClassName("ids")[0].value;
    $.ajax({
        url: "/AdminUserController/adminDeleteUsers",
        data: {
            "userId": ids
        },
        dataType: "text",
        success: function () {
            $(":checked").parent().parent().parent().remove();
            opcate.style.display="inline-block";
            del.style.display="none";
            shanchu.style.display="none";
            // checx.style.display="none";
            for(let i=0;i<radio.length;i++){
                checx[i].style.display="none";
            }
        },
        error: function () {
            alert("请求失败");
        }

    });
};




