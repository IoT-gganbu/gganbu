package com.ssafy.gganbu.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "patient_progress_history")
public class PatientProgressHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pph_id", nullable = false)
    private Long pphId;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private List<Patients> patients = new ArrayList<>();

}