window.onload=function(){
    document.getElementsByClassName("arise")[0].onclick=function(){
        var hide=document.getElementsByClassName("hide");
        for(let i = 0 ; i < hide.length ; i++){
            document.getElementsByClassName("hide")[i].style.display="inline-block";
        }
        var hide2=document.getElementsByClassName("hide2");
        for(let i = 0 ; i < hide2.length ; i++){
            document.getElementsByClassName("hide2")[i].style.display="block";
        }
        this.style.display="none";
    };
    var one = document.getElementsByClassName('select');
    for (let i = 0; i < one.length; i++) {
        one[i].onchange = function () {
            var ids = "";
            for (let k = 0; k < one.length; k++) {
                if (one[k].checked == true) {
                    var foot = document.getElementsByClassName("activityid")[k].innerHTML;
                    ids = ids + foot + ",";
                }
            }
            ids = ids.substring(0,ids.lastIndexOf(','));
            document.getElementsByClassName("ids")[0].value = ids;
        }
    }
    document.getElementsByClassName("delet")[0].onclick=function(){
        var allid=document.getElementsByClassName("ids")[0].value;
        if(allid.length<1){
            alert("请勾选需要删除项")
        }else{
            $.ajax({
                url: "/AdminActivityController/adminDeleteActivitys",
                data: {
                    "activityId":allid,
                },
                dataType: "text",
                type:"POST",
                success: function () {
                    $(":checked").parent().parent().parent().remove();
                    var hide=document.getElementsByClassName("hide");
                    for(let i = 0 ; i < hide.length ; i++){
                        document.getElementsByClassName("hide")[i].style.display="none";
                    }
                    var hide2=document.getElementsByClassName("hide2");
                    for(let i = 0 ; i < hide2.length ; i++){
                        document.getElementsByClassName("hide2")[i].style.display="none";
                    }
                    document.getElementsByClassName("arise")[0].style.display="inline-block"
                },
                error: function () {
                    alert("请求失败！！")
                }
            })
        }
    };
};