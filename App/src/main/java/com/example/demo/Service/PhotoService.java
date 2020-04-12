package com.example.demo.Service;

import com.example.demo.Dao.PhotoDao;
import com.example.demo.Entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {


    @Autowired
    private  PhotoDao photoDao;
    public void upLoadImg(String name, String img1, String img2, String img3, String music, String createtime,String description,int type) {
        photoDao.upLoadImg(name,img1,img2,img3,music,createtime,description,type);
    }

    public Photo searchByName(String name) {
        return photoDao.searchByName(name);
    }

    public List<Photo> getImgList() {
        return photoDao.getImgList();
    }

    public void deleteMsg(String name) {
        photoDao.deleteMsg(name);
    }

    public Photo getRecordById(int id) {
        return photoDao.getRecordById(id);
    }

    public List<Photo> getListByName(String name) {
        return photoDao.getListByName(name);
    }

    public void updateWithoutFile(int id, String description, int type,String createTime) {
        photoDao.updateWithoutFile(id, description, type, createTime);
    }

    public void updateImg1(int id, String imgName) {
        photoDao.updateImg1(id,imgName);
    }

    public void updateImg2(int id, String imgName) {
        photoDao.updateImg2(id,imgName);
    }

    public void updateImg3(int id, String imgName) {
        photoDao.updateImg3(id,imgName);
    }

    public void updateMusic(int id, String musicName) {
        photoDao.updateMusic(id,musicName);
    }
}
