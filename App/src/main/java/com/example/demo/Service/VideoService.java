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

    public void deleteById(int id) {
        videoDao.deleteById(id);
    }

    public Video getRecordById(int id) {
        return videoDao.getRecordById(id);
    }

    public List<Video> getListByName(String name) {
        return videoDao.getListByName(name);
    }

    public void updateVideoWithoutFileById(int id, int type, String remark,String createtime) {
        videoDao.updateVideoWithoutFileById(id,type,remark,createtime);
    }

    public void updateVideoWhithFileById(int id, String fileName, int type, String remark, String createtime) {
        videoDao.updateVideoWhithFileById(id, fileName, type, remark, createtime);
    }
}
