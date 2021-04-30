package com.mng.Controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    public static final String PATH = "/error";

    @RequestMapping(PATH)
    public String handle(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorName = "Error";
        String errorDescription = "Something went wrong, please try again later.";
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode >= 400) {
                errorName = String.valueOf(statusCode);
                HttpStatus httpStatus = HttpStatus.resolve(statusCode);
                if (httpStatus != null) {
                    errorDescription = httpStatus.getReasonPhrase();
                }
            }
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorDescription = "The page you have requested does not exist.";
            }
        }
        request.setAttribute("error_name", errorName);
        request.setAttribute("error_description", errorDescription);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
