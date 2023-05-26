package net.javaguides.springboot.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
//@CONTROLLER ADVICE IS USED HERE TO HANDLE ALL GLOBAL AS WELL AS SPECIFIC EXCEPTION NOT LIKE JUST @EXCEPTIONHANDLER TO
//HANDLE SPECIFIC EXCEPTION AT JUST THE SPECIFIC CONTROLLER LEVEL.

//SO MOVING THE METHOD IN CONTROLLLER THAT WAS ANNOTED WITH @EXCEPTIONHANDLER TO @CONTROLLERADVICE CLASS.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(EmailAlreadyExistException emailAlreadyExistException,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                emailAlreadyExistException.getMessage(),
                webRequest.getDescription(false),
                "USER_EMAIL_ALREADY_EXIST");
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(Exception exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL SERVER ERROR");
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request){
//
//        Map<String,String> errors = new HashMap<>();
//        List<ObjectError>errorList = ex.getBindingResult().getAllErrors();
//        errorList.forEach((error)->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName,message);
//                });
//
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }

//THIS WAS USED TO ACCOMODATE @VALID ANNOTATION TO RETURN CUSTOME ERROR RESPONSE BASED ON THE CRITERIA  HERE FIELDNAME AND MESSAGE SHOWS THE SAME.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        List<ObjectError>errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
                });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}



// NOW AFTER THIS WE CAN SEE THE USE OF JAVA BEAN VALIDATION API THAT IS JUST A SPECIFICATION
//    AND THE IMPLEMENTATION OF THE VALIDATION API IS HIBERNATE VALIDATOR.



