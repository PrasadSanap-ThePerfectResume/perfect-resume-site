package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tb_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //User name should be email and its verified by OTP
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
    @JsonIgnore
    private OTPValidation otpValidation;

    @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
    @JsonIgnore
    private Address address;
}
