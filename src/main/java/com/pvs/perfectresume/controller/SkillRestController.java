package com.pvs.perfectresume.controller;


import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/skill")
public class SkillRestController {


    @Autowired
    private SkillService skillService;

    @PostMapping(value = "/skill", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveSkill(@RequestBody ApiRequestBody apiRequestBody) {
        return skillService.saveSkill(apiRequestBody);
    }

    @PostMapping(value = "/uSkill", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateSkill(@RequestBody ApiRequestBody apiRequestBody) {
        return skillService.updateSkill(apiRequestBody);
    }

    @PostMapping(value = "/update1Skill",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleSkill(@RequestBody ApiRequestBody apiRequestBody){
        return skillService.updateSingleSkill(apiRequestBody.getUser(),apiRequestBody.getSkill());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createSkill(@RequestBody ApiRequestBody apiRequestBody){
        return skillService.createNewSkill(apiRequestBody.getUser(),apiRequestBody.getSkill());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteSkill(@RequestBody ApiRequestBody apiRequestBody){
        return skillService.deleteSkill(apiRequestBody.getUser(),apiRequestBody.getSkill());
    }
}

