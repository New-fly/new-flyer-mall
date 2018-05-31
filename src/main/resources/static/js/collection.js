window.onload=function(){
    var dele=document.getElementsByClassName("delete");
    for(let i = 0 ; i < dele.length ; i++){
        dele[i].onclick=function(){
            var collection=document.getElementsByClassName("coid")[i].innerHTML;
            var userid=document.getElementsByClassName("userid")[0].innerHTML;
            $.ajax({
                url:"/ProductListController/deleteCollectionProduct",
                type:"POST",
                data:{
                    "collectionId":collection,
                    "userId":userid
                },
                async:true,
                cache:true,
                traditional:false,
                dataType:"text",
                success:function(){
                    document.getElementsByClassName("prompt")[0].innerHTML = "删除成功";
                    document.getElementsByClassName("prompt")[0].style.display = "block";
                    setTimeout(function () {
                        document.getElementsByClassName("prompt")[0].style.display = "none";
                        location.reload();
                    }, 1000)
                },
                error:function(){
                    alert("删除失败，请稍后重试！");
                }
            });
        }
    }
    //点击批量删除按钮触发函数
    document.getElementsByClassName("alldelete")[0].onclick=function(){
        var length=document.getElementsByClassName("block-2");
        for(let i=0;i<length.length;i++){
            document.getElementsByClassName("allinput")[i].style.display="inline-block";
            document.getElementsByClassName("delete")[i].style.display="none";
        }
        document.getElementsByClassName("yes")[0].style.display="block";
        var one = document.getElementsByClassName('ipt');
        for (let i = 0; i < one.length; i++) {
            one[i].onchange = function () {
                var ids ="";
                for (let k = 0; k < one.length; k++) {
                    if (one[k].checked == true) {
                        var cool = document.getElementsByClassName("coid")[k].innerHTML;
                        ids = ids + cool + ",";
                    }
                }
                ids = ids.substring(0,ids.lastIndexOf(','));
                document.getElementsByClassName("inputids")[0].value = ids;
            }
        }
    }
    //点击删除按钮进行批量删除
    document.getElementsByClassName("yes")[0].onclick=function(){
        var allcoolid=document.getElementsByClassName("inputids")[0].value;
        $.ajax({
            url: "/ProductListController/deleteCollectionProducts",
            data: {
                "collectionId":allcoolid,
            },
            dataType: "text",
            type:"POST",
            success: function () {
                $(":checked").parent().parent().remove();
                var ipt=document.getElementsByClassName("ipt");
                for(var m=0;m<ipt.length;m++){
                    document.getElementsByClassName("allinput")[m].style.display="none";
                    document.getElementsByClassName("delete")[m].style.display="block";
                }
                document.getElementsByClassName("yes")[0].style.display="none";
                document.getElementsByClassName("prompt")[0].innerHTML = "删除成功";
                document.getElementsByClassName("prompt")[0].style.display = "block";
                setTimeout(function () {
                    document.getElementsByClassName("prompt")[0].style.display = "none";
                }, 1000)
            },
            error: function () {
                alert("请求失败！！")
            }
        })
    }
};