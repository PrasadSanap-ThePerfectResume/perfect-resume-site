package com.pvs.perfectresume.controller;


import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
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

    @PostMapping(value = "/sendOtp",consumes = "application/json",produces="application/json")
    public ApiResponseBody sendOtp(@RequestBody User user){
        ApiRequestBody requestBody = new ApiRequestBody();
        requestBody.setUser(user);
        return userService.sendOtp(requestBody);
    }


}
