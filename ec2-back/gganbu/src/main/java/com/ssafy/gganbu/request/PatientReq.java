package com.ssafy.gganbu.request;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientReq {
    private String name;

    private Integer gender;

    private String tel;

    private String residentNo;

}
