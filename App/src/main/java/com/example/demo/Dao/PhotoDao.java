package com.example.demo.Dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDao {
    void upLoadImg(@Param("name") String name,
                   @Param("img1") String img1,
                   @Param("img2") String img2,
                   @Param("img3") String img3,
                   @Param("music") String music,
                   @Param("createtime")String createtime,
                   @Param("description") String description);
}
