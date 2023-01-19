package com.example.securitytest.util;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.IOException;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(this.body);
    }

}
