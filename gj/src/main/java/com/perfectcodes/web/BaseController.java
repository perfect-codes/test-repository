package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected CommonResp successResp(){
        return new CommonResp().setStatus(1).setMessage("操作成功");
    }

    protected CommonResp failureResp(){
        return new CommonResp().setStatus(0).setMessage("操作失败");
    }

}
