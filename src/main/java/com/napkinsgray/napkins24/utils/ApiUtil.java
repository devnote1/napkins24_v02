package com.napkinsgray.napkins24.utils;

import lombok.Data;

@Data
public class ApiUtil<T> {

    private Integer status;
    private String msg;
    private T body;

    // 성공시 사용할 생성자
    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.body = body;
    }

    // 실패시 사용할 생성자
    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.body = null;
    }

}
