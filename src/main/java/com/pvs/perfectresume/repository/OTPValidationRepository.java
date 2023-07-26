package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.OTPValidation;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPValidationRepository extends JpaRepository<OTPValidation,Integer> {
    OTPValidation findByUsername(String username);
}
