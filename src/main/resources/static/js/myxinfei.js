function deleteProduct(orderId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/AdminProductController/adminDeleteProduct?orderId="+orderId;
    }
}