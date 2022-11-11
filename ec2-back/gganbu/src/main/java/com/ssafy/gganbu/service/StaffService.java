package com.ssafy.gganbu.service;

import com.google.zxing.WriterException;
import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;

import java.io.IOException;

public interface StaffService {
    public Staffs login(String id, String password);

    public String receipt(String residentNo) throws NoChangeExeption, WriterException, IOException;

    Long progress(Long userId);
}
