package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Experience;
import com.pvs.perfectresume.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ExperienceService {
    ApiResponseBody saveExperience(ApiRequestBody apiRequestBody);

    ApiResponseBody updateExperience(ApiRequestBody apiRequestBody);

    ApiResponseBody createNewExperience(User user, Experience experience);

    ApiResponseBody updateSingleExperience(User user, Experience experience);

    ApiResponseBody deleteExperience(User user, Experience experience);
}
