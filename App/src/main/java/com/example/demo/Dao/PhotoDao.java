package com.example.demo.Dao;


import com.example.demo.Entity.Photo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoDao {
    void upLoadImg(@Param("name") String name,
                   @Param("img1") String img1,
                   @Param("img2") String img2,
                   @Param("img3") String img3,
                   @Param("music") String music,
                   @Param("createtime")String createtime,
                   @Param("description") String description,
                   @Param("type")int type);

    Photo searchByName(@Param("name") String name);

    List<Photo> getImgList();

    void deleteMsg(@Param("name") String name);

    Photo getRecordById(@Param("id") int id);
}
