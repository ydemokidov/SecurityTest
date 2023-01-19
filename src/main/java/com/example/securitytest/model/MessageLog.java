package com.example.securitytest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageLog {

    private String status;

    private String type;

    private String messageBody;

    @Override
    public String toString() {
        return "MessageLog{" +
                "status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }

}
