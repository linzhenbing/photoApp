package com.example.demo.Service;


import com.example.demo.Dao.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired
    private VideoDao videoDao;
}
