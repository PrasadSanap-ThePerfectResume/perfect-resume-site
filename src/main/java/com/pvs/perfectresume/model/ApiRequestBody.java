package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class ApiRequestBody {
    private User user;
    private String otp;
    private Address address;
}
