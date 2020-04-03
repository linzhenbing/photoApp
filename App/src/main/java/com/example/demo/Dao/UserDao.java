package com.example.demo.Dao;

import com.example.demo.Entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getUser();

    User getUserByUserName(@Param("username") String username);

    void registered(@Param("user") User user);

    User login(@Param("user") User user);

    void forbidUser(@Param("username") String userName);

    void enableUser(@Param("username") String userName);

    void deleteUser(@Param("username") String userName);

    void updateUser(@Param("user") User user);

    List<User> sercahUser(@Param("username") String username);
}
