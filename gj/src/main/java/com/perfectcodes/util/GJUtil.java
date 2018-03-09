package com.perfectcodes.util;
/**
 * @author xpf
 * @description 工具类
 * @date 2018/3/8 16:23
 */
public class GJUtil {
    /**
     * @author xpf
     * @description 隐藏部分手机号
     * @date 2018/3/8 16:22
     */
    public static String hidePartPhone(String phone){
        if (phone == null || "".equals(phone)){
            return "";
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
    /**
     * @author xpf
     * @description 隐藏部分身份证号
     * @date 2018/3/8 16:23
     */
    public static String hidePartIDCard(String idCard){
        if (idCard == null || "".equals(idCard)){
            return "";
        }
        return idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})","$1****$2");
    }
}
