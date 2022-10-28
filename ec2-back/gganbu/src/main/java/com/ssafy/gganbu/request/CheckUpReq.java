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
    @ApiModelProperty(example = "환자 id")
    private Long patientId;

    @ApiModelProperty(example = "완료된 검진 번호")
    private Long tcId;
}
