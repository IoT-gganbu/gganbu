package com.ssafy.gganbu.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patient")
@ToString
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    @ApiModelProperty(example = "환자 id")
    private Long patientId;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(example = "이름")
    private String name;

    @Column(name = "age", nullable = false)
    @ApiModelProperty(example = "나이")
    private Integer age;

    @Column(name = "gender", nullable = false)
    @ApiModelProperty(example = "성별")
    private Integer gender;

    @Column(name = "tel", nullable = false)
    @ApiModelProperty(example = "전화번호")
    private String tel;

    @Column(name = "resident_no", nullable = false, unique = true)
    @ApiModelProperty(example = "주민번호")
    private String residentNo;


    @Column(name = "is_checkup", nullable = false, columnDefinition = "boolean default false")
    @ApiModelProperty(example = "검진 여부")
    private Boolean isCheckup = false;
}
