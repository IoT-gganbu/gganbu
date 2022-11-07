package com.ssafy.gganbu.event;

import com.ssafy.gganbu.model.SocketVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CheckupEvent {
    private SocketVO socketVO;

    public CheckupEvent(SocketVO socketVO) {
        this.socketVO = socketVO;
    }

}