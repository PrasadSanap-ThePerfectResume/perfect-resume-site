package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import org.springframework.stereotype.Service;

@Service
public interface EducationService {
    ApiResponseBody updateEducation(ApiRequestBody apiRequestBody);

    ApiResponseBody saveEducation(ApiRequestBody apiRequestBody);
}
