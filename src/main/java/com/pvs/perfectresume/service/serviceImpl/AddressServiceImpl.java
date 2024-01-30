package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.AddressRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddressServiceImpl implements AddressService {
    private final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private ApiResponseBody apiResponseBody;

    @Override
    public ApiResponseBody updateSingleAddress(User user, Address address) {
        logger.debug("Start of updateSingleAddress()");
        try {
            apiResponseBody = new ApiResponseBody();
            Address existingAddress=addressRepository.findByAddressIdAndUser(address.getAddressId(),user);
            logger.debug("Existing address ::{}",existingAddress);
            if(existingAddress != null){

                existingAddress.setTown(address.getTown());
                existingAddress.setCity(address.getCity());
                existingAddress.setState(address.getState());
                existingAddress.setCountry(address.getCountry());
                existingAddress.setPin(address.getPin());
                existingAddress.setDistrict(address.getDistrict());
                existingAddress.setAddressType(address.getAddressType());

                addressRepository.save(existingAddress);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.ADDRESS_UPDATED);
            }else{

                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.ADDRESS_NOT_UPDATED);
            }
            logger.debug("End of updateSingleAddress()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(e.getMessage());
        }
        logger.debug("End of updateSingleAddress()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody createNewAddress(User user, Address address) {
        logger.debug("Start of createNewAddress()");
        try {
            apiResponseBody = new ApiResponseBody();
            address.setUser(user);
            addressRepository.save(address);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.ADDRESS_SAVE);
            logger.debug("End of createNewAddress()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewAddress()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ADDRESS_NOT_SAVE);
        }
        logger.debug("End of createNewAddress()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody deleteAddress(User user, Address address) {
        logger.debug("Start of deleteAddress()");
        try {
            apiResponseBody = new ApiResponseBody();
            addressRepository.delete(address);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.ADDRESS_DELETED);
            logger.error("End of deleteAddress");
        }catch (Exception e){
            logger.error("Exception in updateAddress::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ADDRESS_NOT_DELETED);
        }
        return apiResponseBody;
    }
}
