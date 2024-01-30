package com.pvs.perfectresume.controller;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * END POINT -Ends with L is List
 * @author Prasad Sanap
 * @since 2023
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/user/language")
public class LanguageRestController {

    @Autowired
    private LanguageService languageService;

    @PostMapping(value = "/languageL", consumes = "application/json", produces = "application/json")
    public ApiResponseBody saveLanguage(@RequestBody ApiRequestBody apiRequestBody) {
        return languageService.saveLanguageList(apiRequestBody);
    }

    @PostMapping(value = "/uLanguageL", consumes = "application/json", produces = "application/json")
    public ApiResponseBody updateLanguage(@RequestBody ApiRequestBody apiRequestBody) {
        return languageService.updateLanguage(apiRequestBody);
    }


    @PostMapping(value = "/update1Language",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleLanguage(@RequestBody ApiRequestBody apiRequestBody){
        return languageService.updateSingleLanguage(apiRequestBody.getUser(),apiRequestBody.getLanguage());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createLanguage(@RequestBody ApiRequestBody apiRequestBody){
        return languageService.createNewLanguage(apiRequestBody.getUser(),apiRequestBody.getLanguage());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteLanguage(@RequestBody ApiRequestBody apiRequestBody){
        return languageService.deleteLanguage(apiRequestBody.getUser(),apiRequestBody.getLanguage());
    }
}
