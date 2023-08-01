package com.pvs.perfectresume.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_language")
@Getter
@Setter
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer languageId;

    private String langTitle;
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
