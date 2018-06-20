package com.zw.rule.activity.service.impl;

import com.zw.UploadFile;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.base.util.StringUtils;
import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.mapper.activity.ActivityMapper;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Value("${byx.img.path}")
    private String imgPath;

    @Value("${byx.img.banner}")
    private String bannerImg;
    @Resource
    private ActivityMapper activityMapper;


    /**********************************************碧友信*******************************************************/

    /**
     * 获取Banner列表
     * @author 仙海峰
     * @param map
     * @return
     */
    @Override
    public List<Activity> getList(Map map) {
        return activityMapper.getList(map);
    }

    /**
     * 修改Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    @Override
    public int updateActivity(Activity activity){
        return activityMapper.updateActivity(activity);
    }

    /**
     * 添加Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    @Override
    public int insertActivity(Activity activity){
        return activityMapper.insertActivity(activity);
    }

    /**
     * 删除Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    @Override
    public int delActivity(String id){
        return activityMapper.delActivity(id);
    }

    /**
     * 查看Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    @Override
    public Activity lookActivity(String id){
        return activityMapper.lookActivity(id);
    }

    /**
     * 修改Banner状态（上架/下架）
     * @author 仙海峰
     * @param param
     * @return
     */
    @Override
    public int updateState(Map param){
        return activityMapper.updateState(param);
    }

    /**
     * 上传图片
     * @author 仙海峰
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public List uploadaBannerImage(HttpServletRequest request) throws Exception {
        /**
         * 韩梅生
         */
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        multipartFile.transferTo(new File(""));
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));

        File file = (File) multipartFile;
//        String id = GeneratePrimaryKeyUtils.getUUIDKey();//新的文件名
//        //获取根目录
//        //String root = request.getSession().getServletContext().getRealPath("/");
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        String currentDateStr = format.format(new Date());
//        String newFilePath = imgPath + File.separator + bannerImg + currentDateStr;//文件保存路径url
//        Map<String, Object> allMap = UploadFile.getFile(request, newFilePath, id);
//        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
//        if (!list.isEmpty()) {
//            Map<String, Object> fileMap = list.get(0);
//            fileName = bannerImg + currentDateStr+"/"+ fileMap.get("Name").toString();
//        }
        List imageList = new ArrayList();
        String fileFormat= fileName.substring(fileName.indexOf(".") + 1);

        if ("png".equals(fileFormat) || "jpg".equals(fileFormat) || "jpeg".equals(fileFormat) || "gif".equals(fileFormat) || "".equals(fileFormat)){
            BufferedImage sourceImg= ImageIO.read(file);
            Integer imgWidth= sourceImg.getWidth();//750px
            Integer imgHeight=sourceImg.getHeight();//380px
            if (imgWidth==750 && imgHeight ==380){
                imageList.add(fileName);
            }else {
                //图片大小不符合要求
                imageList.add("-1");
            }
        }else {
            //图片格式不符合要求
            imageList.add("-2");
        }

        return imageList;
    }

}
