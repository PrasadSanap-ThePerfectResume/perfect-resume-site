package com.pvs.perfectresume.controller;


import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
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
}

