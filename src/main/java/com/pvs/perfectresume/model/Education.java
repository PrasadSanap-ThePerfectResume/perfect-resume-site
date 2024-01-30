package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_education")
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer educationId;

    private String university;
    private String college;
    private String stream;
    private int startYear;
    private int endYear;
    private String startMonth;
    private boolean presentCollege;
    private String endMonth;
    private String percentage;
    private String educationMode; //Full time,Part time,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

}
