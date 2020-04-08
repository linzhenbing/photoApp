package com.example.demo.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpLoad {
    @Autowired
    private ParamsConfig paramsConfig;
    public  String fileUpLoad(MultipartFile file){

        // 获取文件后缀名
        String name = file.getOriginalFilename();
        String suffixName = name.substring(name.lastIndexOf("."));
        String fileName = getTimeTamp.getTimeTamp();
        //String filePath = this.getClass().getResource("/").toURI().getPath()+"static/";
        // 用时间戳给文件命名
        /*改这个路径*/
        String filePath = paramsConfig.getMusicUploadPath() + fileName + suffixName;
        File dest = new File(filePath);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName+suffixName+"/"+name;
    }
}
