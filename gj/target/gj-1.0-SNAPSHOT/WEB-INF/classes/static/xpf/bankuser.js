function submitInfo(){
    // $('form').validator({
    //     onValid: function(validity) {
    //         console.info("--------true---------");
    //     },
    //
    //     onInValid: function(validity) {
    //         console.info("--------false---------");
    //     }
    // });
    var param = $(".am-form").param();
    console.info(param);
    $.http.post({
        url:"apply",
        data:param,
        success:function(r){
            console.info(r.message)
        }
    })
    return false;
}
$(function(){
    $("input:checkbox").on("change",function(r){
        var subButton = $("button:submit");
        if($(this).prop("checked")){
            subButton.removeProp("disabled")
        }else{
            subButton.prop("disabled",true)
        }
    })
    // $('form').validator({
    //     onValid: function(validity) {
    //     },
    //
    //     onInValid: function(validity) {
    //         console.info("--------false---------");
    //     }
    // });
})