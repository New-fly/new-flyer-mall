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
                success:function(data){
                    $(that).parent().remove();
                },
                error:function(){
                    alert("删除失败，请稍后重试！");
                }
            });
        }
    }
    document.getElementsByClassName("multiple_delete")[0].onclick=function(){
        var asdasd=document.getElementsByClassName("single");
        for(let i=0;i<asdasd.length;i++){
            document.getElementsByClassName("deleat-one")[i].style.display="none";
            document.getElementsByClassName("select")[i].style.display="block";
        }
        document.getElementsByClassName("yes")[0].style.display="block";
        var one=document.getElementsByClassName('select');
        var allid="";
        for(let i=0;i<one.length;i++){
            one[i].onchange = function () {
                allid="";
                for(let k=0;k<one.length;k++){
                    if (one[k].checked == true) {
                        var id=document.getElementsByClassName("id")[k].innerHTML;
                        allid=allid+id+",";
                    }
                }
                document.getElementsByClassName("inputids")[0].value=allid;
            }
        }
    }
};