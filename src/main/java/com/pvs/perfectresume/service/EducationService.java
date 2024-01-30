package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Education;
import com.pvs.perfectresume.model.User;
import org.springframework.stereotype.Service;

@Service
public interface EducationService {
    ApiResponseBody updateEducation(ApiRequestBody apiRequestBody);

    ApiResponseBody saveEducation(ApiRequestBody apiRequestBody);

    ApiResponseBody updateSingleEducation(User user, Education education);

    ApiResponseBody createNewEducation(User user, Education education);

    ApiResponseBody deleteEducation(User user, Education education);
}
