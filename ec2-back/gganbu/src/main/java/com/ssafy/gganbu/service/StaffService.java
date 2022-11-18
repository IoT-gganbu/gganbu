package com.ssafy.gganbu.service;

import com.google.zxing.WriterException;
import com.ssafy.gganbu.db.entity.Patients;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;

import java.io.IOException;

public interface StaffService {
    public Staffs login(String id, String password);

    public Patients receipt(String residentNo) throws NoChangeExeption, WriterException, IOException;

    Long progress(Long userId);
}
