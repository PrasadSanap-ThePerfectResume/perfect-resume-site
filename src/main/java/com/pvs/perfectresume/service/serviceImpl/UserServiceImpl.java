package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.PerfectResumeApplication;
import com.pvs.perfectresume.constants.ResponsesConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.AddressRepository;
import com.pvs.perfectresume.repository.OTPValidationRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.UserService;
import com.pvs.perfectresume.util.OTPGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPValidationRepository otpValidationRepository;

    @Autowired
    private AddressRepository addressRepository;

    private ApiResponseBody apiResponseBody;

    private Pattern pattern = Pattern.compile("[!~?/#$\\{%^&*(\\]}|=)\\[]");

    private Logger logger = LoggerFactory.getLogger(PerfectResumeApplication.class);

    @Override
    public String show() {
        return "HELLO PRASAD";
    }

    @Override
    public ApiResponseBody sendOtp(ApiRequestBody apiRequestBody) {
        logger.debug("Start of sendOtp()");
        apiResponseBody = new ApiResponseBody();
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
            OTPValidation otpValidation = otpValidationRepository.findByUsername(apiRequestBody.getUser().getUsername());
            if (otpValidation == null) {
                //Creating new record.
                otpValidation = new OTPValidation();
                otpValidation.setUser(user);
                otpValidation.setUsername(user.getUsername());
            }
            otpValidation.setOtp(String.valueOf(generatedOTP));
            otpValidation.setVerify(false);
            //Save user with otp into table
            otpValidationRepository.save(otpValidation);
            apiResponseBody.setMessage(ResponsesConstants.OTP_SEND);
            apiResponseBody.setStatus(ResponsesConstants.SUCCESS);
            apiResponseBody.setStatusCode(ResponsesConstants.SUCCESS_CODE);

        } catch (Exception e) {
            apiResponseBody.setMessage(ResponsesConstants.OTP_NOT_SEND);
            apiResponseBody.setStatus(ResponsesConstants.FAILED);
            apiResponseBody.setStatusCode(ResponsesConstants.FAILED_CODE);
            logger.error("Exception in sendOtp::{}", e.getMessage());

        }
        logger.debug("End of sendOtp()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody verifyOtp(OTPValidation otpValidation) {
        logger.debug("Start of verifyOtp()");
        try {
            apiResponseBody = new ApiResponseBody();
            OTPValidation presentOtpValidation = otpValidationRepository.findByUsername(otpValidation.getUsername());
            if (presentOtpValidation.getOtp().equals(otpValidation.getOtp())) {
                //Updating verify otp
                presentOtpValidation.setVerify(true);
                otpValidationRepository.save(presentOtpValidation);
                apiResponseBody.setStatus(ResponsesConstants.SUCCESS);
                apiResponseBody.setStatusCode(ResponsesConstants.SUCCESS_CODE);
                apiResponseBody.setMessage(ResponsesConstants.OTP_VERIFIED);
            } else {
                apiResponseBody.setStatus(ResponsesConstants.FAILED);
                apiResponseBody.setStatusCode(ResponsesConstants.FAILED_CODE);
                apiResponseBody.setMessage(ResponsesConstants.OTP_NOT_VERIFIED);
            }
        } catch (Exception e) {
            logger.error("Exception in verifyOtp::{}", e.getMessage());
        }
        logger.debug("End of verifyOtp()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody saveAddress(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveAddress()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Address presentAddress=addressRepository.findByUser(presentUser);
            if(presentAddress==null){
                Address newAddress=apiRequestBody.getAddress();
                newAddress.setUser(presentUser);
                addressRepository.save(newAddress);
                apiResponseBody.setMessage(ResponsesConstants.ADDRESS_SAVE);
            }else{
                if(!apiRequestBody.getAddress().getAddressType().equals("")|| apiRequestBody.getAddress().getAddressType() != null)
                    presentAddress.setAddressType(apiRequestBody.getAddress().getAddressType());
                if(!apiRequestBody.getAddress().getCity().equals("")|| apiRequestBody.getAddress().getCity() != null)
                    presentAddress.setCity(apiRequestBody.getAddress().getCity());
                if(!apiRequestBody.getAddress().getDistrict().equals("")|| apiRequestBody.getAddress().getDistrict() != null)
                    presentAddress.setDistrict(apiRequestBody.getAddress().getDistrict());
                if(!apiRequestBody.getAddress().getCountry().equals("")|| apiRequestBody.getAddress().getCountry() != null)
                    presentAddress.setCountry(apiRequestBody.getAddress().getCountry());
                if(!apiRequestBody.getAddress().getPin().equals("")|| apiRequestBody.getAddress().getPin() != null)
                    presentAddress.setPin(apiRequestBody.getAddress().getPin());
                if(!apiRequestBody.getAddress().getState().equals("")|| apiRequestBody.getAddress().getState() != null)
                    presentAddress.setState(apiRequestBody.getAddress().getState());
                if(!apiRequestBody.getAddress().getTown().equals("")|| apiRequestBody.getAddress().getTown() != null)
                    presentAddress.setTown(apiRequestBody.getAddress().getTown());
                apiResponseBody.setMessage(ResponsesConstants.ADDRESS_UPDATE);
                addressRepository.save(presentAddress);
            }
            apiResponseBody.setStatus(ResponsesConstants.SUCCESS);
            apiResponseBody.setStatusCode(ResponsesConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveAddress::{}", e.getMessage());
            apiResponseBody.setStatus(ResponsesConstants.FAILED);
            apiResponseBody.setStatusCode(ResponsesConstants.FAILED_CODE);
            apiResponseBody.setMessage(ResponsesConstants.ADDRESS_NOT_SAVE);
        }
        logger.debug("End of saveAddress()");
        return apiResponseBody;
    }

    @Override
    public User findUser(String s) {
        User user = userRepository.findByUsername(s);
        return user;
    }

    //("[a-zA-Z0-9]{6}","prasad1") -->true
}
