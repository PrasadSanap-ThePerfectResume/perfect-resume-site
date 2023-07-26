package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.RequestBody;
import com.pvs.perfectresume.model.ResponseBody;
import com.pvs.perfectresume.model.User;
import com.pvs.perfectresume.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public String get(){
        return userService.show();
    }

    @GetMapping("/sendOtp")
    public ResponseBody sendOtp(){
        RequestBody requestBody = new RequestBody();
        User user=new User();
        user.setUsername("prasadsanap99@gmail.com");
        requestBody.setUser(user);
        return userService.sendOtp(requestBody);
    }
}
