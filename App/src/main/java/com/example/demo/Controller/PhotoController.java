package com.example.demo.Controller;


import com.example.demo.Dao.PhotoDao;
import com.example.demo.Entity.Photo;
import com.example.demo.Service.PhotoService;
import com.example.demo.Tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/photo")
@CrossOrigin
public class PhotoController {

    @Autowired
    private  PhotoService photoService;
    @Autowired
    private ParamsConfig paramsConfig;

    /**
     * 获取图片路径接口
     * @return
     */
    @RequestMapping("/getPhotoUploadPath")
    public String getPhotoUploadPath(){
        return "http://106.15.88.217:8080/static/photo/";
    }


    /**
     * 获取音频文件路径接口
     * @return
     */
    @RequestMapping("/getMusicUploadPath")
    public String getMusicUploadPath(){
        return "http://106.15.88.217:8080/static/music/";
    }


    /**
     * 获取视频图片路径接口
     * @return
     */
    @RequestMapping("/getVideoUploadPath")
    public String getVideoUploadPath(){
        return "http://106.15.88.217:8080/static/video/";
    }


    /**
     * 返回科技作品视频路径接口
     * @return
     */
    @RequestMapping("/getTechnologyUploadPath")
    public String getTechnologyUploadPath(){
        return "http://106.15.88.217:8080/static/technology/";
    }





    /***
     * 获取相册列表
     * @return
     */
    @RequestMapping("/getImgList")
    public  JsonResult<List<Photo>> getImgList(){
        List<Photo> list = photoService.getImgList();
        return new JsonResult<>(200,"http://106.15.88.217:8080/static/photo/",list);
    }


    /**
     * 图片上传接口
     * @param name
     * @param img1
     * @param img2
     * @param img3
     * @param music
     * @param description
     * @param type
     * @return
     */
    @RequestMapping("/upLoadImg")
    public JsonResult<PhotoDao> upLoadImg(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "img1") String img1,
                                          @RequestParam(value = "img2",required = false) String img2,
                                          @RequestParam(value = "img3",required = false) String img3,
                                          @RequestParam(value = "music") MultipartFile music,
                                          @RequestParam(value = "description",required = false)String description,
                                          @RequestParam(value = "type")int type){
        if(photoService.searchByName(name)!=null){
            return new JsonResult<>(200,"学生已存在",null);
        }
        String ImgName1, ImgName2 = null, ImgName3 = null;
        Base64ToImg base = new Base64ToImg();
        ImgName1 = base.base64ToImg(img1);
        if(img2 != null){
            ImgName2 = base.base64ToImg(img2);
            if(img3 != null){
                ImgName3 = base.base64ToImg(img3);
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createTime = df.format(date);
        FileUpLoad file = new FileUpLoad();
        String musicName = file.fileUpLoad(music);
        photoService.upLoadImg(name,ImgName1,ImgName2,ImgName3,musicName,createTime,description,type);
        return new JsonResult<>(200,null,null);
    }

    /***
     * 相册管理删除一条记录
     * @param name
     * @return
     */
    @RequestMapping("/deleteMsg")
    public JsonResult<Photo> deleteMsg(@RequestParam(value = "name")String name){
        Photo photo = photoService.searchByName(name);
        /*只要改下面这两个根路径*/
        String rootImgPath =  paramsConfig.getPhotoUploadPath();
        String rootMusicPath =  paramsConfig.getMusicUploadPath();
        /*图片路径1*/
        String filePath = rootImgPath + photo.getImg1();
        int num = 0;
        num = FileDelete.delFile(filePath);
        if(photo.getImg2()!=null){
            /*图片路径2*/
            filePath = rootImgPath + photo.getImg2();
            num = FileDelete.delFile(filePath);
            if(photo.getImg3()!=null){
                /*图片路径3*/
                filePath = rootImgPath + photo.getImg3();
                num = FileDelete.delFile(filePath);
            }
        }
        String[] musicName = photo.getMusic().split("/");
        filePath = rootMusicPath + musicName[0];
        num = FileDelete.delFile(filePath);
        photoService.deleteMsg(name);
        return new JsonResult<>(200,"",null);
    }


    /**
     * 根据id返回一条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/getRecordById")
    public JsonResult<Photo> getRecordById(@RequestParam("id")int id){
        Photo photo = photoService.getRecordById(id);
        return new JsonResult<>(200,"http://106.15.88.217:8080/static/photo/",photo);
    }


    /**
     * 根据name搜索照片记录
     * @param name
     * @return
     */
    @RequestMapping("/getListByName")
    public JsonResult<List<Photo>> getListByName(@RequestParam("name")String name){
        List<Photo> list = photoService.getListByName(name);
        return new JsonResult<>(200,"",list);
    }


    /**
     * 相册更新接口
     * @param id
     * @param img1（非必传）
     * @param img2（非必传）
     * @param img3（非必传）
     * @param music（非必传）
     * @param description
     * @param type
     * @return
     */
    @RequestMapping("/updatePhoto")
    public JsonResult<Photo> updatePhoto(@RequestParam("id")int id,
                                         @RequestParam(value = "img1", required = false)String img1,
                                         @RequestParam(value = "img2", required = false)String img2,
                                         @RequestParam(value = "img3", required = false)String img3,
                                         @RequestParam(value = "music", required = false)MultipartFile music,
                                         @RequestParam(value = "description")String description,
                                         @RequestParam(value = "type")int type
                                         ){
        Photo photo = photoService.getRecordById(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createTime = df.format(date);
        photoService.updateWithoutFile(id,description,type,createTime);
        Base64ToImg base64ToImg = new Base64ToImg();
        String ImgName;
        int num;
        if(img1 != null){
            num = FileDelete.delFile(paramsConfig.getPhotoUploadPath()+photo.getImg1());
            ImgName = base64ToImg.base64ToImg(img1);
            photoService.updateImg1(id,ImgName);
        }
        if(img2 != null){
            num = FileDelete.delFile(paramsConfig.getPhotoUploadPath()+photo.getImg2());
            ImgName = base64ToImg.base64ToImg(img2);
            photoService.updateImg2(id,ImgName);
        }
        if(img3 != null){
            num = FileDelete.delFile(paramsConfig.getPhotoUploadPath()+photo.getImg3());
            ImgName = base64ToImg.base64ToImg(img3);
            photoService.updateImg3(id,ImgName);
        }

        if(music != null){
            num = FileDelete.delFile(paramsConfig.getMusicUploadPath()+photo.getMusic().split("/")[0]);
            FileUpLoad file = new FileUpLoad();
            String musicName = file.fileUpLoad(music);
            photoService.updateMusic(id,musicName);
        }
        return new JsonResult<>(200,"",null);
    }



}
