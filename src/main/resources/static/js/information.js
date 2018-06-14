    //修改用户名
	document.getElementsByClassName("change_name")[0].onclick=function(){
		document.getElementsByClassName("black_name")[0].style.width="100%";
		document.getElementsByClassName("black_name")[0].style.height="100%";
		document.getElementsByClassName("black_name")[0].style.top="0";
		document.getElementsByClassName("black_name")[0].style.left="0";
	};
	//修改密码
	document.getElementsByClassName("change_password")[0].onclick=function(){
		document.getElementsByClassName("black_password")[0].style.width="100%";
		document.getElementsByClassName("black_password")[0].style.height="100%";
		document.getElementsByClassName("black_password")[0].style.top="0";
		document.getElementsByClassName("black_password")[0].style.left="0";
	};
	//修改邮箱
	document.getElementsByClassName("change_mail")[0].onclick=function(){
		document.getElementsByClassName("black_oldmail")[0].style.width="100%";
		document.getElementsByClassName("black_oldmail")[0].style.height="100%";
		document.getElementsByClassName("black_oldmail")[0].style.top="0";
		document.getElementsByClassName("black_oldmail")[0].style.left="0";
	};
    //修改头像
    document.getElementsByClassName("chang_img")[0].onclick=function(){
        document.getElementsByClassName("black_img")[0].style.width="100%";
        document.getElementsByClassName("black_img")[0].style.height="100%";
        document.getElementsByClassName("black_img")[0].style.top="0";
        document.getElementsByClassName("black_img")[0].style.left="0";
    };
    //修改头像
    document.getElementsByClassName("user_img2")[0].onclick=function(){
        document.getElementsByClassName("black_img")[0].style.width="100%";
        document.getElementsByClassName("black_img")[0].style.height="100%";
        document.getElementsByClassName("black_img")[0].style.top="0";
        document.getElementsByClassName("black_img")[0].style.left="0";
    };

	for(let i = 0 ; i < 5 ; i++){
		//取消关闭弹窗
		document.getElementsByClassName("cancel")[i].onclick=function(){
			document.getElementsByClassName("black")[i].style.width="0";
			document.getElementsByClassName("black")[i].style.height="0";
			document.getElementsByClassName("black")[i].style.top="50%";
			document.getElementsByClassName("black")[i].style.left="50%";
		};
		//差号关闭弹窗
		document.getElementsByClassName("close")[i].onclick=function(){
			document.getElementsByClassName("black")[i].style.width="0";
			document.getElementsByClassName("black")[i].style.height="0";
			document.getElementsByClassName("black")[i].style.top="50%";
			document.getElementsByClassName("black")[i].style.left="50%";
		}
        //  I判断原密码与新密码是否一致
        function judge(){
            var repassword=document.getElementById("repassword").value;
            var again=document.getElementById("again").value;
            if(repassword==again){
                document.getElementById("error").innerHTML="";
                return true;
                }
                else{
                document.getElementById("error").innerHTML="请保持密码输入一致";
            }

        }
        var new_mail= document.getElementsByClassName("new_mail")[0];
        function test() {
            //II.对电子邮件的验证
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (new_mail.value != "") {
                if (myreg.test(new_mail.value)) {
                    document.getElementById("error1").innerHTML = "";
                    return true;
                } else {
                    document.getElementById("error1").innerHTML = "请输入有效的E_mail！";
                    return false;
                }
            }
        }

//III.用户名有关判断
        function user_test(){
            var new_name=document.getElementsByClassName("new_name")[0];
            //用户名正则，4到16位（字母，数字，下划线，减号）
            var uPattern = /^[a-zA-Z0-9_-]{4,16}$/;
            if (new_name.value != "") {
                if (uPattern.test(new_name.value)) {
                    document.getElementById("error2").innerHTML = "";
                    return true;
                } else {
                    document.getElementById("error2").innerHTML = "请输入有效用户名";
                    return false;
                }
            }
            if(new_name.value==""||new_name.value==null){
                document.getElementById("error2").innerHTML = "用户名不能为空";
            }else{
                document.getElementById("error2").innerHTML = "";
            }
        }
    }
    document.getElementById("prepassword").onblur=function(){
        var oldpassword1=document.getElementById("prepassword").value;
        var oldpassword2=document.getElementsByClassName("password1")[0].innerHTML;
        console.log(oldpassword1);
        console.log(oldpassword2);
        if (oldpassword1!=oldpassword2){
            alert("原密码输入错误！！")
        }
    };
	//验证验证码
    $(".fdgdfgfdgfd").on("click",function () {
        var user_mail=document.getElementsByClassName("user_mail")[0].value;
        var user_code=document.getElementsByClassName("user_code1")[0].value;
        $.ajax({
            type: 'POST',
            url: '/UserManagementController/changeEmail',
            data: {
                "user_mail":user_mail,
                "user_code":user_code
            },
            error: function () {
                alert("123")
            },
            success: function (data) {
               // alert("成功")
                document.getElementsByClassName("black_oldmail")[0].style.width="0";
                document.getElementsByClassName("black_oldmail")[0].style.height="0";
                document.getElementsByClassName("black_oldmail")[0].style.top="50%";
                document.getElementsByClassName("black_oldmail")[0].style.left="50%";
                document.getElementsByClassName("prompt")[0].innerHTML = "修改成功";
                document.getElementsByClassName("prompt")[0].style.display = "block";
                // change_password.innerHTML=repassword;
                setTimeout(function () {
                    document.getElementsByClassName("prompt")[0].style.display = "none";
                }, 1000)
            }
        });
    });


