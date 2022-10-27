package com.ssafy.gganbu.service;

import com.ssafy.gganbu.db.entity.Staffs;

public interface StaffService {
    public Staffs login(String id, String password);
}
