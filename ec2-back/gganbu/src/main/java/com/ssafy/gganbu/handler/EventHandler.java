package com.ssafy.gganbu.handler;

import com.ssafy.gganbu.event.CheckupEvent;
import com.ssafy.gganbu.model.SocketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void senddata(CheckupEvent checkupEvent) {
        System.out.println("checkupEvent.getName() = " + checkupEvent.getName());
        simpMessagingTemplate.convertAndSend("/ros/1", new SocketVO("a", "b"));
        System.out.println("EventHandler.senddata");
    }
}
