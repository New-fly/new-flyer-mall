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
var i;
var opcate=document.getElementsByClassName("opcate")[0];
var del=document.getElementsByClassName("del")[0];
//点选按钮
var checx=document.getElementsByClassName("checx");
var shanchu=document.getElementsByClassName("shanchu")[0];
var radio=document.getElementsByClassName("demo--radio");
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
    if(ids.length<1){
        alert("请勾选需要删除项")
    }else{
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
    }
};




