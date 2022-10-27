package com.ssafy.gganbu.service;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientReqository;
import com.ssafy.gganbu.db.repository.StaffRepository;
import com.ssafy.gganbu.exception.NoChangeExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service("StaffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PatientReqository patientRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    QrService qrService;
    @Override
    public Staffs login(String id, String password) {
        System.out.println("StaffServiceImpl.login");
        Staffs staff = staffRepository.findByIdAndPassword(id, password).orElseThrow(()-> new NoSuchElementException("staff not found"));
        return staff;
    }

    @Override
    public String receipt(String residentNo) throws NoChangeExeption, WriterException, IOException {
        System.out.println("StaffServiceImpl.receipt");
        // 나중에 Optional로 변경해야함.
        Patients patient = patientRepository.findByResidentNo(residentNo);
        // null 체크
        if(patient == null) {
            throw new NoSuchElementException("no patient found");
        }
        // 이미 isCheckup이 true라면?
        if(patient.getIsCheckup()){
            throw new NoChangeExeption("aleady checked");
        }
        // isCheckup을 true로 변환
//        patient.setIsCheckup(true);
        patientRepository.save(patient);
        // 추 후에 QR코드 전송방식으로 변경해줘야함.
        System.out.println("make QR code");
        BitMatrix qr = qrService.getQrCode(patient.getPatientId().toString());
        // bitMatrix를 png로 변환
        String path = qrService.createQrImage(qr, patient);
        return path;
    }

    @Override
    public Object progress(Long patientId) {
        System.out.println("StaffServiceImpl.progress");
        // userId 기반으로 patient 찾기
        Patients patient = patientRepository.findByPatientId(patientId);
        if(patient == null) {
            throw new NoSuchElementException("no patient found");
        }
        // patient 기반으로 progress 찾기
        List<PatientProgressHistory> list = historyRepository.findByPatientOrderByTaskChecktitleAsc(patient);
        // 정상출력 확인
//        for(PatientProgressHistory history : list) {
//            System.out.println(history.getTaskChecktitle().getTcId());
//        }
        // 제일 큰 값 하나만 리턴해준다?
        Long max = list.get(list.size()-1).getTaskChecktitle().getTcId();
        return max;
    }
}

