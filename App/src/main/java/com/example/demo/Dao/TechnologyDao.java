package com.example.demo.Dao;

import com.example.demo.Entity.Technology;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyDao {
    List<Technology> getTechList();

    void addTech(@Param("name") String name,
                 @Param("fileName") String fileName,
                 @Param("form") String form,
                 @Param("createtime") String createtime,
                 @Param("type") int type,
                 @Param("remark") String remark);

    Technology getById(@Param("id") int id);

    void deleteById(@Param("id") int id);

    List<Technology> getListByName(@Param("name") String name);

    void updateVideoWhithFileById(@Param("id") int id,
                                  @Param("fileName")String fileName,
                                  @Param("type") int type,
                                  @Param("form") String form,
                                  @Param("remark")String remark,
                                  @Param("createtime")String createtime);

    void updateVideoWithoutFileById(@Param("id") int id,
                                    @Param("type") int type,
                                    @Param("form") String form,
                                    @Param("remark") String remark,
                                    @Param("createtime") String createtime);
}
