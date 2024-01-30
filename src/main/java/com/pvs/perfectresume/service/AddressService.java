package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.Address;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.User;

public interface AddressService {
    ApiResponseBody updateSingleAddress(User user, Address address);

    ApiResponseBody createNewAddress(User user, Address address);

    ApiResponseBody deleteAddress(User user, Address address);
}
