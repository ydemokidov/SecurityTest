package com.example.securitytest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {

    private int id;

    private String token;

    private String body;

}
