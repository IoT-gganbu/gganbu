package com.ssafy.gganbu.service;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientRepository;
import com.ssafy.gganbu.db.repository.StaffRepository;
import com.ssafy.gganbu.exception.NoChangeExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service("StaffService")
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    QrService qrService;
    @Override
    public Staffs login(String id, String password) {
        log.info("StaffServiceImpl.login");
        return staffRepository.findByIdAndPassword(id, password).orElseThrow(()-> new NoSuchElementException("staff not found"));
    }

    @Override
    public Patients receipt(String residentNo) throws NoChangeExeption, WriterException, IOException {
        log.info("StaffServiceImpl.receipt");
        // 나중에 Optional로 변경해야함.
        Patients patient = patientRepository.findByResidentNo(residentNo).orElseThrow(()-> new NoSuchElementException("patient not found"));
        // null 체크
        if(patient == null) {
            throw new NoSuchElementException("no patient found");
        }
        // 이미 isCheckup이 true라면?
        if(Boolean.TRUE.equals(patient.getIsCheckup())){
            throw new NoChangeExeption("aleady checked");
        }
        // isCheckup을 true로 변환
        patient.setIsCheckup(true);
        // history에 추가
        // 이미 접수를 완료한 상황이니 접수는 완료처리해준다.
        addHistoryAndStatus(patient, 1L,4);
        // 접수가 끝났으니 다음 검진에 대한 히스토리를 만들어준다.
        addHistoryAndStatus(patient, 2L,0);

        patientRepository.save(patient);
        log.info("make QR code");
        BitMatrix qr = qrService.getQrCode(patient.getPatientId().toString());
        String path = qrService.createQrImage(qr, patient);
        return patient;
    }

    private void addHistoryAndStatus(Patients patient, Long task, Integer status) {
        PatientProgressHistory history = new PatientProgressHistory();
        history.setPatient(patient);
        history.setTaskChecktitle(taskService.getTask(task));
        history.setPatientStatus(status);
        historyRepository.save(history);
    }

    @Override
    public Long progress(Long patientId) {
        log.info("StaffServiceImpl.progress");
        // userId 기반으로 patient 찾기
        Patients patient = patientRepository.findByPatientId(patientId).orElseThrow(()-> new NoSuchElementException("patient not found"));
        if(patient == null) {
            throw new NoSuchElementException("no patient found");
        }
        // patient 기반으로 progress 찾기
        List<PatientProgressHistory> list = historyRepository.findByPatientOrderByTaskChecktitleAsc(patient).orElseThrow(()-> new NoSuchElementException("check list not found"));
        // 현재 로직에선 가장 마지막값을 기준으로 모달에 표시해준다.
        // 이젠 status가 4일경우에만 size -1, 3이하일경우엔 -2를 보내줘야할거같다.
//        if(list.get(list.size()-1).getPatientStatus() == 4) {
//            return list.get(list.size()-1).getTaskChecktitle().getTcId();
//        } else {
            return list.get(list.size()-2).getTaskChecktitle().getTcId();
//        }
    }
}

