package com.ssafy.gganbu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "상태 변경 Request", description = "상태변경을 위한 Request")
public class StatusReq {
    @ApiModelProperty(value = "환자 id", required = true, example = "1")
    private Long patientId;
    @ApiModelProperty(value = "검사 id", required = true, example = "1")
    private Long tcId;
    @ApiModelProperty(value = "상태", required = true, example = "1")
    private int status;
}
