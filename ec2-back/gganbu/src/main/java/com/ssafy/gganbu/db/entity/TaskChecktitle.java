package com.ssafy.gganbu.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "task_checktitle")
public class TaskChecktitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tc_id", nullable = false)
    @ApiModelProperty(example = "task id")
    private Long tcId;

    @Column(name = "check_title", nullable = false)
    @ApiModelProperty(example = "검진명")
    private String checkTitle;

}