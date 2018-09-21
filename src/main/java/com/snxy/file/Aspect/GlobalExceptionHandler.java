package com.snxy.file.Aspect;

import com.snxy.common.response.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by 24398 on 2018/9/4.
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultData exceptionHandler(Exception e){

        log.error("全局异常处理 :  [{}]",e);
        return ResultData.fail(e.getMessage());
    }

}
