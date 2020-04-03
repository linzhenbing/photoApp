package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserDao userDao;


    public List<User> getUser() {
        return userDao.getUser();
    }

    public User getUserByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    public void registered(User user) {
        userDao.registered(user);
    }

    public User login(User user) {
        return userDao.login(user);
    }

    public void forbidUser(String userName) {
        userDao.forbidUser(userName);
    }

    public void enableUser(String userName) {
        userDao.enableUser(userName);
    }

    public void deleteUser(String userName) {
        userDao.deleteUser(userName);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
