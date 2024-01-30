package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/experience")
public class ExperienceRestController {
    @Autowired
    private ExperienceService experienceService;

    @PostMapping(value = "/experience", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveExperience(@RequestBody ApiRequestBody apiRequestBody) {
        return experienceService.saveExperience(apiRequestBody);
    }

    @PostMapping(value = "/uExperience", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateExperience(@RequestBody ApiRequestBody apiRequestBody) {
        return experienceService.updateExperience(apiRequestBody);
    }

    @PostMapping(value = "/update1Experience",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleExperience(@RequestBody ApiRequestBody apiRequestBody){
        return experienceService.updateSingleExperience(apiRequestBody.getUser(),apiRequestBody.getExperience());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createExperience(@RequestBody ApiRequestBody apiRequestBody){
        return experienceService.createNewExperience(apiRequestBody.getUser(),apiRequestBody.getExperience());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteExperience(@RequestBody ApiRequestBody apiRequestBody){
        return experienceService.deleteExperience(apiRequestBody.getUser(),apiRequestBody.getExperience());
    }
}
