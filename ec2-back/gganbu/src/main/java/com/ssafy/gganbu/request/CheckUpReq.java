package com.ssafy.gganbu.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckUpReq {
    private Long patientId;

    private Long tcId;
}
