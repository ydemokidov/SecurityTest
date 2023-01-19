package com.example.securitytest.filter;

import com.example.securitytest.model.RequestDTO;
import com.example.securitytest.service.TokenValidatorService;
import com.example.securitytest.util.RequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Order(2)
@Component
public class CustomValidateTokenFilter extends GenericFilterBean {

    private final TokenValidatorService tokenValidatorService;

    @Autowired
    public CustomValidateTokenFilter(TokenValidatorService tokenValidatorService) {
        this.tokenValidatorService = tokenValidatorService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final RequestWrapper wrapper = new RequestWrapper(request);
        final byte[] requestBodyBytes = StreamUtils.copyToByteArray(wrapper.getInputStream());

        final ObjectMapper mapper = new ObjectMapper();
        final RequestDTO requestDTO = mapper.readValue(requestBodyBytes,RequestDTO.class);

        boolean tokenValid = tokenValidatorService.validateToken(requestDTO.getToken());

        if(tokenValid){
            filterChain.doFilter(wrapper,servletResponse);
        }else{
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

}
