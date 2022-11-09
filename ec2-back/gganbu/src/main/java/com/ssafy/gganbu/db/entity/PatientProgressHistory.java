package com.ssafy.gganbu.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patient_progress_history")
public class PatientProgressHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pph_id", nullable = false)
    @ApiModelProperty(example = "history id")
    private Long pphId;

    @ManyToOne(targetEntity = Patients.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @ApiModelProperty(example = "환자 id")
    private Patients patient;

    @ManyToOne(targetEntity = TaskChecktitle.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "tc_id")
    @ApiModelProperty(example = "검진목록 id")
    private TaskChecktitle taskChecktitle;

    // 이동전(0), 이동중(1), 대기중(2), 검사중(3), 검사완료(4)
    @Column(name = "patient_status", nullable = false, columnDefinition = "Integer default 0")
    private Integer patientStatus;

}