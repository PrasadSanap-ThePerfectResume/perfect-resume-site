package com.pvs.perfectresume.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseBody {
    private String message;
    private int statusCode;
    private String status;
}
