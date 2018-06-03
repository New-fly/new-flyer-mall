function deleteProduct(orderId) {
    var isDel = confirm("您确认要删除吗？");
    if(isDel){
        //要删除
        location.href = "/AdminProductController/adminDeleteProduct?orderId="+orderId;
    }
}
function userAccepted(orderId) {
    var isDel = confirm("您确认要收货吗？");
    if(isDel){
        //要收货
        location.href = "/UserOrderController/userAccepted?orderId="+orderId;
    }
}
function returnOrder(orderId) {
    var isDel = confirm("您确认要退货吗？");
    if(isDel){
        //要删除
        location.href = "/aliPay/refund?orderId="+orderId;
    }
}