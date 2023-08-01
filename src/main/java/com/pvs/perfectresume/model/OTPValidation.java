package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_otp_validation")
@Getter
@Setter
public class OTPValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long otpValidationId;

    private String username;
    private String otp;
    private Timestamp otpGenerateDT;
    private boolean verify = false;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
