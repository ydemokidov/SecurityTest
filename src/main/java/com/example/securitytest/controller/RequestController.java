package com.example.securitytest.controller;

import com.example.securitytest.model.RequestDTO;
import com.example.securitytest.model.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @PostMapping(path = "/request")
    public ResponseDTO processRequest(@RequestBody RequestDTO request){
        ResponseDTO response = new ResponseDTO();
        try {
            response.setBody("Response body text");
            response.setId(request.getId());
            response.setStatus("SUCCESS");
            //throw new Exception("my exception!!!!");
        }catch (Exception e){
            response.setBody(e.getMessage());
            response.setStatus("ERROR");
            response.setId(request.getId());
        }

        return response;
    }

}
