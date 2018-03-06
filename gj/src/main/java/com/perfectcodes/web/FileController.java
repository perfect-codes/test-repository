package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
import com.perfectcodes.common.ObjTypeEnum;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.component.PropConfig;
import com.perfectcodes.domain.Affix;
import com.perfectcodes.service.AffixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

@RestController
public class FileController extends BaseController {

    @Autowired
    private PropConfig propConfig;
    @Autowired
    private AffixService affixService;

    /**
     * @author xpf
     * @description 二维码图片上传
     * @date 2018/2/28 12:25
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public CommonResp upload(HttpServletRequest request, HttpSession session) {
        MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
        MultipartFile mfile = mrequest.getFile("file");
        File folder = new File(propConfig.getAffixDir());
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        String originName = mfile.getOriginalFilename();
        String subfix = originName.substring(originName.lastIndexOf('.'));
        String name = session.getId() + subfix;
        StringBuffer path = new StringBuffer(folder.getAbsolutePath());
        path.append(File.separator)
                .append("image")
                .append(File.separator)
                .append(calendar.get(Calendar.YEAR))
                .append(File.separator)
                .append(calendar.get(Calendar.MONTH)+1)
                .append(File.separator)
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(File.separator)
                .append(name);
        File file = new File(path.toString());
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        try {
            OutputStream os = new FileOutputStream(file);
            FileCopyUtils.copy(mfile.getInputStream(), os);
            Affix affix = new Affix()
                    .setOriginName(originName)
                    .setName(name)
                    .setPath(path.toString())
                    .setUrl(path.substring(folder.getAbsolutePath().length()))
                    .setCreateDate(new Date())
                    .setObjType(ObjTypeEnum.QR_IMAGE.getVal())
                    .setStatus(StatusEnum.NORMAL.getVal());
            affixService.addBean(affix);
            return successResp().setData(affix);
        } catch (Exception e) {
            logger.error("二维码图片上传错误", e);
        }
        return failureResp();
    }

}
