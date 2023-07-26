package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.OTPValidation;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String show();

    ApiResponseBody sendOtp(ApiRequestBody apiRequestBody);

    ApiResponseBody verifyOtp(OTPValidation otpValidation);
}
