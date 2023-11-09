package com.gihae.filestorage._core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gihae.filestorage._core.errors.exception.Exception401;
import com.gihae.filestorage._core.errors.exception.Exception403;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse response, Exception401 e) throws IOException {
        response.setStatus(e.status().value());
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        response.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse response, Exception403 e) throws IOException {
        response.setStatus(e.status().value());
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        response.getWriter().println(responseBody);
    }
}
