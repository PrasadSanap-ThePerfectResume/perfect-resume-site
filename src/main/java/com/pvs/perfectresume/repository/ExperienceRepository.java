package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Experience;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Experience findByExperienceIdAndUser(Integer experienceId, User user);
}
