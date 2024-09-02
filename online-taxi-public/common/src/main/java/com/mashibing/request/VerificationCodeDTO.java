package com.mashibing.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {
    private String passengerPhone;
    private String verificationcode;
}
