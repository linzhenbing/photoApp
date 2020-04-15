package com.example.demo.Tools;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamsConfig {

    @Value("${upload-photo-path}")
    private String photoUploadPath;
    public String getPhotoUploadPath() {
        return photoUploadPath;
    }

    @Value("${upload-music-path}")
    private String musicUploadPath;
    public String getMusicUploadPath() {
        return musicUploadPath;
    }

    @Value("${upload-video-path}")
    private String videoUploadPath;
    public String getVideoUploadPath() {
        return videoUploadPath;
    }

    @Value("${upload-technology-path}")
    private String technologyUploadPath;
    public String getTechnologyUploadPath() {
        return technologyUploadPath;
    }
}
