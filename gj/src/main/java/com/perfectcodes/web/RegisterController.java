package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.domain.Banner;
import com.perfectcodes.domain.Register;
import com.perfectcodes.service.BannerService;
import com.perfectcodes.service.RegisterService;
import com.perfectcodes.web.vo.RegisterVo;
import com.perfectcodes.web.vo.UserSupplyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class RegisterController extends BaseController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/registPage")
    public String pageIndex(Model model) throws Exception {
        Banner banner = new Banner().setType(2).setStatus(StatusEnum.NORMAL.getVal());
        List<Banner> banners = bannerService.findAll(banner);
        model.addAttribute("banner", banners.size() == 0 ? null : banners.get(0));
        return "registPage";
    }

    @ResponseBody
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public CommonResp regist(@RequestBody @Valid RegisterVo registerVo,BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return failureResp().setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        Register register = new Register();
        BeanUtils.copyProperties(registerVo,register);
        Date curDate = new Date();
        register.setCreateDate(curDate);
        register.setUpdateDate(curDate);
        register.setStatus(StatusEnum.NORMAL.getVal());
        try {
            registerService.addBean(register);
        } catch (Exception e) {
            logger.error("用户办卡注册错误",e);
            return failureResp().setMessage("系统异常");
        }
        return successResp();
    }

}
