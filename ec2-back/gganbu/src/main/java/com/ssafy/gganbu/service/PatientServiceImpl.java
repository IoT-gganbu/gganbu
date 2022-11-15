package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.PatientProgressHistory;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.TaskChecktitle;
import com.ssafy.gganbu.db.repository.HistoryRepository;
import com.ssafy.gganbu.db.repository.PatientRepository;

import java.util.Calendar;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service("patientService")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService{

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    TaskService taskService;


    public boolean checkResidentNo(String residentNo){
        log.info("service" + residentNo);
        return patientRepository.existsByResidentNo(residentNo).orElseThrow(()-> new NoSuchElementException("patient not found"));
    }

    @Override
    public List<Patients> searchPatient(String name) {
        try {
            return patientRepository.findAllByName(name).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Patients getPatient(Long patientId) {
        try {
            return patientRepository.findByPatientId(patientId).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<Patients> searchPatientWithPage(String name, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return patientRepository.findAllByName(name, pageable).orElseThrow(()-> new NoSuchElementException("patient not found"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<Patients> searchAllPatientWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
            return patientRepository.findAll(pageable);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkPatientHistory(Patients patient, TaskChecktitle taskChecktitle) {
        PatientProgressHistory patientProgressHistory = historyRepository.findByPatientAndTaskChecktitle(patient, taskChecktitle).orElseThrow(() -> new NoSuchElementException("patientProgressHistory not found"));
        // 이동전(0), 이동중(1), 대기중(2), 검사중(3), 검사완료(4)
        if(patientProgressHistory.getPatientStatus() == 4)
            return false;
        else if(patientProgressHistory.getPatientStatus() == 3)
            return true;
        else{
            log.error("this patient is not staus 3 or 4");
            return false;
        }
    }

    @Override
    public String updateStatus(Long patientId, Long tcId, int status) {
        Patients patient = getPatient(patientId);
        TaskChecktitle taskChecktitle = taskService.getTask(tcId);
        PatientProgressHistory patientProgressHistory = historyRepository.findByPatientAndTaskChecktitle(patient, taskChecktitle).orElseThrow(() -> new NoSuchElementException("patientProgressHistory not found"));
        // 검진 완료라면
        if(status ==4) {
            TaskChecktitle nextTask = taskService.getTask(taskChecktitle.getTcId()+1L);
            // 이미 처리가 된 경우
            if (patientProgressHistory.getPatientStatus() == 4) {
                return "duplicated";
            }
            // 상태 변경
            patientProgressHistory.setPatientStatus(status);
            historyRepository.save(patientProgressHistory);
            // 중복 컬럼 처리
            if (existedHistory(patient, nextTask)) {
                return "duplicated";
            }
            addHistory(patient, nextTask, 0);
            return "SUCCESS";
        // 검진 시작시
        }else{
            patientProgressHistory.setPatientStatus(status);
            historyRepository.save(patientProgressHistory);
            return "SUCCESS";
        }
    }

    public static int getAge(int birthYear, int birthMonth, int birthDay) {
        Calendar current = Calendar.getInstance();

        int currentYear = current.get(Calendar.YEAR);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentDay = current.get(Calendar.DAY_OF_MONTH);

        // 만 나이 구하기 2022-1995=27 (현재년-태어난년)
        int age = currentYear - birthYear;
        // 만약 생일이 지나지 않았으면 -1
        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
            age--;
        // 5월 26일 생은 526
        // 현재날짜 5월 25일은 525
        // 두 수를 비교 했을 때 생일이 더 클 경우 생일이 지나지 않은 것이다.
        return age;
    }

    @Override
    public PatientProgressHistory getHistory(Patients patient, TaskChecktitle taskChecktitle) {
        PatientProgressHistory patientProgressHistory = historyRepository.findByPatientAndTaskChecktitle(patient, taskChecktitle).orElseThrow(() -> new NoSuchElementException("patientProgressHistory not found"));

        return patientProgressHistory;
    }


    @Override
    public boolean existedHistory(Patients patient, TaskChecktitle taskChecktitle) {
        boolean isExisted = historyRepository.existsByPatientAndTaskChecktitle(patient, taskChecktitle);
        return isExisted;
    }

    @Override
    public void addHistory(Patients patient, TaskChecktitle taskChecktitle, int status) {
        PatientProgressHistory history = new PatientProgressHistory();
        history.setPatient(patient);
        history.setTaskChecktitle(taskChecktitle);
        history.setPatientStatus(status);
        historyRepository.save(history);
    }

    @Override
    public boolean addPatient(String name, Integer gender, String tel, String residentNo) {
        if(checkResidentNo(residentNo)){
            return false;
        }else {
            Patients res = new Patients();
            res.setName(name);
            res.setGender(gender);
            res.setTel(tel);
            res.setResidentNo(residentNo);
            int newage = 0;
            // 2000년생 이상인 경우
            if (Integer.parseInt(residentNo.substring(7, 8)) == 3 || Integer.parseInt(residentNo.substring(7, 8)) == 4) {
                newage = 2000 + Integer.parseInt(residentNo.substring(0, 2));
                // 1900년생인 경우
            } else {
                newage = 1900 + Integer.parseInt(residentNo.substring(0, 2));
            }
            // 만나이 구하는 함수
            int age = getAge(newage,
                    Integer.parseInt(residentNo.substring(2, 4)), Integer.parseInt(residentNo.substring(4, 6)));
            res.setAge(age);
            patientRepository.save(res);
            return true;
        }
    }



    public Patients receipt(Patients patients){

        try{
            return patientRepository.save(patients);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
