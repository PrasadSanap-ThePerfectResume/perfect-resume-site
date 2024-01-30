package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.ActivityCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/activity-certificate")
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


    @PostMapping(value = "/update1ActivityCertificate",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleActivityCertificate(@RequestBody ApiRequestBody apiRequestBody){
        return activityCertificationService.updateSingleActivityCertificate(apiRequestBody.getUser(),apiRequestBody.getActivityCertification());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createActivityCertificate(@RequestBody ApiRequestBody apiRequestBody){
        return activityCertificationService.createNewActivityCertificate(apiRequestBody.getUser(),apiRequestBody.getActivityCertification());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteActivityCertificate(@RequestBody ApiRequestBody apiRequestBody){
        return activityCertificationService.deleteActivityCertificate(apiRequestBody.getUser(),apiRequestBody.getActivityCertification());
    }
}
