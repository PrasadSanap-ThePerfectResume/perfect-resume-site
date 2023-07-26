package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="tb_otp_validation")
@Getter
@Setter
@ToString
public class OTPValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long otpValidationId;

    private String username;
    private String otp;
    private boolean verify=false;

    @OneToOne
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user;
}
