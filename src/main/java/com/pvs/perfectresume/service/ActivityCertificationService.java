package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ActivityCertification;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.User;

public interface ActivityCertificationService {
    ApiResponseBody updateActivityCertification(ApiRequestBody apiRequestBody);

    ApiResponseBody saveActivityCertification(ApiRequestBody apiRequestBody);

    ApiResponseBody deleteActivityCertificate(User user, ActivityCertification activityCertification);

    ApiResponseBody createNewActivityCertificate(User user, ActivityCertification activityCertification);

    ApiResponseBody updateSingleActivityCertificate(User user, ActivityCertification activityCertification);
}
