package com.sepideh.notebook.exeption;

import com.sepideh.notebook.dto.response.GenericRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
                "Request failed", ex.getMessage()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Illegal Exception ***********************************************************************************************
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        logger.error("Exception: ", ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed", ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Data Access Exception *******************************************************************************************
    @ExceptionHandler(value = { DataAccessException.class })
    public ResponseEntity<Object> handleDataAccessException(Exception ex) {
        logger.error("Exception: ", ex);

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Request failed", ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Entity Not Found Exception **************************************************************************************
    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        logger.error("Exception: ", ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Entry not found", ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // Unsupported Encoding Exception **********************************************************************************
    @ExceptionHandler(value = { UnsupportedEncodingException.class })
    public ResponseEntity<Object> handleUnsupportedEncodingException(Exception ex) {
        logger.error("Exception: ", ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request failed", ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

    // IO Exception ****************************************************************************************************
    @ExceptionHandler(value = { IOException.class })
    public ResponseEntity<Object> handleIOException(Exception ex) {
        logger.error("Exception: ", ex.getMessage());

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Request failed", ex.getMessage()
            ),
            HttpStatus.OK
        );
    }

}
