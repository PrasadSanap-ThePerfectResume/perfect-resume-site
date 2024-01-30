package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.EducationRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.EducationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationServiceImpl implements EducationService {
    private Logger logger = LoggerFactory.getLogger(EducationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducationRepository educationRepository;

    private ApiResponseBody apiResponseBody;


    @Override
    public ApiResponseBody saveEducation(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Education education = apiRequestBody.getEducation();
            education.setUser(presentUser);
            educationRepository.save(education);
            apiResponseBody.setMessage(AppConstants.EDUCATION_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveEducation::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_SAVE);
        }
        logger.debug("End of saveEducation()");
        return apiResponseBody;
    }


    @Override
    public ApiResponseBody updateEducation(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Education> education = presentUser.getEducation();
            education.stream().forEach(e -> {
                if (e.getEducationId() == apiRequestBody.getEducation().getEducationId()) {
                    if (!apiRequestBody.getEducation().getUniversity().equals("") && apiRequestBody.getEducation().getUniversity() != null)
                        e.setUniversity(apiRequestBody.getEducation().getUniversity());
                    if (!apiRequestBody.getEducation().getCollege().equals("") && apiRequestBody.getEducation().getCollege() != null)
                        e.setCollege(apiRequestBody.getEducation().getCollege());
                    if (!apiRequestBody.getEducation().getStream().equals("") && apiRequestBody.getEducation().getStream() != null)
                        e.setStream(apiRequestBody.getEducation().getStream());
                    if ( apiRequestBody.getEducation().getStartYear() != 0)
                        e.setStartYear(apiRequestBody.getEducation().getStartYear());
                    if ( apiRequestBody.getEducation().getEndYear() != 0)
                        e.setEndYear(apiRequestBody.getEducation().getEndYear());
                    if (!apiRequestBody.getEducation().getStartMonth().equals("") && apiRequestBody.getEducation().getStartMonth() != null)
                        e.setStartMonth(apiRequestBody.getEducation().getStartMonth());
                    if (!apiRequestBody.getEducation().getEndMonth().equals("") && apiRequestBody.getEducation().getEndMonth() != null)
                        e.setEndMonth(apiRequestBody.getEducation().getEndMonth());
                    if (!apiRequestBody.getEducation().getPercentage().equals("") && apiRequestBody.getEducation().getPercentage() != null)
                        e.setPercentage(apiRequestBody.getEducation().getPercentage());
                    if (!apiRequestBody.getEducation().getEducationMode().equals("") && apiRequestBody.getEducation().getEducationMode() != null)
                        e.setEducationMode(apiRequestBody.getEducation().getEducationMode());
                    if (apiRequestBody.getEducation().isPresentCollege())
                        e.setPresentCollege(apiRequestBody.getEducation().isPresentCollege());
                    educationRepository.save(e);
                    apiResponseBody.setMessage(AppConstants.EDUCATION_UPDATED);
                    apiResponseBody.setStatus(AppConstants.SUCCESS);
                    apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
                }
            });
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateEducation::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_UPDATED);
        }
        logger.debug("End of updateEducation()");
        return apiResponseBody;
    }


    @Override
    public ApiResponseBody updateSingleEducation(User user, Education education) {
        logger.debug("Start of updateSingleSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            Education existingEducation=educationRepository.findByEducationIdAndUser(education.getEducationId(),user);
            logger.debug("Existing education ::{}",existingEducation);
            if(existingEducation != null){
                existingEducation.setUniversity(education.getUniversity());
                existingEducation.setCollege(education.getCollege());
                existingEducation.setStream(education.getStream());
                existingEducation.setStartYear(education.getStartYear());
                existingEducation.setEndYear(education.getEndYear());
                existingEducation.setStartMonth(education.getStartMonth());
                existingEducation.setEndMonth(education.getEndMonth());
                existingEducation.setPercentage(education.getPercentage());
                existingEducation.setEducationMode(education.getEducationMode());
                existingEducation.setPresentCollege(education.isPresentCollege());

                educationRepository.save(existingEducation);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.EDUCATION_UPDATED);
            }else{

                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_UPDATED);
            }
            logger.debug("End of updateSingleSkill()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(e.getMessage());
        }
        logger.debug("End of updateSingleSkill()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody createNewEducation(User user, Education education) {
        logger.debug("Start of createNewEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            education.setUser(user);
            educationRepository.save(education);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_SAVE);
            logger.debug("End of createNewEducation()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewEducation()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_SAVE);
        }
        logger.debug("End of createNewEducation()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody deleteEducation(User user, Education education) {
        logger.debug("Start of deleteEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            educationRepository.delete(education);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_DELETED);
            logger.debug("End of deleteEducation()");
        }catch (Exception e){
            logger.error("Exception in deleteEducation::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_DELETED);
        }
        return apiResponseBody;
    }

}
