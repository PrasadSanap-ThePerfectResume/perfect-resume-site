package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Skill;
import com.pvs.perfectresume.model.User;
import com.pvs.perfectresume.repository.SkillRepository;
import com.pvs.perfectresume.repository.UserRepository;
import com.pvs.perfectresume.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkillServiceImpl implements SkillService {
    private final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    private ApiResponseBody apiResponseBody;

    @Override
    public ApiResponseBody saveSkill(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Skill> skill = apiRequestBody.getSkillList();
            skill.forEach(e -> e.setUser(presentUser));
            skillRepository.saveAll(skill);
            apiResponseBody.setMessage(AppConstants.SKILL_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveSkill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_SAVE);
        }
        logger.debug("End of saveSkill()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateSingleSkill(User user, Skill skill) {
        logger.debug("Start of updateSingleSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            Skill existingSkill=skillRepository.findBySkillIdAndUser(skill.getSkillId(),user);
            logger.debug("Existing skill ::{}",existingSkill);
            if(existingSkill != null){
                existingSkill.setLevel(skill.getLevel());
                existingSkill.setSkillTitle(skill.getSkillTitle());
                skillRepository.save(existingSkill);
                apiResponseBody.setStatusCode(AppConstants.UPDATED_CODE);
                apiResponseBody.setMessage(AppConstants.SKILL_UPDATED);
            }else{

                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.SKILL_NOT_UPDATED);
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
    public ApiResponseBody createNewSkill(User user, Skill skill) {
        logger.debug("Start of createNewSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            skill.setUser(user);
            skillRepository.save(skill);
            apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_SAVE);
            logger.debug("End of createNewSkill()");
            return apiResponseBody;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.debug("End of createNewSkill()");
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_SAVE);
        }
        logger.debug("End of createNewSkill()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody deleteSkill(User user, Skill skill) {
        logger.debug("Start of deleteSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            skillRepository.delete(skill);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_DELETED);
            logger.error("End of deleteSkill");
        }catch (Exception e){
            logger.error("Exception in updateSkill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_DELETED);
        }
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateSkill(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Skill> skill = presentUser.getSkill();
            skill.forEach(e -> {
                List<Skill> newSkill = apiRequestBody.getSkillList();
                newSkill.forEach(f -> {
                    if (e.getSkillId().equals(f.getSkillId())) {
                        if (!f.getSkillTitle().equals("") && f.getSkillTitle() != null)
                            e.setSkillTitle(f.getSkillTitle());
                    }
                });
                newSkill.forEach(f -> {
                    if (f.getSkillId() == null) {
                        f.setUser(presentUser);
                        skillRepository.save(f);
                    }
                });
            });
            skillRepository.saveAll(skill);
            apiResponseBody.setMessage(AppConstants.SKILL_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateSkill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_UPDATED);
        }
        logger.debug("End of updateSkill()");
        return apiResponseBody;
    }

}
