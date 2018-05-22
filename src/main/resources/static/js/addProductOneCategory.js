window.onload=function () {
    var category_name = document.getElementById('category_name');
    var file = document.getElementById('file');
    var add = document.getElementById('add');
    add.onclick = function () {
        if (category_name.value == null || category_name.value == "") {
            alert("请写入分类名称");
            return false;
        }
        if (file.value == null || file.value == "") {
            alert("请上传文件");
            return false;
        } else {
            return true;
        }

    };
};