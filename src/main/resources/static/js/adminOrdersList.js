function deleteOrder(orderId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/OrderController/deleteOrder?orderId="+orderId;
    }
}
// window.onload=function () {

    //console.log(allas);
    //为每个button绑定一个单击相应函数
    /*for(i=0;i<allas.length;i++){
        allas[i].onclick=delA;
    }
    var allmodifibtns=document.getElementsByClassName("btn btn-primary modifi");
    *///console.log(allmodifibtns);
    //为每个修改按钮添加一个单击向应函数
//     var tr;
//     for(i=0;i<allmodifibtns.length;i++){
//         allmodifibtns[i].onclick=modify;
//
//     }
//     function modify() {
//         var modifi = document.getElementsByClassName("windoww")[0];
//         modifi.style.display = "block";
//         tr=this.parentNode.parentNode;
//         var button=tr.getElementsByClassName("btn btn-primary modifi")[0];
//
//     }
//     function update() {
//         //var aaa=document.getElementById("#").value;
//         var bbb=document.getElementById("Name").value;
//         var ccc=document.getElementById("Mumber").value;
//         var ddd=document.getElementById("Picture").value;
//         var eee=document.getElementById("Keywords").value;
//         // var fff=document.getElementById("Time").value;
//         // var ggg = document.getElementById('Address').value;
//         // console.log(tr.innerHTML);
//         tr.innerHTML=" <td></td> <td>"+bbb+"</td> <td>"+ccc+"</td> <td>"+ddd+"</td> <td>"+eee+"</td> <td></td> <td></td> <td><a class=\"a2\">删除</a>\n" + "<button type=\"button\" class=\"btn btn-primary modifi\" style=\"background-color: #007BFF\" onclick=\"modify(this)\">修改</button></td>";
//         // console.log(tr.innerHTML);
//         //修改过后的添加点击删除事件：
//         var a=tr.getElementsByClassName("a2")[0];
//         a.onclick=delA;
//         //修改过后的再添加点击修改事件
//         var button=tr.getElementsByClassName("btn btn-primary modifi")[0];
//         button.onclick=modify&&update;
//         //修改过后，弹窗消失
//         var modifi = document.getElementsByClassName("windoww")[0];
//         modifi.style.display = "none";
//     }
//     //点击按钮取消
//     // var cancelbtn=document.getElementById("cancel");
//     // cancel.onclick=function(){
//     //     var modifi = document.getElementsByClassName("windoww")[0];
//     //     modifi.style.display = "none";
//     // };
// };