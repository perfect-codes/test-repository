package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
import com.perfectcodes.common.GeneralException;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.component.PropConfig;
import com.perfectcodes.domain.Bank;
import com.perfectcodes.domain.Banner;
import com.perfectcodes.domain.User;
import com.perfectcodes.service.BankService;
import com.perfectcodes.service.BannerService;
import com.perfectcodes.service.UserService;
import com.perfectcodes.web.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BankService bankService;

    /**
     * @author xpf
     * @description 跳转用户信息填写页面
     * @date 2018/3/4 20:23
     * @param bankCode
     * @param seller
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/goApply",method = {RequestMethod.GET})
    public String pageBankUser(@RequestParam(required = false) String bankCode,@RequestParam(required = false) String seller,Model model) throws IOException {
        model.addAttribute("bankCode",bankCode);
        model.addAttribute("seller",seller);
        Banner banner = new Banner();
        banner.setType(2);
        banner.setStatus(StatusEnum.NORMAL.getVal());
        try {
            List<Banner> banners = bannerService.findAll(banner);
            model.addAttribute("banner",banners.get(0));//默认取该类型第一张
        } catch (Exception e) {
            logger.error("用户信息填写页banner加载错误",e);
        }
        return "bankuser";
    }

    @ResponseBody
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public CommonResp supply(@RequestBody @Valid UserVo userVo, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return failureResp().setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        Date curDate = new Date();
        user.setCreateDate(curDate);
        user.setUpdateDate(curDate);
        user.setStatus(StatusEnum.NORMAL.getVal());
        CommonResp successResp = successResp();
        try {
            userService.addBean(user);
            Bank bank = bankService.getBeanByCode(userVo.getBankCode());
            if (bank!=null){
                successResp.setMessage(bank.getHtmlUrl());
            }
        } catch(GeneralException e){
            logger.warn(e.getMessage());
            return failureResp().setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("用户申请注册错误",e);
            return failureResp();
        }
        return successResp;
    }

}
