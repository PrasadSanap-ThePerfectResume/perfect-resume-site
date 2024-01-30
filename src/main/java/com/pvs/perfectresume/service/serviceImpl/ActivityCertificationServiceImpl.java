package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.ActivityCertificationRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.ActivityCertificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityCertificationServiceImpl implements ActivityCertificationService {
    private Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityCertificationRepository activityCertificationRepository;

    private ApiResponseBody apiResponseBody;

    @Override
    public ApiResponseBody saveActivityCertification(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveActivityCertification()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<ActivityCertification> activityCertification = apiRequestBody.getActivityCertificationList();
            activityCertification.stream().forEach(e -> {
                e.setUser(presentUser);
            });
            activityCertificationRepository.saveAll(activityCertification);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveActivityCertification::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_SAVE);
        }
        logger.debug("End of saveActivityCertification()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateActivityCertification(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateActivityCertification()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<ActivityCertification> activityCertification = presentUser.getActivityCertification();
            activityCertification.stream().forEach(e -> {
                List<ActivityCertification> newActivityCertification = apiRequestBody.getActivityCertificationList();
                newActivityCertification.stream().forEach(f -> {
                    if (e.getActCerId() == f.getActCerId()) {
                        if (!f.getActCerTitle().equals("") && f.getActCerTitle() != null)
                            e.setActCerTitle(f.getActCerTitle());

                    }
                });
                newActivityCertification.stream().forEach(f -> {
                    if (f.getActCerId() == null) {
                        f.setUser(presentUser);
                        activityCertificationRepository.save(f);
                    }
                });
            });
            activityCertificationRepository.saveAll(activityCertification);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateActivityCertification::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_UPDATED);
        }
        logger.debug("End of updateActivityCertification()");
        return apiResponseBody;
    }


    @Override
    public ApiResponseBody deleteActivityCertificate(User user, ActivityCertification activityCertification) {
        logger.debug("Start of deleteActivityCertificate()");
        try {
            apiResponseBody = new ApiResponseBody();
            activityCertificationRepository.delete(activityCertification);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_DELETED);
            logger.debug("End of deleteActivityCertificate()");
        }catch (Exception e){
            logger.error("Exception in deleteActivityCertificate::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_DELETED);
        }
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody createNewActivityCertificate(User user, ActivityCertification activityCertification) {
        logger.debug("Start of createNewActivityCertificate()");
        try {
            apiResponseBody = new ApiResponseBody();
            activityCertification.setUser(user);
            activityCertificationRepository.save(activityCertification);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_SAVE);
            logger.debug("End of createNewActivityCertificate()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewActivityCertificate()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_SAVE);
        }
        logger.debug("End of createNewLanguage()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateSingleActivityCertificate(User user, ActivityCertification activityCertification) {
        logger.debug("Start of updateSingleActivityCertificate()");
        try {
            apiResponseBody = new ApiResponseBody();
            ActivityCertification existingActCert=activityCertificationRepository.findByActCerIdAndUser(activityCertification.getActCerId(),user);
            logger.debug("Existing ActivityCertification ::{}",existingActCert);
            if(existingActCert != null){
                existingActCert.setActCerTitle(activityCertification.getActCerTitle());
                activityCertificationRepository.save(existingActCert);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.ACT_CERT_UPDATED);
            }else{
                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_UPDATED);
            }
            logger.debug("End of updateSingleActivityCertificate()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(e.getMessage());
        }
        logger.debug("End of updateSingleActivityCertificate()");
        return apiResponseBody;
    }

}
