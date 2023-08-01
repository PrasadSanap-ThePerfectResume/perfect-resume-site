package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
