package com.ssafy.gganbu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResidentNoReq {
    @ApiModelProperty(value = "주민번호", required = true, example = "990909-9999999")
    private String residentNo;
}
