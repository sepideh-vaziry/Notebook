package com.sepideh.notebook.exeption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    // General Exception ***********************************************************************************************
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        logger.error("Exception: ", ex);

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Request failed",
                ex.getMessage()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Illegal Exception ***********************************************************************************************
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Data Access Exception *******************************************************************************************
    @ExceptionHandler(value = { DataAccessException.class })
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        logger.error(ex.getCause().getMessage());

        String errorMessage = ex.getMostSpecificCause().getMessage();
        if (errorMessage == null) {
            errorMessage = ex.getMessage();
        }

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed",
                ex.getMostSpecificCause().getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Entity Not Found Exception **************************************************************************************
    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Entry not found",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Unsupported Encoding Exception **********************************************************************************
    @ExceptionHandler(value = { UnsupportedEncodingException.class })
    public ResponseEntity<Object> handleUnsupportedEncodingException(Exception ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // IO Exception ****************************************************************************************************
    @ExceptionHandler(value = { IOException.class })
    public ResponseEntity<Object> handleIOException(Exception ex) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Request failed",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Method Argument Not Valid Exception *****************************************************************************
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(errorMap);
        }
        catch (JsonProcessingException e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed",
                json
            ),
            HttpStatus.OK
        );
    }

    // Username Or Password Not Correct Exception **********************************************************************
    @ExceptionHandler(UsernameOrPasswordNotCorrectException.class)
    public ResponseEntity<Object> handleUsernameOrPasswordNotCorrectException(
        UsernameOrPasswordNotCorrectException ex
    ) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "The username or password is incorrect",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Username Not Found Exception **********************************************************************
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(
        UsernameNotFoundException ex
    ) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "User not found",
                ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Token Invalid Exception *****************************************************************************************
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Object> handleTokenInvalidException(
        TokenInvalidException ex
    ) {
        logger.error(ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Token has invalid",
                ex.getMessage()
            ),
            HttpStatus.UNAUTHORIZED
        );
    }

}
