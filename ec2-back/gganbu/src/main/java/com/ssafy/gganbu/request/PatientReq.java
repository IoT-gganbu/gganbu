package com.ssafy.gganbu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "환자 Request", description = "환자 등록을 위한 Request")
public class PatientReq {
    @ApiModelProperty(example = "이름")
    private String name;

    @ApiModelProperty(example = "성별")
    private Integer gender;

    @ApiModelProperty(example = "전화번호")
    private String tel;

    @ApiModelProperty(example = "주민 번호")
    private String residentNo;

}
