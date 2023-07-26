package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.RequestBody;
import com.pvs.perfectresume.model.ResponseBody;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String show();

    ResponseBody sendOtp(RequestBody requestBody);
}
