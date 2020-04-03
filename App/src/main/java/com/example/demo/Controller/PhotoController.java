package com.example.demo.Controller;


import com.example.demo.Dao.PhotoDao;
import com.example.demo.Service.PhotoService;
import com.example.demo.Tools.Base64ToImg;
import com.example.demo.Tools.FileUpLoad;
import com.example.demo.Tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/photo")
@CrossOrigin
public class PhotoController {

    @Autowired
    private  PhotoService photoService;
    /*
    * 上传接口
    *
    * */
    @RequestMapping("/upLoadImg")
    public JsonResult<PhotoDao> upLoadImg(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "img1") String img1,
                                          @RequestParam(value = "img2",required = false) String img2,
                                          @RequestParam(value = "img3",required = false) String img3,
                                          @RequestParam(value = "music") MultipartFile music,
                                          @RequestParam(value = "description",required = false)String description){
        String ImgName1, ImgName2 = null, ImgName3 = null;
        ImgName1 = Base64ToImg.base64ToImg(img1);
        if(img2 != null){
            ImgName2 = Base64ToImg.base64ToImg(img2);
            if(img3 != null){
                ImgName3 = Base64ToImg.base64ToImg(img3);
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createTime = df.format(date);
        String musicName = FileUpLoad.fileUpLoad(music);
        photoService.upLoadImg(name,ImgName1,ImgName2,ImgName3,musicName,createTime,description);
        return new JsonResult<>(200,null,null);
    }
}
