package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_project")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer projectId;

    private String projectTitle;
    private String frontEndTech;
    private String backEndTech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experienceId")
    @JsonIgnore
    private Experience experience;
}
