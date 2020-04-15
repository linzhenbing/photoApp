package com.example.demo.Controller;

import com.example.demo.Entity.Technology;
import com.example.demo.Service.TechnologyService;
import com.example.demo.Tools.FileDelete;
import com.example.demo.Tools.JsonResult;
import com.example.demo.Tools.ParamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/technology")
@CrossOrigin
public class TechnologyController {
    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private ParamsConfig paramsConfig;

    /**
     * 获取作品列表
     * @return
     */
    @RequestMapping("/getTechList")
    public JsonResult<List<Technology>> getTechList(){
        List<Technology> list = technologyService.getTechList();
        return new JsonResult<>(200,"",list);
    }


    /**
     * 新增作品接口
     * @param name
     * @param video
     * @param form
     * @param remark
     * @param type
     * @return
     */
    @RequestMapping("/addTech")
    public JsonResult<Technology> addTech(@RequestParam("name")String name,
                                          @RequestParam("video")MultipartFile video,
                                          @RequestParam("form")String form,
                                          @RequestParam(value = "remark", defaultValue = "")String remark,
                                          @RequestParam("type")int type){
        String oldFileName = video.getOriginalFilename();
        String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+suffixName;
        String filePath = paramsConfig.getTechnologyUploadPath() + newFileName;
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
        technologyService.addTech(name,fileName,form,createtime,type,remark);
        return new JsonResult<>(200,"",null);
    }

    /**
     * 根据id更新一条记录
     * @param id
     * @param video
     * @param type
     * @param form
     * @param remark
     * @return
     */
    @RequestMapping("/updateTech")
    public JsonResult<Technology> updateTech(@RequestParam("id")int id,
                                             @RequestParam(value = "video",required = false)MultipartFile video,
                                             @RequestParam("type")int type,
                                             @RequestParam("form")String form,
                                             @RequestParam("remark")String remark){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createtime = df.format(date);
        if(video != null){
            Technology technology = technologyService.getById(id);
            String name = technology.getVideo();
            String[] name1 = name.split("/");
            int num = FileDelete.delFile(paramsConfig.getTechnologyUploadPath()+name1[0]);
            /*对新文件进行上传以及保存生成文件名倒数据库*/
            String oldFileName = video.getOriginalFilename();
            String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString()+suffixName;
            String filePath = paramsConfig.getTechnologyUploadPath() + newFileName;
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
            technologyService.updateVideoWhithFileById(id,fileName,type,form,remark,createtime);
        }else {
            technologyService.updateVideoWithoutFileById(id,type,form,remark,createtime);
        }
        return new JsonResult<>(200,"",null);
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public JsonResult<Technology> deleteById(@RequestParam("id")int id){
        Technology technology = technologyService.getById(id);
        String videoName = technology.getName();
        String[] name = videoName.split("/");
        int num = FileDelete.delFile(paramsConfig.getVideoUploadPath()+name[0]);
        technologyService.deleteById(id);
        if(num == 1){
            return new JsonResult<>(200,"删除成功",null);
        }else {
            return new JsonResult<>(200,"删除失败",null);
        }
    }

    /**
     * 根据id返回一条记录
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public JsonResult<Technology> getById(@RequestParam("id") int id){
        Technology technology = technologyService.getById(id);
        return new JsonResult<>(200,"",technology);
    }


    /**
     * 根据名字搜索记录
     * @param name
     * @return
     */
    @RequestMapping("/getListByName")
    public JsonResult<List<Technology>> getListByName(@RequestParam("name")String name){
        List<Technology> list = technologyService.getListByName(name);
        return new JsonResult<>(200,"",list);
    }

}
