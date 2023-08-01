package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
