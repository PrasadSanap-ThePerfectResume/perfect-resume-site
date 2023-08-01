package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
}
