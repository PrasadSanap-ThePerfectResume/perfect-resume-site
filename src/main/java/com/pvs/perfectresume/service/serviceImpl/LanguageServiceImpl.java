package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Language;
import com.pvs.perfectresume.model.User;
import com.pvs.perfectresume.repository.LanguageRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguageServiceImpl implements LanguageService {
    private Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private ApiResponseBody apiResponseBody;

    @Override
    public ApiResponseBody saveLanguage(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Language> language = apiRequestBody.getLanguage();
            language.stream().forEach(e -> {
                e.setUser(presentUser);
            });
            languageRepository.saveAll(language);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveLanguage::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_SAVE);
        }
        logger.debug("End of saveLanguage()");
        return apiResponseBody;
    }


    @Override
    public ApiResponseBody updateLanguage(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Language> language = presentUser.getLanguage();
            language.stream().forEach(e -> {
                List<Language> newLanguage = apiRequestBody.getLanguage();
                newLanguage.stream().forEach(f -> {
                    if (e.getLanguageId() == f.getLanguageId()) {
                        if (!f.getLangTitle().equals("") && f.getLangTitle() != null)
                            e.setLangTitle(f.getLangTitle());
                        if (f.getLevel() <= 100 && f.getLevel() >= 0)
                            e.setLevel(f.getLevel());
                    }
                });
                newLanguage.stream().forEach(f -> {
                    if (f.getLanguageId() == null) {
                        f.setUser(presentUser);
                        languageRepository.save(f);
                    }
                });
            });
            languageRepository.saveAll(language);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateLanguage::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_UPDATED);
        }
        logger.debug("End of updateLanguage()");
        return apiResponseBody;
    }

}
