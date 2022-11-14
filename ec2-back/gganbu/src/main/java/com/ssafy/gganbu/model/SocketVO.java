package com.ssafy.gganbu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// Data 어노테이션은 getter, setter를 자동 생성합니다.
@Data

// AllArgsConstructor 어노테이션은 생성자를 자동 생성합니다.
@AllArgsConstructor
public class SocketVO {
    // 유저의 이름을 저장하기 위한 변수
    private String patientId;

    // 메시지의 내용을 저장하기 위한 변
    private String task;

    private Long status;

    public SocketVO() {

    }
}
