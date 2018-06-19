package com.flj.miracle.base.web;

import com.flj.miracle.base.domain.Dictionary;
import com.flj.miracle.base.service.DictionaryService;
import com.flj.miracle.base.web.model.DictionaryModel;
import com.flj.miracle.core.common.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Dictionary的控制层
 *
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Controller
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 添加
     */
    @ResponseBody
    @RequestMapping(value = {"/dictionary"}, method = {RequestMethod.POST})
    public CommonResp add(@ModelAttribute DictionaryModel model) {
        Dictionary bean;
        Date now = new Date();
        if (isAdd(model)) {
            try{
                bean = new Dictionary();
                bindBean(model, bean);
                bean.setCreateDate(now);
                bean.setLevel(2);
                //查询父节点
                Dictionary parent = dictionaryService.findByName(model.getParentName());
                Optional.ofNullable(parent).ifPresent(p -> bean.setParentId(parent.getId()));
                this.dictionaryService.addBean(bean);
                return successResp();
            }catch (Exception e){
                logger.error("Dictionary添加错误", e);
            }
        } else if (isDel(model)){
            try {
                this.dictionaryService.deleteBean(model.getId());
                return successResp();
            }catch (Exception e){
                logger.error("Dictionary删除错误", e);
            }
        } else {
            try {
                bean = this.dictionaryService.findById(model.getId());
                bean.setName(model.getName());
                bean.setCode(model.getCode());
                bean.setStatus(model.getStatus());
                bean.setUpdateDate(now);
                this.dictionaryService.updateBean(bean);
                return successResp();
            }catch (Exception e){
                logger.error("Dictionary修改错误", e);
            }
        }
        return failureResp();
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/dictionary/{id}"}, method = {RequestMethod.DELETE})
    public String delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.dictionaryService.deleteBean(id);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Dictionary删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = {"/dictionary/deletes"}, method = {RequestMethod.POST})
    public String deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.dictionaryService.deleteBeans(cs);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Customer删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = {"/dictionary/{id}"}, method = {RequestMethod.PUT})
    public String update(@PathVariable Long id, @RequestBody DictionaryModel model) {
        try {
            Dictionary bean = this.dictionaryService.findById(id);
            this.bindBean(model, bean);
            this.dictionaryService.updateBean(bean);
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("Dictionary修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
    @RequestMapping(value = {"/dictionary/{id}"}, method = {RequestMethod.GET})
    public Dictionary findOne(@PathVariable Long id) {
        try {
            Dictionary bean = this.dictionaryService.findById(id);
            return bean;
        } catch (Exception e) {
            logger.error("Dictionary单个查询错误", e);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @ResponseBody
    @RequestMapping(value = {"/dictionaries"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Page<Dictionary> findByPage(@ModelAttribute DictionaryModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<Dictionary> beans = this.dictionaryService.findByPagination(model, pageable);
            return beans;
        } catch (Exception e) {
            logger.error("Dictionary分页查询错误", e);
        }
        return null;
    }

    @GetMapping(path = "dictionariesView")
    public String dictionarysView() {
        return "dictionarylist";
    }

    @ResponseBody
    @RequestMapping(value = {"/dictionaries2"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,Object> findByPage2(@ModelAttribute DictionaryModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String,Object> result = new HashMap<>(1);
            result.put("rows",this.dictionaryService.findAllSecondLevel());
            return result;
        } catch (Exception e) {
            logger.error("Dictionary分页查询错误", e);
        }
        return new HashMap<>(0);
    }
}