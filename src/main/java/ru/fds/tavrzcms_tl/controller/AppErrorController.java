package ru.fds.tavrzcms_tl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class AppErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        model.addAttribute("status", statusCode);
        String message = "";
        if(Objects.nonNull(exception)){
            message = exception.getMessage();
        }
        model.addAttribute("message", message);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
