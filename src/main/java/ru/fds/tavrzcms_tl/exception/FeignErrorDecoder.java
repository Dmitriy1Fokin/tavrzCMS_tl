package ru.fds.tavrzcms_tl.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {


        switch (response.status()){
            case 400:
            case 404:
            {
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Not Found");
            }
            default:
                return new Exception(response.reason());
        }
    }

}
