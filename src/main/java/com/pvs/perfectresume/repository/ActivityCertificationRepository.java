package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.ActivityCertification;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityCertificationRepository extends JpaRepository<ActivityCertification, Integer> {
//    ActivityCertification findByIdAndUser(Integer actCerId, User user);

    ActivityCertification findByActCerIdAndUser(Integer actCerId, User user);
}
