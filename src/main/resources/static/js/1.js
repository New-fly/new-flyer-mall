var viewmodel = avalon.define({
$id: "viewmodel", datalist
:
{
}
,
text: "请求数据", request
:

function () {
    $.ajax({type: "post", url: "/hello/data", //向后端请求数据的url data: {}, success: function (data) { $('button').removeClass("btn-primary").addClass("btn-success").attr('disabled', true); viewmodel.datalist = data; viewmodel.text = "数据请求成功，已渲染"; } }); } });
