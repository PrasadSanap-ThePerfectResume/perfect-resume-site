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

import java.util.Collections;
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
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_SAVE);
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
                    if (!apiRequestBody.getExperience().getWorkingMode().equals("") && apiRequestBody.getExperience().getWorkingMode() != null)
                        e.setWorkingMode(apiRequestBody.getExperience().getWorkingMode());
                    if (!apiRequestBody.getExperience().getStartMonth().equals("") && apiRequestBody.getExperience().getStartMonth() != null)
                        e.setStartMonth(apiRequestBody.getExperience().getStartMonth());
                    if (apiRequestBody.getExperience().getStartYear() !=0)
                        e.setStartYear(apiRequestBody.getExperience().getStartYear());
                    if (!apiRequestBody.getExperience().getEndMonth().equals("") && apiRequestBody.getExperience().getEndMonth() != null)
                        e.setEndMonth(apiRequestBody.getExperience().getEndMonth());
                    if (apiRequestBody.getExperience().getEndYear() !=0 )
                        e.setEndYear(apiRequestBody.getExperience().getEndYear());
                    if (!apiRequestBody.getExperience().getSkills().equals("") && apiRequestBody.getExperience().getSkills() != null)
                        e.setSkills(apiRequestBody.getExperience().getSkills());
                    if (!apiRequestBody.getExperience().getDescription().equals("") && apiRequestBody.getExperience().getDescription() != null)
                        e.setDescription(apiRequestBody.getExperience().getDescription());
                    if(apiRequestBody.getExperience().isPresentCompany())
                        e.setPresentCompany(apiRequestBody.getExperience().isPresentCompany());
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
                    List<Project> presentProject = e.getProjects();
                    List<Project> requestProject = apiRequestBody.getExperience().getProjects();
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
                        projectRepository.saveAll(presentProject);
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

    @Override
    public ApiResponseBody updateSingleExperience(User user, Experience experience) {
        logger.debug("Start of updateSingleExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            Experience existingExperience=experienceRepository.findByExperienceIdAndUser(experience.getExperienceId(),user);
            logger.debug("Existing experience ::{}",existingExperience);
            if(existingExperience != null){
                existingExperience.setTitle(experience.getTitle());
                existingExperience.setEmploymentType(experience.getEmploymentType());
                existingExperience.setCompanyName(experience.getCompanyName());
                existingExperience.setStartMonth(experience.getStartMonth());
                existingExperience.setStartYear(experience.getStartYear());
                existingExperience.setEndMonth(experience.getEndMonth());
                existingExperience.setEndYear(experience.getEndYear());
                existingExperience.setSkills(experience.getSkills());
                existingExperience.setDescription(experience.getDescription());
                existingExperience.setPresentCompany(experience.isPresentCompany());

                //ACHIEVEMENT UPDATE SECTION
                List<Achievement> existingAchievements= existingExperience.getAchievements();
                List<Achievement> updatedAchievements= experience.getAchievements();
                //UPDATE EXISTING ACHIEVEMENT
                if(existingAchievements.size()>0 && updatedAchievements.size()>0) {
                    for (Achievement eAchievement : existingAchievements) {
                        for (Achievement uAchievement : updatedAchievements) {
                                if(eAchievement.getAchievementId().equals(uAchievement.getAchievementId())){
                                    boolean flag = false;
                                    if(!eAchievement.getAchievement().equals(uAchievement.getAchievement()))
                                    {
                                        flag=true;
                                        eAchievement.setAchievement(uAchievement.getAchievement());
                                    }
                                    if(flag){
                                        achievementRepository.save(eAchievement);
                                    }
                                    break;
                                }
                        }
                    }
                }
                //ADDING NEW ACHIEVEMENT
                for (Achievement uAchievement : updatedAchievements) {
                    if(uAchievement.getAchievementId() == null || uAchievement.getAchievementId()==0){
                        uAchievement.setExperience(existingExperience);
                        achievementRepository.save(uAchievement);
                    }
                }

                //PROJECT UPDATE SECTION
                List<Project> existingProject = existingExperience.getProjects();
                List<Project> updatedProject = experience.getProjects();


                if(existingProject.size()>0 && updatedAchievements.size()>0) {
                    for (Project eProject : existingProject) {
                        for (Project uProject : updatedProject) {
                            if(eProject.getProjectId().equals(uProject.getProjectId())){
                                boolean flag = false;
                                if(!eProject.getProjectTitle().equals(uProject.getProjectTitle()))
                                {
                                    flag=true;
                                    eProject.setProjectTitle(uProject.getProjectTitle());
                                }
                                if(!eProject.getFrontEndTech().equals(uProject.getFrontEndTech()))
                                {
                                    flag=true;
                                    eProject.setFrontEndTech(uProject.getFrontEndTech());
                                }
                                if(!eProject.getBackEndTech().equals(uProject.getBackEndTech()))
                                {
                                    flag=true;
                                    eProject.setBackEndTech(uProject.getBackEndTech());
                                }
                                if(flag){
                                    projectRepository.save(eProject);
                                }
                                break;
                            }
                        }
                    }
                }
                //ADDING NEW ACHIEVEMENT
                for (Project uProject : updatedProject) {
                    if(uProject.getProjectId() == null || uProject.getProjectId()==0){
                        uProject.setExperience(existingExperience);
                        projectRepository.save(uProject);
                    }
                }


                existingExperience.setUser(user);
                experienceRepository.save(existingExperience);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.EXPERIENCE_UPDATED);
            }else{
                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_UPDATED);
            }
            logger.debug("End of updateSingleExperience()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(e.getMessage());
        }
        logger.debug("End of updateSingleExperience()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody createNewExperience(User user, Experience experience) {
        logger.debug("Start of createNewExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            experience.setUser(user);
            experienceRepository.save(experience);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_SAVE);
            logger.debug("End of createNewExperience()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewExperience()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_SAVE);
        }
        logger.debug("End of createNewExperience()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody deleteExperience(User user, Experience experience) {
        logger.debug("Start of deleteExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            experienceRepository.delete(experience);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_DELETED);
            logger.debug("End of deleteExperience()");
        }catch (Exception e){
            logger.error("Exception in deleteExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_DELETED);
        }
        return apiResponseBody;
    }


}
