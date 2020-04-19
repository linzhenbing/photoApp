package com.example.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Tools.JsonResult;
import com.example.demo.Tools.Md5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender javaMailSender;


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
     * @param user(username,password,type,mail)
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
        user.setState(0);
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
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            SecurityUtils.getSubject().login(token);
            SecurityUtils.getSubject().getSession().setTimeout(1000*60*60*24);
        }catch (UnknownAccountException e){
            return new JsonResult<>(200,"用户名或密码错误",null);
        }catch (IncorrectCredentialsException e){
            return new JsonResult<>(200,"用户名或密码错误",null);
        }

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

    /***
     * 更新用户接口
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/updateUser")
    public JsonResult<User> updateUser(@RequestParam(value = "userName")String userName,
                                       @RequestParam(value = "oldPassword")String oldPassword,
                                       @RequestParam(value = "newPassword")String newPassword,
                                       @RequestParam(value = "mail", defaultValue = "")String mail) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(Md5.changeToMd5(oldPassword));
        if (userService.judge(user) == null){
            return new JsonResult<>(200,"原始密码错误",null);
        }else {
            user.setPassword(Md5.changeToMd5(newPassword));
            userService.updateUser(user);
        }
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


    /**
     * 更新邮箱接口
     * @param userName
     * @param mail
     * @return
     */
    @RequestMapping("/updateMail")
    public JsonResult<User> updateMail(@RequestParam("userName")String userName,
                                       @RequestParam("mail")String mail){
        userService.updateMail(userName,mail);
        return new JsonResult<>(200,"",null);
    }


    /**
     * 重置密码接口（需验证邮箱）
     * @param userName
     * @param mail
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/resetPassword")
    public JsonResult<User> resetPassword(@RequestParam("userName")String userName,
                                          @RequestParam("mail")String mail) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.getUserByUserName(userName);
        if(mail.equals(user.getMail())){
            Integer randNum = (int)(Math.random()* (999999)+1);//产生(0,999999]之间的随机数
            String workPassWord = String.format("%06d",randNum);//进行六位数补全
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("1176026424@qq.com");
            message.setTo(mail);
            message.setSubject("您好，"+userName);
            message.setText("您重置后的密码为："+workPassWord);
            String newPassword = Md5.changeToMd5(workPassWord);
            userService.resetPassword(userName,newPassword);
            javaMailSender.send(message);
            return new JsonResult<>(200,"修改成功",null);
        }else {
            return new JsonResult<>(200,"邮箱验证失败",null);

        }
    }
}
