package com.ruubypay.web;

import com.ruubypay.common.CommonResp;
import com.ruubypay.domain.Dictionary;
import com.ruubypay.service.DictionaryService;
import com.ruubypay.web.dto.DictionaryAddDTO;
import com.ruubypay.web.dto.DictionaryUpdateDTO;
import com.ruubypay.web.model.DictionaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 添加
     */
    @RequestMapping(value = {"/dictionary"}, method = {RequestMethod.POST})
    public CommonResp add(@ModelAttribute DictionaryAddDTO dto) {
        Date now = new Date();
        try{
            //查询父节点
//            Dictionary parent = dictionaryService.findByName(dto);
//            Optional.ofNullable(parent).ifPresent(p -> bean.setParentId(parent.getId()));
            this.dictionaryService.addBean(dto);
            return success();
        }catch (Exception e){
            logger.error("Dictionary添加错误", e);
        }
        return failure();
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/dictionary/{id}"}, method = {RequestMethod.DELETE})
    public CommonResp delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
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
    public CommonResp deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
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
    @RequestMapping(value = {"/dictionary"}, method = {RequestMethod.PUT})
    public CommonResp update(@PathVariable Long id, @RequestBody DictionaryUpdateDTO dto) {
        try {
            this.dictionaryService.updateBean(dto);
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("Dictionary修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
//    @PreAuthorize("hasAuthority('DICTIONARY:DELETE')")
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