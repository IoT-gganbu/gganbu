package com.ssafy.gganbu.handler;

import com.ssafy.gganbu.event.CheckupEvent;
import com.ssafy.gganbu.model.SocketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    int num = 0;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void senddata(CheckupEvent checkupEvent) {
        SocketVO socketVO = checkupEvent.getSocketVO();
        System.out.println("socketVO = " + socketVO.toString());
        simpMessagingTemplate.convertAndSend("/ros/1", new SocketVO(socketVO.getPatientId(), socketVO.getTask()));
        System.out.println("event End");
    }
}
