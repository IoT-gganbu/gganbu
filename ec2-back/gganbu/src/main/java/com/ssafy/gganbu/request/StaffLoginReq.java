package com.ssafy.gganbu.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class StaffLoginReq {
    @ApiModelProperty(value = "staff id",required = true, example = "ssafy")
    private String id;
    @ApiModelProperty(value = "staff password", required = true, example = "ssafy")
    private String password;
}
