window.onload=function(){
    function fun1(){
        document.getElementById("small").style.display="inline-block";
    }
    function fun2(){
        document.getElementById("small").style.display="none";
    }
    function fun3(){
        aa=setTimeout(function(){document.getElementById("small").style.display="none";},1000);
    }
    function fun4(){
        clearTimeout(aa);
    }
    var dele=document.getElementsByClassName("deleat-one");
    for(let i = 0 ; i < dele.length ; i++){
        dele[i].onclick=function(){
            var that = this;
            var foot = $(that).parent().children(".footprintId").text();
            console.log(foot);
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
                success:function(data){
                    $(that).parent().remove();
                    console.log($(that));
                    console.log(data);
                },
                error:function(){
                    alert("删除失败，请稍后重试！");
                }
            });
        }
    }
}