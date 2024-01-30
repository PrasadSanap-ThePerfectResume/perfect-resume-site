package com.pvs.perfectresume.controller;


import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.OTPValidation;
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
    public String get() {
        return userService.show();
    }

    @PostMapping(value = "/sendOtp", consumes = "application/json", produces = "application/json")
    public ApiResponseBody sendOtp(@RequestBody User user) {
        ApiRequestBody requestBody = new ApiRequestBody();
        requestBody.setUser(user);
        return userService.sendOtp(requestBody);
    }

    @PostMapping(value = "/verifyOtp", consumes = "application/json", produces = "application/json")
    public ApiResponseBody verifyOtp(@RequestBody OTPValidation otpValidation) {
        return userService.verifyOtp(otpValidation);
    }

    @GetMapping(value = "/findUser", produces = "application/json")
    public User findUser() {
        return userService.findUser("prasadsanap2@gmail.com");
    }

    @GetMapping(value = "/{userName}", produces = "application/json")
    public User getUserDetails(@PathVariable String userName) {
        return userService.findUser(userName);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveUser(@RequestBody ApiRequestBody apiRequestBody) {
        return userService.saveUser(apiRequestBody);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ApiResponseBody loginUser(@RequestBody ApiRequestBody apiRequestBody) {
        return userService.loginUser(apiRequestBody);
    }

    @PostMapping(value = "/forget", consumes = "application/json", produces = "application/json")
    public ApiResponseBody forgetPassword(@RequestBody ApiRequestBody apiRequestBody) {
        return userService.forgotPassword(apiRequestBody);
    }


}
