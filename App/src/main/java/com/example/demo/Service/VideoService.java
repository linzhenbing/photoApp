package com.example.demo.Service;


import com.example.demo.Dao.VideoDao;
import com.example.demo.Entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoDao videoDao;

    public List<Video> getVideoList() {
        return videoDao.getVideoList();
    }

    public Video getVideoByName(String name) {
        return videoDao.getVideoByName(name);
    }

    public void upLoadVideo(String name, String newFileName, String createtime, int type, String remark) {
        videoDao.upLoadVideo(name, newFileName, createtime, type, remark);
    }

    public Video getById(int id) {
        return videoDao.getById(id);
    }
}
