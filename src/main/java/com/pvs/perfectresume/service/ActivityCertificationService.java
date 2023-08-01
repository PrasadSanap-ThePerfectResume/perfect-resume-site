package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;

public interface ActivityCertificationService {
    ApiResponseBody updateActivityCertification(ApiRequestBody apiRequestBody);

    ApiResponseBody saveActivityCertification(ApiRequestBody apiRequestBody);
}
