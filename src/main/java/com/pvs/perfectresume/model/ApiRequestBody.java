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
    private List<Language> language;
    private List<Skill> skill;
    private List<ActivityCertification> activityCertification;
}
