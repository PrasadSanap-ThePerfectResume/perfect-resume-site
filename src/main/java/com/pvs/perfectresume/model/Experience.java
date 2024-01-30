package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_experience")
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer experienceId;

    private String title;
    private String employmentType; //Full-time,Part-time,Self-employed,Freelance,Internship,Trainee
    private String companyName;
    private String location;
    private String workingMode; //On-site,Hybrid,Remote
    private String startMonth;
    private boolean presentCompany;
    private int startYear;
    private int endYear;
    private String endMonth;
    private String skills;
    private String description;
    /*Give the option to user add more achievements and project while making frontend development*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "experience")
    private List<Achievement> achievements;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "experience")
    private List<Project> projects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

}
