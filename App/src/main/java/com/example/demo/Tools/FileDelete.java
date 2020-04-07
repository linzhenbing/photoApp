package com.example.demo.Tools;

import java.io.File;

public class FileDelete {

    /**
     * 删除文件接口
     * @param path
     * @return
     * 1---删除成功
     * 0---删除失败
     * -1---文件不存在
     */
    public static int delFile(String path) {

        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }

    }

}
