package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/education")
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


    @PostMapping(value = "/update1Education",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleEducation(@RequestBody ApiRequestBody apiRequestBody){
        return educationService.updateSingleEducation(apiRequestBody.getUser(),apiRequestBody.getEducation());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createEducation(@RequestBody ApiRequestBody apiRequestBody){
        return educationService.createNewEducation(apiRequestBody.getUser(),apiRequestBody.getEducation());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteEducation(@RequestBody ApiRequestBody apiRequestBody){
        return educationService.deleteEducation(apiRequestBody.getUser(),apiRequestBody.getEducation());
    }
}
