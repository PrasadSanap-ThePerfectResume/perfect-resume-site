package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_achievement")
@Getter
@Setter
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer achievementId;
    private String achievement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experienceId")
    @JsonIgnore
    private Experience experience;
}
