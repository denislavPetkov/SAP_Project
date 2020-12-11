package sap.project.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error"; // default

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                errorPage = "error/404";
                LOGGER.error("Error 404");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorPage = "error/500";
                LOGGER.error("Error 500");
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                errorPage = "error/403";
                LOGGER.error("Error 403");
            }
        }
        return errorPage;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

