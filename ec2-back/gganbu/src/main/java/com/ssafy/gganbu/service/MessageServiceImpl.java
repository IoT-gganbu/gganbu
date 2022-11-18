package com.ssafy.gganbu.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Value("${COOLSMS_API_KEY}")
     private String COOLSMS_API_KEY;
    @Value("${COOLSMS_PHONE}")
     private String COOLSMS_PHONE;
    @Value("${COOLSMS_SECRET}")
    private String COOLSMS_SECRET;


    @Override
//    coolsms를 통해 문자 전송
    public boolean sendMessage(String phone, String message) {
        System.out.println("MessageServiceImpl.sendMessage");
        System.out.println("phone : " + phone);
        System.out.println("message : " + message);
        System.out.println("COOLSMS_API_KEY : " + COOLSMS_API_KEY);
        System.out.println("COOLSMS_PHONE : " + COOLSMS_PHONE);
        System.out.println("COOLSMS_SECRET : " + COOLSMS_SECRET);
        Message coolsms = new Message(COOLSMS_API_KEY, COOLSMS_SECRET);
        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone);
        params.put("from", COOLSMS_PHONE);
        params.put("type", "SMS");
        params.put("text", message);
        params.put("app_version", "test app 1.2"); // application name and version
        System.out.println("MessageServiceImpl.sendMessage params : " + params.toString());
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            log.info(obj.toString());
            return true;
        } catch (CoolsmsException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error(String.valueOf(e.getCode()));
            return false;
        }
    }
}
