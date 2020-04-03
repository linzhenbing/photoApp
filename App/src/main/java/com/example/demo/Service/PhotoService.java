package com.example.demo.Service;

import com.example.demo.Dao.PhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {


    @Autowired
    private  PhotoDao photoDao;
    public void upLoadImg(String name, String img1, String img2, String img3, String music, String createtime,String description) {
        photoDao.upLoadImg(name,img1,img2,img3,music,createtime,description);
    }
}
