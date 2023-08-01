package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_activity_certification")
@Getter
@Setter
public class ActivityCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actCerId;
    private String actCerTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
