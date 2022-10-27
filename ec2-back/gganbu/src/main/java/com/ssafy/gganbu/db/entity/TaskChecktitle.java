package com.ssafy.gganbu.db.entity;

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
    private Long tcId;

    @Column(name = "check_title", nullable = false)
    private String checkTitle;

}