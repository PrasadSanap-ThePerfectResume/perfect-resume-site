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
    private Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

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
            List<Skill> skill = apiRequestBody.getSkill();
            skill.stream().forEach(e -> {
                e.setUser(presentUser);
            });
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
    public ApiResponseBody updateSkill(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Skill> skill = presentUser.getSkill();
            skill.stream().forEach(e -> {
                List<Skill> newSkill = apiRequestBody.getSkill();
                newSkill.stream().forEach(f -> {
                    if (e.getSkillId() == f.getSkillId()) {
                        if (!f.getSkillTitle().equals("") && f.getSkillTitle() != null)
                            e.setSkillTitle(f.getSkillTitle());
                        if (f.getLevel() <= 100 && f.getLevel() >= 0)
                            e.setLevel(f.getLevel());
                    }
                });
                newSkill.stream().forEach(f -> {
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
