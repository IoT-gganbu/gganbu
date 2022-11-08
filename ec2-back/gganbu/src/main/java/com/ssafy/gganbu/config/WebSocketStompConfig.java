package com.ssafy.gganbu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//  클래스 선언 앞에 작성합니다.
//  이 어노테이션은 해당 클래스가 Bean의 설정을 할 것이라는 것을 나타냅니다.
@Configuration
// WebSocket 서버를 활성화하는 데 사용됩니다.
@EnableWebSocketMessageBroker
// WebSocketMessageBrokerConfigurer - 웹 소켓 연결을 구성하기 위한 메서드를 구현하고 제공합니다
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
//    클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록합니다.
//  엔드 포인트 구성에 withSockJS ()를 사용합니다.
//  SockJS는 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용됩니다.
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//      addEndpoint() - 클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록합니다.
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
//    메시지 브로커를 활성화하고 메시지 브로커가 처리할 메시지의 접두사를 구성합니다.
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
//       - "/app" 시작되는 메시지가 message-handling methods으로 라우팅 되어야 한다는 것을 명시합니다.
        registry.setApplicationDestinationPrefixes("/app");
// "/topic" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의합니다.
//  메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 합니다.
        registry.enableSimpleBroker("/topic");
    }

}

