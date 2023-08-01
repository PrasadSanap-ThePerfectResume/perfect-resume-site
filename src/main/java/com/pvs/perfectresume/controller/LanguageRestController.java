package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class LanguageRestController {

    @Autowired
    private LanguageService LanguageService;

    @PostMapping(value = "/language", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveLanguage(@RequestBody ApiRequestBody apiRequestBody) {
        return LanguageService.saveLanguage(apiRequestBody);
    }

    @PostMapping(value = "/uLanguage", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateLanguage(@RequestBody ApiRequestBody apiRequestBody) {
        return LanguageService.updateLanguage(apiRequestBody);
    }
}
