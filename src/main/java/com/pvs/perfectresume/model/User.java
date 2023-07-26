package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="tb_user")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //User name should be email and its verified by OTP
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
    private OTPValidation otpValidation;
}
