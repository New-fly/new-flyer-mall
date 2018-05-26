window.onload=function(){
	//修改用户名
	document.getElementsByClassName("change_name")[0].onclick=function(){
		document.getElementsByClassName("black_name")[0].style.width="100%";
		document.getElementsByClassName("black_name")[0].style.height="100%";
		document.getElementsByClassName("black_name")[0].style.top="0";
		document.getElementsByClassName("black_name")[0].style.left="0";
	}
	//修改密码
	document.getElementsByClassName("change_password")[0].onclick=function(){
		document.getElementsByClassName("black_password")[0].style.width="100%";
		document.getElementsByClassName("black_password")[0].style.height="100%";
		document.getElementsByClassName("black_password")[0].style.top="0";
		document.getElementsByClassName("black_password")[0].style.left="0";
	}
	//修改邮箱
	document.getElementsByClassName("change_mail")[0].onclick=function(){
		document.getElementsByClassName("black_mail")[0].style.width="100%";
		document.getElementsByClassName("black_mail")[0].style.height="100%";
		document.getElementsByClassName("black_mail")[0].style.top="0";
		document.getElementsByClassName("black_mail")[0].style.left="0";
	}
	for(let i = 0 ; i < 3 ; i++){
		//取消关闭弹窗
		document.getElementsByClassName("cancel")[i].onclick=function(){
			document.getElementsByClassName("black")[i].style.width="0";
			document.getElementsByClassName("black")[i].style.height="0";
			document.getElementsByClassName("black")[i].style.top="50%";
			document.getElementsByClassName("black")[i].style.left="50%";
		}
		//差号关闭弹窗
		document.getElementsByClassName("close")[i].onclick=function(){
			document.getElementsByClassName("black")[i].style.width="0";
			document.getElementsByClassName("black")[i].style.height="0";
			document.getElementsByClassName("black")[i].style.top="50%";
			document.getElementsByClassName("black")[i].style.left="50%";
		}
	}
	var modify0=document.getElementsByClassName("modify0")[0];
	var modify1=document.getElementsByClassName("modify1")[0];
	var modify2=document.getElementsByClassName("modify2")[0];
	var userId=document.getElementById("userId");
	var user_name=document.getElementsByClassName("new_name")[0].value;
	modify0.onclick=function () {
		$.ajax({
        url:"/UserManagementController/changeInformation",
        data:{
            "userId":userId,
            "user_name":user_name,
        },
        dataType:"text",
        success:function(){
            alert("成功");
        },
        error:function () {
            alert("请求失败")
        }
    });
	}
};