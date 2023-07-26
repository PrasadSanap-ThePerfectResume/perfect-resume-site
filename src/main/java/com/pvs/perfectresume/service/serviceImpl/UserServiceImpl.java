package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.model.OTPValidation;
import com.pvs.perfectresume.model.RequestBody;
import com.pvs.perfectresume.model.ResponseBody;
import com.pvs.perfectresume.model.User;
import com.pvs.perfectresume.repository.OTPValidationRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.UserService;
import com.pvs.perfectresume.util.OTPGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPValidationRepository otpValidationRepository;


    private ResponseBody responseBody;
    private Pattern pattern=Pattern.compile("[#$%^&*()]");
    @Override
    public String show() {
        return "HELLO PRASAD";
    }

    @Override
    public ResponseBody sendOtp(RequestBody requestBody) {
        responseBody=new ResponseBody();
        if(pattern.matcher(requestBody.getUser().getUsername()).find()) {
            responseBody.setOtp("000000");
            return responseBody;
        }

        int generatedOTP=OTPGeneration.getOTP();
        responseBody.setOtp(String.valueOf(generatedOTP));
        //Check first is user already present in database
        User user=userRepository.findByUsername(requestBody.getUser().getUsername());
        if(user==null) {
            //Save user first into table
            userRepository.save(requestBody.getUser());
        }
        //Fetch user from table with id.
        user=userRepository.findByUsername(requestBody.getUser().getUsername());

        //Save user with otp into table
        OTPValidation otpValidation=new OTPValidation();
        otpValidation.setUser(user);
        otpValidation.setOtp(String.valueOf(generatedOTP));
        otpValidation.setUsername(user.getUsername());
        otpValidationRepository.save(otpValidation);

        return responseBody;
    }

    //("[a-zA-Z0-9]{6}","prasad1") -->true
}
