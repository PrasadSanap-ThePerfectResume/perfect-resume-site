package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Skill;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Skill findBySkillIdAndUser(Integer skillId, User user);
}
