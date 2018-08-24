// $('#fileupload').fileupload({
//     url: 'upload',
//     add: function (e, data) {
//         // 不用点击直接上传
//         var jqXHR = data.submit()
//             .success(function (result, textStatus, jqXHR) {
//                 if(result.data.url){
//                     $(".gj-qrimage").html("").append("<img src='"+result.data.url+"'/>");
//                 }
//             })
//             .error(function (jqXHR, textStatus, errorThrown) {})
//             .complete(function (result, textStatus, jqXHR) {});
//     }
// });
function ajaxFileUpload() {
    $.ajaxFileUpload
    (
        {
            url: 'upload', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'fileupload', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (result, status)  //服务器成功响应处理函数
            {
                $(".gj-qrimage").html("").append("<img src='"+result.data.url+"'/>");
                /*if (typeof (data.error) != 'undefined') {
                    if (data.error != '') {
                        alert(data.error);
                    } else {
                        alert(data.msg);
                    }
                }*/
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                //alert(e);
                console.info(data)
            }
        }
    )
    return false;
}
function submitInfo(){
    var param = $(".am-form").param();
    console.info(param);
    $.http.post({
        url:"regist",
        data:param,
        success:function(r){
            $.messager.success(r.message)
        }
    })
    return false;
}