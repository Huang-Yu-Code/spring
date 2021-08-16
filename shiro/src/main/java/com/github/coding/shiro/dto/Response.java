package com.github.coding.shiro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response响应
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Response {
    private boolean status;
    private String info;
    private Object data;

    public Response(boolean status){
        this.status=status;
    }

    public Response(String info) {
        this.info = info;
    }

    public Response(Object data) {
        this.status = true;
        this.data = data;
    }
}
