$('#fileupload').fileupload({
    url: 'upload',
    add: function (e, data) {
        // 不用点击直接上传
        var jqXHR = data.submit()
            .success(function (result, textStatus, jqXHR) {
                console.info(result)
                console.info(textStatus)
                $(".gj-qrimage").append("<img src="++"/>")
                result.data.id
            })
            .error(function (jqXHR, textStatus, errorThrown) {})
            .complete(function (result, textStatus, jqXHR) {});
    }
});