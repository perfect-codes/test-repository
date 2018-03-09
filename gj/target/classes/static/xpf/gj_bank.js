$(function(){
	var seller = getUrlParam("seller");
	if(seller != null){
		$("#gj-btn-tg").remove()
		$("#gj-btn-sq").removeClass("am-u-sm-6").addClass("am-u-sm-12")
		var btnApply = $("#gj-btn-sq").children("a").first();
        btnApply.attr("href",btnApply.attr("href")+"&seller="+seller)
	}
})
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}