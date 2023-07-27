package com.pvs.perfectresume.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="tb_address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

    private String pin;
    private String town;
    private String city;
    private String district;
    private String state;
    private String country;
    private String addressType;

    @OneToOne()
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user;
}
