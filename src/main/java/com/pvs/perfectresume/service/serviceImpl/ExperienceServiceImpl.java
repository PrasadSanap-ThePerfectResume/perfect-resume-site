package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.AchievementRepository;
import com.pvs.perfectresume.repository.ExperienceRepository;
import com.pvs.perfectresume.repository.ProjectRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExperienceServiceImpl implements ExperienceService {

    private Logger logger = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private ApiResponseBody apiResponseBody;


    @Override
    public ApiResponseBody saveExperience(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Experience experience = apiRequestBody.getExperience();
            experience.setUser(presentUser);
            final Experience saveExperience = experienceRepository.save(experience);
            List<Achievement> achievement = experience.getAchievements();
            achievement.stream().forEach(e -> {
                e.setExperience(saveExperience);
            });
            achievementRepository.saveAll(achievement);
            apiResponseBody.setMessage(AppConstants.EDUCATION_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_SAVE);
        }
        logger.debug("End of saveExperience()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateExperience(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Experience> experience = presentUser.getExperience();
            experience.stream().forEach(e -> {
                if (e.getExperienceId() == apiRequestBody.getExperience().getExperienceId()) {
                    if (!apiRequestBody.getExperience().getTitle().equals("") && apiRequestBody.getExperience().getTitle() != null)
                        e.setTitle(apiRequestBody.getExperience().getTitle());
                    if (!apiRequestBody.getExperience().getEmploymentType().equals("") && apiRequestBody.getExperience().getEmploymentType() != null)
                        e.setEmploymentType(apiRequestBody.getExperience().getEmploymentType());
                    if (!apiRequestBody.getExperience().getCompanyName().equals("") && apiRequestBody.getExperience().getCompanyName() != null)
                        e.setCompanyName(apiRequestBody.getExperience().getCompanyName());
                    if (!apiRequestBody.getExperience().getLocation().equals("") && apiRequestBody.getExperience().getLocation() != null)
                        e.setLocation(apiRequestBody.getExperience().getLocation());
                    if (!apiRequestBody.getExperience().getLocationType().equals("") && apiRequestBody.getExperience().getLocationType() != null)
                        e.setLocationType(apiRequestBody.getExperience().getLocationType());
                    if (!apiRequestBody.getExperience().getStartMonth().equals("") && apiRequestBody.getExperience().getStartMonth() != null)
                        e.setStartMonth(apiRequestBody.getExperience().getStartMonth());
                    if (!apiRequestBody.getExperience().getStartYear().equals("") && apiRequestBody.getExperience().getStartYear() != null)
                        e.setStartYear(apiRequestBody.getExperience().getStartYear());
                    if (!apiRequestBody.getExperience().getEndMonth().equals("") && apiRequestBody.getExperience().getEndMonth() != null)
                        e.setEndMonth(apiRequestBody.getExperience().getEndMonth());
                    if (!apiRequestBody.getExperience().getEndYear().equals("") && apiRequestBody.getExperience().getEndYear() != null)
                        e.setEndYear(apiRequestBody.getExperience().getEndYear());
                    if (!apiRequestBody.getExperience().getSkills().equals("") && apiRequestBody.getExperience().getSkills() != null)
                        e.setSkills(apiRequestBody.getExperience().getSkills());
                    if (!apiRequestBody.getExperience().getDescription().equals("") && apiRequestBody.getExperience().getDescription() != null)
                        e.setDescription(apiRequestBody.getExperience().getDescription());
                    //Achievement update section
                    List<Achievement> presentAchievement = e.getAchievements();
                    List<Achievement> requestAchievement = apiRequestBody.getExperience().getAchievements();
                    if (presentAchievement.size() == 0) {
                        requestAchievement.stream().forEach(a -> {
                            a.setExperience(e);
                        });
                        achievementRepository.saveAll(requestAchievement);
                    } else {
                        presentAchievement.stream().forEach(a -> {
                            //UPDATE EXISTING RECORDS
                            requestAchievement.stream().forEach(b -> {
                                if (a.getAchievementId() == b.getAchievementId()) {
                                    if (!b.getAchievement().equals("") && b.getAchievement() != null)
                                        a.setAchievement(b.getAchievement());
                                }
                            });
                            //ADD NEW RECORDS
                            requestAchievement.stream().forEach(b -> {
                                if (b.getAchievementId() == null) {
                                    b.setExperience(e);
                                    achievementRepository.save(b);
                                }
                            });
                        });
                        achievementRepository.saveAll(presentAchievement);
                    }

                    //Project update section
                    List<Project> presentProject = e.getProject();
                    List<Project> requestProject = apiRequestBody.getExperience().getProject();
                    if (presentProject.size() == 0) {
                        requestProject.stream().forEach(a -> {
                            a.setExperience(e);
                        });
                        projectRepository.saveAll(requestProject);
                    } else {
                        presentProject.stream().forEach(a -> {
                            //UPDATE EXISTING RECORDS
                            requestProject.stream().forEach(b -> {
                                if (a.getProjectId() == b.getProjectId()) {
                                    if (!b.getProjectTitle().equals("") && b.getProjectTitle() != null)
                                        a.setProjectTitle(b.getProjectTitle());
                                    if (!b.getBackEndTech().equals("") && b.getBackEndTech() != null)
                                        a.setBackEndTech(b.getBackEndTech());
                                    if (!b.getFrontEndTech().equals("") && b.getFrontEndTech() != null)
                                        a.setFrontEndTech(b.getFrontEndTech());
                                }
                            });
                            //ADD NEW RECORDS
                            requestProject.stream().forEach(b -> {
                                if (b.getProjectId() == null) {
                                    b.setExperience(e);
                                    projectRepository.save(b);
                                }
                            });
                        });
                        achievementRepository.saveAll(presentAchievement);
                    }

                    experienceRepository.save(e);
                    apiResponseBody.setMessage(AppConstants.EXPERIENCE_UPDATED);
                    apiResponseBody.setStatus(AppConstants.SUCCESS);
                    apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
                }
            });
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_UPDATED);
        }
        logger.debug("End of updateExperience()");
        return apiResponseBody;
    }

}
