package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.ActivityCertification;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.User;
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
            List<ActivityCertification> activityCertification = apiRequestBody.getActivityCertification();
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
                List<ActivityCertification> newActivityCertification = apiRequestBody.getActivityCertification();
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

}
