// $(document).ready(function(){
// 		var allas=document.getElementsByClassName("aa1")[0];
//     	allas.onclick=function(){
// 		        var tr=this.parentNode.parentNode;
// 		        //获取要删除的名字
// 		        var name=tr.children[0].innerHTML;
// 		        var flag=confirm("确定要删除"+name+"吗？");
// 		        if(flag){
// 		            tr.parentNode.removeChild(tr);
// 		        }
// 		}
//
// 		//获取所有修改按钮
// 		var allmodifibtns=document.getElementsByClassName("btn btn-primary modifi");
// 		//console.log(allmodifibtns);
// 		//为每个修改按钮添加一个单击向应函数
// 		var tr;
// 		for(i=0;i<allmodifibtns.length;i++){
// 		    allmodifibtns[i].onclick=modify;
// 		}
// 		function modify() {
// 		    var modifi = document.getElementsByClassName("windoww")[0];
// 		    modifi.style.display = "block";
// 		    tr=this.parentNode.parentNode;
// 		    var button=tr.getElementsByClassName("btn btn-primary modifi")[0];
// 		}
// 		//document.getElementById("change").onclick=function(){update()}
// 		function update() {
//     var aaa=document.getElementById("num").value;
//     var bbb=document.getElementById("userphoto").value;
//     var ccc=document.getElementById("username").value;
//     tr.innerHTML=" <td>"+aaa+"</td> <td>"+bbb+"</td> <td>"+ccc+"</td><td><a class=\"aa1\">删除</a>\n" + "<button type=\"button\" class=\"btn btn-primary modifi\" style=\"background-color: #007BFF\" onclick=\"modify(this)\">修改</button></td>";
//     // console.log(tr.innerHTML);
//     //修改过后的添加点击删除事件：
//     var a=tr.getElementsByClassName("aa1")[0];
//     a.onclick=delA;
//     //修改过后的再添加点击修改事件
//     var button=tr.getElementsByClassName("btn btn-primary modifi")[0];
//     button.onclick=modify&&update;
//
//     //修改过后，弹窗消失
//     var modifi = document.getElementsByClassName("windoww")[0];
//     modifi.style.display = "none";
// }
// 		var cancelbtn=document.getElementById("cancel");
// 		cancel.onclick=function(){
// 		    var modifi = document.getElementsByClassName("windoww")[0];
// 		    modifi.style.display = "none";
// 		};
// 		var addbtn1=document.getElementsByClassName("btn btn-primary add")[0];
// 		addbtn1.onclick=function () {
// 		    var modifi=document.getElementsByClassName("windoww")[0];
// 		    modifi.style.display="block";
// 		};
// 		var addbtn2=document.getElementById("add");
// 		addbtn2.onclick=function(){
// 		    var oNum = document.getElementById('num').value;
// 		    var oUserphoto = document.getElementById('userphoto').value;
// 		    var ousername = document.getElementById('username').value;
// 		    // alert(oNum );
// 		    //创建一个tr
// 		    var tr=document.createElement("tr");
// 		    tr.innerHTML="<td>"+oNum+"</td>"
// 		        +"<td>"+oUserphoto+"</td>"+
// 		        "<td>"+ousername+"</td>"+
// 		       "<td><a class='aa1' >删除</a> <button type=\"button\" class=\"btn btn-primary modifi\" style=\"background-color: #007BFF\" onclick=\"modify()\">修改</button></td>";
// 		    var a=tr.getElementsByClassName("aa1")[0];
// 		    a.onclick=delA;
// 		    var tbody=document.getElementsByTagName("tbody")[0];
// 		    tbody.appendChild(tr);
// 		    var modifi=document.getElementsByClassName("windoww")[0];
// 		    modifi.style.display="none";
// 		};
// 		var mDD = document.getElementById("anim").getElementsByTagName("option");
// 		for(var i = 0; i < mDD.length; i++) {
// 			(function(index) {
// 				mDD[index].onclick = function() {
// 					console.log(mDD[index].innerHTML)
// 				}
// 			})(i);
// 		}
// });
function deleteProduct(categoryId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/AdminProductController/deleteProductCategory?categoryId="+categoryId;
    }
}