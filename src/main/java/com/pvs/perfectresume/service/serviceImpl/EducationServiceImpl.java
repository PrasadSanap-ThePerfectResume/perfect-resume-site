package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Education;
import com.pvs.perfectresume.model.User;
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
                    if (!apiRequestBody.getEducation().getStartYear().equals("") && apiRequestBody.getEducation().getStartYear() != null)
                        e.setStartYear(apiRequestBody.getEducation().getStartYear());
                    if (!apiRequestBody.getEducation().getEndYear().equals("") && apiRequestBody.getEducation().getEndYear() != null)
                        e.setEndYear(apiRequestBody.getEducation().getEndYear());
                    if (!apiRequestBody.getEducation().getStartMonth().equals("") && apiRequestBody.getEducation().getStartMonth() != null)
                        e.setStartMonth(apiRequestBody.getEducation().getStartMonth());
                    if (!apiRequestBody.getEducation().getEndMonth().equals("") && apiRequestBody.getEducation().getEndMonth() != null)
                        e.setEndMonth(apiRequestBody.getEducation().getEndMonth());
                    if (!apiRequestBody.getEducation().getMark().equals("") && apiRequestBody.getEducation().getMark() != null)
                        e.setMark(apiRequestBody.getEducation().getMark());
                    if (!apiRequestBody.getEducation().getEducationType().equals("") && apiRequestBody.getEducation().getEducationType() != null)
                        e.setEducationType(apiRequestBody.getEducation().getEducationType());
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

}
