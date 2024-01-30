package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Education;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Education findByEducationIdAndUser(Integer educationId, User user);
}
