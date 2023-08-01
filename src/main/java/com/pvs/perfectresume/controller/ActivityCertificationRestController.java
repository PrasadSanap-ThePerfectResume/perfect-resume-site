package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.ActivityCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ActivityCertificationRestController {

    @Autowired
    private ActivityCertificationService activityCertificationService;

    @PostMapping(value = "/activityCertification", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveActivityCertification(@RequestBody ApiRequestBody apiRequestBody) {
        return activityCertificationService.saveActivityCertification(apiRequestBody);
    }

    @PostMapping(value = "/uActivityCertification", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateActivityCertification(@RequestBody ApiRequestBody apiRequestBody) {
        return activityCertificationService.updateActivityCertification(apiRequestBody);
    }
}
