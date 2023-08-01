package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetails {
    private String recipient;
    private String emailBody;
    private String subject;
    private String attachment;
}
