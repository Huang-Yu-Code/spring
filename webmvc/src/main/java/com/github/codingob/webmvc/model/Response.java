package com.github.codingob.webmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean status;
    private String info;
    private Object data;
}
