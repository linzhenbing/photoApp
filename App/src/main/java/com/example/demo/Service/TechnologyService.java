package com.example.demo.Service;

import com.example.demo.Dao.TechnologyDao;
import com.example.demo.Entity.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
    @Autowired
    private TechnologyDao technologyDao;
    public List<Technology> getTechList() {
        return technologyDao.getTechList();
    }

    public void addTech(String name, String fileName, String form, String createtime, int type, String remark) {
        technologyDao.addTech(name,fileName,form,createtime,type,remark);
    }

    public Technology getById(int id) {
        return technologyDao.getById(id);
    }

    public void deleteById(int id) {
        technologyDao.deleteById(id);
    }

    public List<Technology> getListByName(String name) {
        return technologyDao.getListByName(name);
    }

    public void updateVideoWhithFileById(int id, String fileName, int type, String form, String remark, String createtime) {
        technologyDao.updateVideoWhithFileById(id, fileName, type, form, remark, createtime);
    }

    public void updateVideoWithoutFileById(int id, int type, String form, String remark, String createtime) {
        technologyDao.updateVideoWithoutFileById(id,type,form,remark,createtime);
    }
}
