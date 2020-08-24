package ag.pinguin.exception;

import ag.pinguin.dto.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static ag.pinguin.exception.Messages.INVALID_FIELD_VALUE;
import static ag.pinguin.exception.Messages.INVALID_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var errorMessages = new ArrayList<String>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                var fieldErr = (FieldError) error;
                errorMessages.add(String.format("'%s' %s", fieldErr.getField(), fieldErr.getDefaultMessage()));
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return super.handleExceptionInternal(ex, new ErrorResponse(errorMessages, badRequest.value()), headers,
                badRequest, request);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var badRequest = HttpStatus.BAD_REQUEST;
        return super.handleExceptionInternal(ex, new ErrorResponse(INVALID_REQUEST, badRequest.value()), headers,
                badRequest, request);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
                                                     WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            var exception = (MethodArgumentTypeMismatchException) ex;
            var badRequest = HttpStatus.BAD_REQUEST;
            var message = String.format(INVALID_FIELD_VALUE, exception.getValue(), exception.getName());
            return super.handleExceptionInternal(exception, new ErrorResponse(message, badRequest.value()), new HttpHeaders(),
                    badRequest, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var messages = ex.getConstraintViolations()
                .stream()
                .map(x -> String.format("'%s' %s", x.getPropertyPath().toString(), x.getMessage()))
                .collect(Collectors.toList());
        return super.handleExceptionInternal(ex, new ErrorResponse(messages, badRequest.value()), new HttpHeaders(), badRequest, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var notFound = HttpStatus.NOT_FOUND;
        return super.handleExceptionInternal(ex, new ErrorResponse(ex.getMessage(), notFound.value()), new HttpHeaders(),
                notFound, request);
    }
}
