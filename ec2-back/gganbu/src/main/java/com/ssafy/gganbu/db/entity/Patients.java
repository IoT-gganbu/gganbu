package com.ssafy.gganbu.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patient")
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "gender", nullable = false)
    private Integer gender;
    @Column(name = "tel", nullable = false)
    private String tel;
    @Column(name = "resident_no", nullable = false)
    private String residentNo;
    @Column(name = "is_checkup", nullable = false, columnDefinition = "boolean default false")
    private Integer isCheckup;
}
