package com.example.securitytest.aspect;

import com.example.securitytest.db.MessageLogStorage;
import com.example.securitytest.model.MessageLog;
import com.example.securitytest.model.RequestDTO;
import com.example.securitytest.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class LoggingAspect {

    private final MessageLogStorage messageLogStorage;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public LoggingAspect(MessageLogStorage messageLogStorage) {
        this.messageLogStorage = messageLogStorage;
    }

    @Before("within(com.example.securitytest.controller.*)")
    public void endpointBefore(JoinPoint p) {
        try {
            System.out.println("LOGGING ASPECT BEFORE START");
            System.out.println(p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " START");
            Object[] signatureArgs = p.getArgs();

            if (signatureArgs[0] != null) {
                final RequestDTO requestDTO = (RequestDTO) signatureArgs[0];
                final MessageLog messageLog = new MessageLog();
                messageLog.setType("REQUEST");
                messageLog.setMessageBody(mapper.writeValueAsString(requestDTO));
                messageLogStorage.log(messageLog);
                System.out.println("REQUEST LOG ADDED");
            } else {
                System.out.println("NOTHING TO LOG");
            }
            System.out.println("LOGGING ASPECT BEFORE END");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterReturning(value = "within(com.example.securitytest.controller.*)", returning = "returnValue")
    public void endpointAfterReturning(JoinPoint p, Object returnValue) {
        try {
            System.out.println("LOGGING ASPECT AFTER RETURNING START");
            if (returnValue != null) {
                final ResponseDTO responseDTO = (ResponseDTO) returnValue;
                final MessageLog messageLog = new MessageLog();
                messageLog.setMessageBody(mapper.writeValueAsString(responseDTO));
                messageLog.setType("RESPONSE");
                messageLog.setStatus(responseDTO.getStatus());
                messageLogStorage.log(messageLog);
                System.out.println("RESPONSE LOG ADDED");
            } else {
                System.out.println("NOTHING TO LOG");
            }
            System.out.println("LOGGING ASPECT AFTER RETURNING END");

            messageLogStorage.print();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterThrowing(pointcut = ("within(com.example.securitytest.controller.*)"), throwing = "e")
    public void endpointAfterThrowing(JoinPoint p, Exception e) {
        System.out.println("LOGGING ASPECT AFTER THROWING START");
        final MessageLog messageLog = new MessageLog();
        messageLog.setType("EXCEPTION");
        messageLog.setStatus("ERROR");
        messageLog.setMessageBody(e.getMessage());
        messageLogStorage.log(messageLog);
        System.out.println("EXCEPTION LOG ADDED");
        System.out.println("LOGGING ASPECT AFTER THROWING END");

        messageLogStorage.print();
    }

}
