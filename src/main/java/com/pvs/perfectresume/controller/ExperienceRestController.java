package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
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

}
