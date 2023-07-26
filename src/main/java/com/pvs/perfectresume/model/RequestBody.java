package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
public class RequestBody {
    private User user;
}
