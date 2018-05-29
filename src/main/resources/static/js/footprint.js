window.onload=function(){
    var dele=document.getElementsByClassName("deleat-one");
    for(let i = 0 ; i < dele.length ; i++){
        dele[i].onclick=function(){
            var that = this;
            var foot = $(that).parent().children(".footprintId").text();
            $.ajax({
                url:"/FootprintController/deleteFootprint",
                type:"POST",
                data:{
                    "footprintId":foot
                },
                async:true,
                cache:true,
                traditional:false,
                dataType:"text",
                success:function(){
                    $(that).parent().remove();
                },
                error:function(){
                    alert("删除失败，请稍后重试！");
                }
            });
        }
    }
    //控制在输入框内放入所有需要删除的id
    document.getElementsByClassName("multiple_delete")[0].onclick=function() {
        var single = document.getElementsByClassName("single");
        for (let i = 0; i < single.length; i++) {
            document.getElementsByClassName("deleat-one")[i].style.display = "none";
            document.getElementsByClassName("select")[i].style.display = "inline-block";
        }
        document.getElementsByClassName("yes")[0].style.display = "block";
        var one = document.getElementsByClassName('select1');
        for (let i = 0; i < one.length; i++) {
            one[i].onchange = function () {
                var allid = "";
                for (let k = 0; k < one.length; k++) {
                    if (one[k].checked == true) {
                        var foot = document.getElementsByClassName("foot")[k].innerHTML;
                        allid = allid + foot + ",";
                    }
                }
                allid = allid.substring(0,allid.lastIndexOf(','));
                document.getElementsByClassName("inputids")[0].value = allid;
            }
        }
    };
    //删除按钮去删除触发ajax
    document.getElementsByClassName("yes")[0].onclick=function(){
        var allfootid=document.getElementsByClassName("inputids")[0].value;
        $.ajax({
            url: "/FootprintController/deleteFootprints",
            data: {
                "footprintId":allfootid,
            },
            dataType: "text",
            type:"POST",
            success: function () {
                $(":checked").parent().parent().remove();
                var single=document.getElementsByClassName("single");
                for(var m=0;m<single.length;m++){
                    document.getElementsByClassName("deleat-one")[m].style.display = "block";
                    document.getElementsByClassName("select")[m].style.display = "none";
                    document.getElementsByClassName("yes")[0].style.display = "none";
                }
            },
            error: function () {
                alert("请求失败！！")
            }
        })
    }
};