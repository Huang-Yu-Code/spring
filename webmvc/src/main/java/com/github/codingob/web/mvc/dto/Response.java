package com.github.codingob.web.mvc.dto;

import lombok.Data;

/**
 * Response响应
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
public class Response {
    private boolean status;
    private String info;
    private Object data;

    private Response() {

    }

    private Response(Object data) {
        this.status = true;
        this.info = "success";
        this.data = data;
    }

    private Response(boolean status) {
        this.status = status;
    }

    public static Response success(){
        return new Response(null);
    }

    public static Response success(Object data){
        return new Response(data);
    }

    public static Response fair(String info) {
        Response response = new Response();
        response.setInfo(info);
        return response;
    }

}
