package com.trinhnx151.human_resource_management.exception;

import com.trinhnx151.human_resource_management.entity.ResponseOject;
import com.trinhnx151.human_resource_management.exception.custom.DuplicateException;
import com.trinhnx151.human_resource_management.exception.custom.ImageErrorException;
import com.trinhnx151.human_resource_management.exception.custom.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("message", "Thất bại");

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ResponseOject> handleCustomDuplicateExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseOject("500", "Duplicate", e.getMessage())
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseOject> handleCustomNotFoundExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseOject("500", "Not Found", e.getMessage())
        );
    }

    @ExceptionHandler(ImageErrorException.class)
    public ResponseEntity<ResponseOject> uploadImageErrorExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseOject("500", "Upload Fail", e.getMessage())
        );
    }

//    @ExceptionHandler({ NotFoundException.class })
//    protected ResponseEntity<Object> handleNotFound(
//            Exception ex, WebRequest request) {
//        return handleExceptionInternal(ex, "Not found",
//                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
}
