package com.pvs.perfectresume.service;

import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.model.Skill;
import com.pvs.perfectresume.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SkillService {
    ApiResponseBody updateSkill(ApiRequestBody apiRequestBody);

    ApiResponseBody saveSkill(ApiRequestBody apiRequestBody);

    ApiResponseBody updateSingleSkill(User user, Skill skill);

    ApiResponseBody createNewSkill(User user, Skill skill);

    ApiResponseBody deleteSkill(User user,Skill skill);
}
