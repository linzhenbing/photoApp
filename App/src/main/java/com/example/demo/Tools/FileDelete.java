package com.example.demo.Tools;

import java.io.File;

public class FileDelete {
    public int delFile(String path) {

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
