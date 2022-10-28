package com.ssafy.gganbu.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "staff")
public class Staffs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    @ApiModelProperty(example = "staff id")
    private Long staffId;

    @Column(name = "id", nullable = false, unique = true)
    @ApiModelProperty(example = "아이디")
    private String id;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(example = "비밀번호")
    private String password;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(example = "스태프 이름")
    private String name;

    @Column(name = "task", nullable = false)
    @ApiModelProperty(example = "담당 검진 구역 번호")
    private Integer task;


}
