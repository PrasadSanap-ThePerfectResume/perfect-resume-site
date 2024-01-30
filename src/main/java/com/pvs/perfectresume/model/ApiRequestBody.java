package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiRequestBody {
    private User user;
    private String otp;
    private Address address;
    private Education education;
    private Experience experience;
    private Skill skill;
    private Language language;
    private ActivityCertification  activityCertification;
    private List<Language> languageList;
    private List<Skill> skillList;
    private List<ActivityCertification> activityCertificationList;
}
