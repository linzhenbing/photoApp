package com.example.demo.Dao;

import com.example.demo.Entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoDao {
    List<Video> getVideoList();

    Video getVideoByName(@Param("name") String name);

    void upLoadVideo(@Param("name") String name,
                     @Param("newFileName") String newFileName,
                     @Param("createtime") String createtime,
                     @Param("type") int type,
                     @Param("remark") String remark);

    Video getById(@Param("id") int id);

    void deleteById(@Param("id") int id);
}
