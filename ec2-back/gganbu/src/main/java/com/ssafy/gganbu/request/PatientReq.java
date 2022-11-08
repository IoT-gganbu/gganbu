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
    @ApiModelProperty(value = "환자 이름", required = true, example = "김철수")
    private String name;

    @ApiModelProperty(value = "환자 성별", required = true, example = "0")
    private Integer gender;

    @ApiModelProperty(value = "환자 전화번호", required = true, example = "010-1234-1234")
    private String tel;

    @ApiModelProperty(value = "환자 주민번호", required = true, example = "990909-9999999")
    private String residentNo;

}
