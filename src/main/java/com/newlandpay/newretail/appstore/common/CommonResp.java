package com.newlandpay.newretail.appstore.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CommonResp {
    private Integer code;
    private String msg;

}
