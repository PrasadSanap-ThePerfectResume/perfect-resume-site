package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.OTPValidation;
import com.pvs.perfectresume.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String show();

    ApiResponseBody sendOtp(ApiRequestBody apiRequestBody);

    ApiResponseBody verifyOtp(OTPValidation otpValidation);

    ApiResponseBody saveAddress(ApiRequestBody apiRequestBody);

    User findUser(String s);

    ApiResponseBody saveUser(ApiRequestBody apiRequestBody);


//    ApiResponseBody saveExperience(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody updateExperience(ApiRequestBody apiRequestBody);
//
//
//    ApiResponseBody saveSkill(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody saveActivityCertification(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody updateEducation(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody saveEducation(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody saveLanguage(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody updateLanguage(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody updateSkill(ApiRequestBody apiRequestBody);
//
//    ApiResponseBody updateActivityCertification(ApiRequestBody apiRequestBody);
}
