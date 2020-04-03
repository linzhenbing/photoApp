package com.example.demo.Tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpLoad {
    public static String fileUpLoad(MultipartFile file){
        // 获取文件后缀名
        String name = file.getOriginalFilename();
        String suffixName = name.substring(name.lastIndexOf("."));
        String fileName = getTimeTamp.getTimeTamp();
        //String filePath = this.getClass().getResource("/").toURI().getPath()+"static/";
        // 用时间戳给文件命名
        /*改这个路径*/
        String filePath = System.getProperty("user.dir")+ "/webapps/assets/photo/" + fileName + suffixName;;
        File dest = new File(filePath + fileName);
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
        return fileName;
    }
}
