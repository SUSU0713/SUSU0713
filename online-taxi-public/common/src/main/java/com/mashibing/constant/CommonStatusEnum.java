package com.mashibing.constant;

import lombok.Getter;

public enum CommonStatusEnum {
    SUCCESS(1, "success"),
    FAIL(0, "error"),
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),
    TOKEN_ERROR(1199, "token错误"),
    USER_NOT_EXIST(1200, "当前用户不存在"),
    PRICE_RULE_EMPTY(1300, "计价规则不存在"),
    MAP_DISTRICT_ERROR(1400, "请求地图错误"),
    DRIVER_INFO_INSERT_ERROR(1500, "司机信息插入失败")
    ;


    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
