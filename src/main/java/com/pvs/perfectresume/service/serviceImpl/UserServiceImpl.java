package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.ResponsesConstants;
import com.pvs.perfectresume.model.OTPValidation;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
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


    private ApiResponseBody apiResponseBody;
    private Pattern pattern=Pattern.compile("[!~?/#$\\{%^&*(\\]}|=)\\[]");
    @Override
    public String show() {
        return "HELLO PRASAD";
    }

    @Override
    public ApiResponseBody sendOtp(ApiRequestBody apiRequestBody) {
        apiResponseBody =new ApiResponseBody();
        try {
            if (pattern.matcher(apiRequestBody.getUser().getUsername()).find()) {
                apiResponseBody.setMessage(ResponsesConstants.VALID_EMAIL);
                apiResponseBody.setStatus(ResponsesConstants.FAILED);
                apiResponseBody.setStatusCode(ResponsesConstants.FAILED_CODE);
                return apiResponseBody;
            }

            int generatedOTP = OTPGeneration.getOTP();
            //Check first is user already present in database
            User user = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            if (user == null) {
                //Save user first into table
                userRepository.save(apiRequestBody.getUser());
            }
            //Fetch user from table with id.
            user = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            OTPValidation otpValidation=otpValidationRepository.findByUsername(apiRequestBody.getUser().getUsername());
            if(otpValidation==null){
                //Creating new record.
                otpValidation=new OTPValidation();
                otpValidation.setUser(user);
                otpValidation.setUsername(user.getUsername());
            }
            otpValidation.setOtp(String.valueOf(generatedOTP));

            //Save user with otp into table
            otpValidationRepository.save(otpValidation);
            apiResponseBody.setMessage(ResponsesConstants.OTP_SEND);
            apiResponseBody.setStatus(ResponsesConstants.SUCCESS);
            apiResponseBody.setStatusCode(ResponsesConstants.SUCCESS_CODE);

        }catch(Exception e){
            apiResponseBody.setMessage(ResponsesConstants.OTP_NOT_SEND);
            apiResponseBody.setStatus(ResponsesConstants.FAILED);
            apiResponseBody.setStatusCode(ResponsesConstants.FAILED_CODE);
        }
        return apiResponseBody;
    }

    //("[a-zA-Z0-9]{6}","prasad1") -->true
}
