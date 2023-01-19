package com.example.securitytest.db;

import com.example.securitytest.model.MessageLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class MessageLogStorage {

    private List<MessageLog> logStorage;

    public MessageLogStorage() {
        this.logStorage = new ArrayList<>();
    }

    public void log(MessageLog logRecord){
        logStorage.add(logRecord);
    }

    public void print(){
        for(MessageLog log : logStorage){
            System.out.println(log);
        }
    }

}
