package com.example.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Tools.JsonResult;
import com.example.demo.Tools.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private  UserService userService;


    @PostMapping("/info")
    public JsonResult<JSONObject> info(@RequestParam String token){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roles","admin");
        jsonObject.put("introduction","hello");
        jsonObject.put("avatar","he2llo");
        jsonObject.put("name","how");
        return new JsonResult<>(200,"登录成功",jsonObject);
    }

    /***
     * 返回所有用户接口
     * @return
     * List<User>
     */
    @GetMapping(value = "/getUser")
    public JsonResult<List<User>> getUser(){
        List<User> list = userService.getUser();

        if(list !=null){
            return new JsonResult<>(200,"",list);
        }
        return new JsonResult<>(200,"查询数据为空",null);
}
    public User getByUserName(String username){
        return userService.getUserByUserName(username);
    }

    /**
     * 注册接口
     * @param user(username,password,type)
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/registered")
    public JsonResult<User> registered(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(getByUserName(user.getUsername())!=null){
            return new JsonResult<>(200,"用户名重复",null);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        user.setCreatetime(df.format(date));
        user.setPassword(Md5.changeToMd5(user.getPassword()));
        user.setState(1);
        userService.registered(user);
        return new JsonResult<>(200,"插入成功",null);
    }

    /**
     * 登录接口
     * @param user(username,password,type)
     * @return JsonResult
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/login")
    public JsonResult<JSONObject> login(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(Md5.changeToMd5(user.getPassword()));
        User result = userService.login(user);
        if(result == null){
            return new JsonResult<>(200,"用户名或密码错误",null);
        }else if(result.getState() == 0){
            return new JsonResult<>(200,"用户被禁用",null);
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token","admin-token");
            jsonObject.put("username",result.getUsername());
            jsonObject.put("type",result.getType());
            return new JsonResult<>(200,"登录成功",jsonObject);
        }
    }

    /**
     * 用户禁用接口
     * @param userName
     * @return JsonResult
     */
    @PostMapping("/forbidUser")
    public JsonResult<User> forbidUser(@RequestParam String userName){
        userService.forbidUser(userName);
        return new JsonResult<>(200,"",null);
    }

    /**
     * 用户激活接口
     * @param userName
     * @return
     */
    @PostMapping("/enableUser")
    public JsonResult<User> enableUser(@RequestParam String userName){
        userService.enableUser(userName);
        return new JsonResult<>(200,"",null);
    }

    /**
     * 用户删除接口
     * @param userName
     * @return
     */
    @DeleteMapping("/deleteUser")
    public JsonResult<User> deleteUser(@RequestParam String userName){
        userService.deleteUser(userName);
        return new JsonResult<>(200,"",null);
    }

    /**
     * 更新用户（包括用户自己更改密码，也包括管理员修改用户）
     * @param user（username，password,type）
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateUser")
    public JsonResult<User> updateUser(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(Md5.changeToMd5(user.getPassword()));
        userService.updateUser(user);
        return new JsonResult<>(200,"",null);
    }

    /**
     * 用户查询接口
     * @param username
     * @return
     */
    @PostMapping("/searchUser")
    public JsonResult<List<User>> searchUser(@RequestParam String username){
        List<User> users = userService.searchUser(username);
        return new JsonResult<>(200,"",users);
    }

}
