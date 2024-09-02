package com.mashibing.dto;

import com.mashibing.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public  class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public static  ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData("");
    }

    public static <T> ResponseResult success(T data){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setCode(CommonStatusEnum.FAIL.getCode()).setMessage(CommonStatusEnum.FAIL.getValue()).setData(data);
    }

    public static ResponseResult fail(int code, String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }
}

