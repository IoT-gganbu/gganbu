package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Staffs;
import com.ssafy.gganbu.exception.NoChangeExeption;

public interface StaffService {
    public Staffs login(String id, String password);

    public Boolean receipt(String residentNo) throws NoChangeExeption;

    Object progress(Long userId);
}
