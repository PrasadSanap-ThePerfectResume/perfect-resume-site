package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class EducationRestController {
    @Autowired
    private EducationService educationService;


    @PostMapping(value = "/education", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveEducation(@RequestBody ApiRequestBody apiRequestBody) {
        return educationService.saveEducation(apiRequestBody);
    }

    @PostMapping(value = "/uEducation", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateEducation(@RequestBody ApiRequestBody apiRequestBody) {
        return educationService.updateEducation(apiRequestBody);
    }

}
