package com.example.demo.Controller;

import com.example.demo.Entity.Video;
import com.example.demo.Service.VideoService;
import com.example.demo.Tools.FileDelete;
import com.example.demo.Tools.JsonResult;
import com.example.demo.Tools.ParamsConfig;
import com.example.demo.Tools.GetTimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private ParamsConfig paramsConfig;

    @RequestMapping("/test")
    public String test() throws URISyntaxException {
        String s = "123/456";
        String[] s1 = s.split("/");
        return s1[0];
    }

    /***
     * 返回视频列表
     * @return
     */
    @RequestMapping("/getVideoList")
    public JsonResult<List<Video>> getVideoList(){
        List<Video> list = videoService.getVideoList();
        return new JsonResult<>(200,"",list);
    }

    /**
     * 上传视频
     * @param name
     * @param video
     * @param type
     * @param remark
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping("/upLoadVideo")
    public JsonResult<Video> upLoadVideo(@RequestParam("name")String name,
                                         @RequestParam("video")MultipartFile video,
                                         @RequestParam("type")int type,
                                         @RequestParam(value = "remark", defaultValue = "")String remark) throws URISyntaxException {
        Video video1 = videoService.getVideoByName(name);
        if(video1 == null){

            String oldFileName = video.getOriginalFilename();
            String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString()+suffixName;
            String filePath = paramsConfig.getVideoUploadPath() + newFileName;
            File newFile = new File(filePath);
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            try {
                video.transferTo(newFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(newFileName);
            System.out.println(oldFileName);
            String fileName = newFileName + "/"+oldFileName;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String createtime = df.format(date);
            videoService.upLoadVideo(name,fileName,createtime,type,remark);


        }else {
            return new JsonResult<>(200,"该学生已存在视频",null);
        }
        return new JsonResult<>(200,"",null);
    }


    /**
     * 视频更新接口
     * @param id
     * @param video（可选--非必传）
     * @param type
     * @param remark
     * @return
     */
    @RequestMapping("/updateVideo")
    public JsonResult<Video> updateVideo(@RequestParam("id")int id,
                                         @RequestParam(value = "video",required = false)MultipartFile video,
                                         @RequestParam("type") int type,
                                         @RequestParam(value = "remark")String remark){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createtime = df.format(date);
        if(video == null){
            videoService.updateVideoWithoutFileById(id,type,remark,createtime);
        }else {
            /*删除文件*/
            Video video1 = videoService.getById(id);
            String name = video1.getVideo();
            String[] name1 = name.split("/");
            int num = FileDelete.delFile(paramsConfig.getVideoUploadPath()+name1[0]);

            /*对新文件进行上传以及保存生成文件名倒数据库*/
            String oldFileName = video.getOriginalFilename();
            String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString()+suffixName;
            String filePath = paramsConfig.getVideoUploadPath() + newFileName;
            File newFile = new File(filePath);
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            try {
                video.transferTo(newFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = newFileName + "/"+oldFileName;
            videoService.updateVideoWhithFileById(id,fileName,type,remark,createtime);
        }
        return new JsonResult<>(200,"",null);

    }


    /**
     * 根据id删除一条记录以及文件
     * @param id
     * @return
     */
    @RequestMapping("/deleteVideo")
    public JsonResult<Video> deleteVideo(@RequestParam("id")int id){
        Video video = videoService.getById(id);
        String videoName = video.getName();
        String[] name = videoName.split("/");
        int num = FileDelete.delFile(paramsConfig.getVideoUploadPath()+name[0]);
        videoService.deleteById(id);
        if(num == 1){
            return new JsonResult<>(200,"删除成功",null);
        }else {
            return new JsonResult<>(200,"删除失败",null);
        }

    }

    /**
     * 根据id返回一条视频记录
     * @param id
     * @return
     */
    @RequestMapping("/getRecordById")
    public JsonResult<Video> getRecordById(@RequestParam("id") int id){
        Video video = videoService.getRecordById(id);
        return new JsonResult<>(200,"",video);
    }


    /**
     * 根据名字搜索视频
     * @param name
     * @return
     */
    @RequestMapping("/getListByName")
    private JsonResult<List<Video>> getListByName(@RequestParam("name")String name){
        List<Video> list = videoService.getListByName(name);
        return new JsonResult<>(200,"",list);
    }
}
