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
@ApiModel(value = "검진 Request", description = "검진 완료 등록을 위한 Request")
public class CheckUpReq {
    @ApiModelProperty(value = "환자 id", required = true, example = "1")
    private Long patientId;
    @ApiModelProperty(value = "검사 id", required = true, example = "1")
    private Long tcId;
}
