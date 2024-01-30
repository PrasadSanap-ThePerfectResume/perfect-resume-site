package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Language;
import com.pvs.perfectresume.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LanguageService {

    ApiResponseBody updateLanguage(ApiRequestBody apiRequestBody);

    ApiResponseBody saveLanguageList(ApiRequestBody apiRequestBody);

    ApiResponseBody updateSingleLanguage(User user, Language language);

    ApiResponseBody createNewLanguage(User user, Language language);

    ApiResponseBody deleteLanguage(User user, Language language);
}
