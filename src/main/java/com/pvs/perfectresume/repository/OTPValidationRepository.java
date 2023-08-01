package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.OTPValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OTPValidationRepository extends JpaRepository<OTPValidation, Integer> {
    OTPValidation findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_otp_validation SET otp= ?1 WHERE otp_generatedt<=now()-INTERVAL ?2 Minute", nativeQuery = true)
    void updateRecords(String resetValue, Integer interval);
}
