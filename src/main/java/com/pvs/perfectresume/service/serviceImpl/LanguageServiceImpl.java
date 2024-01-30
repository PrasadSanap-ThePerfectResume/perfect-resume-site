package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
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
    private final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private ApiResponseBody apiResponseBody;

    @Override
    public ApiResponseBody saveLanguageList(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Language> language = apiRequestBody.getLanguageList();
            language.forEach(e -> e.setUser(presentUser));
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
            language.forEach(e -> {
                List<Language> newLanguage = apiRequestBody.getLanguageList();
                newLanguage.forEach(f -> {
                    if (e.getLanguageId().equals(f.getLanguageId())) {
                        if (!f.getLangTitle().equals("") && f.getLangTitle() != null)
                            e.setLangTitle(f.getLangTitle());
                    }
                });
                newLanguage.forEach(f -> {
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


    @Override
    public ApiResponseBody updateSingleLanguage(User user, Language language) {
        logger.debug("Start of updateSingleLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            Language existingLanguage=languageRepository.findByLanguageIdAndUser(language.getLanguageId(),user);
            logger.debug("Existing language ::{}",existingLanguage);
            if(existingLanguage != null){
                existingLanguage.setLevel(language.getLevel());
                existingLanguage.setLangTitle(language.getLangTitle());
                languageRepository.save(existingLanguage);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.LANGUAGE_UPDATED);
            }else{

                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_UPDATED);
            }
            logger.debug("End of updateSingleLanguage()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(e.getMessage());
        }
        logger.debug("End of updateSingleLanguage()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody createNewLanguage(User user, Language language) {
        logger.debug("Start of createNewLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            language.setUser(user);
            languageRepository.save(language);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_SAVE);
            logger.debug("End of createNewLanguage()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewLanguage()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_SAVE);
        }
        logger.debug("End of createNewLanguage()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody deleteLanguage(User user, Language language) {
        logger.debug("Start of deleteSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            languageRepository.delete(language);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_DELETED);
            logger.debug("End of deleteSkill()");
        }catch (Exception e){
            logger.error("Exception in delete skill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_DELETED);
        }
        return apiResponseBody;
    }

}
