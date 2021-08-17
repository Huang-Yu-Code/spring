package com.github.codingob.web.mvc.handler;

import com.github.codingob.web.mvc.dto.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public Response maxUploadSizeExceededException() {
        return Response.fair("上传失败(文件太大)");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Response unauthorizedException() {
        return Response.fair("操作失败(权限不足)");
    }

}
