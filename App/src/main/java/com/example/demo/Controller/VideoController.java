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
            newFileName += "/"+oldFileName;
            String createtime = GetTimeStamp.getTimeTamp();
            videoService.upLoadVideo(name,newFileName,createtime,type,remark);


        }else {
            return new JsonResult<>(200,"该学生已存在视频",null);
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
        if(num == 1){
            return new JsonResult<>(200,"删除成功",null);
        }else {
            return new JsonResult<>(200,"删除失败",null);
        }

    }

}
