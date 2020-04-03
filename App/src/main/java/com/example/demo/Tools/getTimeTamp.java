package com.example.demo.Tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getTimeTamp {
    public static String getTimeTamp(){
        Date date=new Date();
        //DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        //文件输入流不支持文件命名格式yyyy-MM-dd HH:mm:ss:SS
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSS");
        return format.format(date);
    }
}
