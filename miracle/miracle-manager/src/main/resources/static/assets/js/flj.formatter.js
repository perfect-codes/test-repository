function status(cellvalue, options, rowObject) {
    if (cellvalue === 0){
        return "<span style='color: red;'>停用</span>";
    }else if (cellvalue === 1){
        return "<span style='color: darkseagreen;'>正常</span>";
    }else if (cellvalue === 2){
        return "<span style='color: #999;'>删除</span>";
    }
    return "<span style='color: #999;'>--</span>"
}
function gender(cellvalue, options, rowObject) {
    if (cellvalue === 0){
        return "<span style='color: #999;'>未知</span>";
    }else if (cellvalue === 1){
        return "<i class='fa fa-male' title='男'></i>男";
    }else if (cellvalue === 2){
        return "<i class='fa fa-female' title='女'></i>女";
    }
    return "<span style='color: #999;'>--</span>"
}