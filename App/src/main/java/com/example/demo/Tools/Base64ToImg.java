package com.example.demo.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64ToImg {
    @Autowired
    private ParamsConfig paramsConfig;
    public  String base64ToImg(String base64Date){
        String[] d = base64Date.split(";base64,");
        String dataPrix = "";
        String data = "";
        if(d!=null && d.length == 2){
            dataPrix = d[0];//获取到的前缀
            String sb[] = dataPrix.split("/");
            dataPrix = "."+sb[1];
            data = d[1];//获取到的图片内容
            System.out.println(dataPrix);
        }
        byte[] bytes = Base64Utils.decodeFromString(data);
        for(int i = 0; i<bytes.length; i++){
            if(bytes[i]<0){
                bytes[i] += 256;
            }
        }
        String fileName = GetTimeStamp.getTimeTamp();
//        String imgFilePath = System.getProperty("user.dir");
//        System.out.println(imgFilePath);
        //imgFilePath = imgFilePath + "/../webapps/assets/photo/" + filename;
        /*改这个路径*/
        String imgFilePath =  paramsConfig.getPhotoUploadPath() + fileName + dataPrix;
        System.out.println(imgFilePath);
        try{
            OutputStream outputStream = new FileOutputStream(imgFilePath);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName+dataPrix;
    }
}
