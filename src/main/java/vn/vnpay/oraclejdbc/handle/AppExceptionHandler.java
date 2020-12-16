package vn.vnpay.oraclejdbc.handle;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.vnpay.oraclejdbc.exception.GeneralException;
import vn.vnpay.oraclejdbc.dto.ResponseDTO;
import vn.vnpay.oraclejdbc.exception.SQLCustomException;


@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = {GeneralException.class})
    protected ResponseEntity<ResponseDTO> generalException(GeneralException ex, WebRequest rq) {
        final ResponseDTO responseDTO = ResponseDTO.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        logger.error("[AppExceptionHandle.GeneralException: {}]", ex.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @ExceptionHandler(value = {SQLCustomException.class})
    protected ResponseEntity<ResponseDTO> SQLCustomException(SQLCustomException ex, WebRequest rq) {
        final ResponseDTO responseDTO = ResponseDTO.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        logger.error("[AppExceptionHandle.SQLCustomException: {}]", ex.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


}
