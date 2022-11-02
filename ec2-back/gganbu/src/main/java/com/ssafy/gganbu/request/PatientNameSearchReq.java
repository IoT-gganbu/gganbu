package com.ssafy.gganbu.request;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PatientNameSearchReq {
    private String name;
    private Pageable pageable;
}