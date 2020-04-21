package com.bzm.controller;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.bzm.core.Result;
import com.bzm.core.ResultGenerator;
import com.bzm.entity.User;
import com.bzm.service.UserService;
import com.bzm.util.AesEncryptUtil;
import com.bzm.util.ConstantUtils;
import com.bzm.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Api(tags = "用户模块")
@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired(required = false)
    private UserService userService;

    @PostMapping("register")
    @ApiOperation(value = "用户注册", notes="根据用户名、密码判断该用户是否存在")
    public Result register(@RequestBody User user){
        User existUser = userService.findBy("userName", user.getUserName());
        if(existUser != null){
            return ResultGenerator.genFailResult("用户名已经存在");
        }else {
            encryptPasswordByUser(user);
            userService.save(user);
            return ResultGenerator.genSuccessResult();
        }
    }
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes="用户名、密码登录")
    public Result login(@RequestBody User user){
        encryptPasswordByUser(user);
        User existUser = userService.selectOne(user);
        if(existUser == null){
            return ResultGenerator.genFailResult("用户名或密码不正确");
        }else {
            String token = JwtUtil.createToken(user);
            return ResultGenerator.genSuccessResult(token);
        }
    }
    @GetMapping("queryUserByName")
    @ApiOperation(value = "用户注册校验", notes="根据用户名判断该用户是否存在")
    @ApiImplicitParam(name = "userName",value="用户姓名",required=true,type = "String")
    public Result queryUserByName(String userName){
        User existUser = userService.findBy("userName", userName);
        if(existUser != null){
            return ResultGenerator.genFailResult("用户名已经存在");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }

    public void  encryptPasswordByUser(User user){
        String password = AesEncryptUtil.encrypt(user.getPassword());
        user.setPassword(password);
    }
}
