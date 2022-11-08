package com.ssafy.gganbu.handler;

import com.sun.security.auth.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import static com.ssafy.gganbu.GganbuApplication.principalList;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        final String randomId = UUID.randomUUID().toString();
//        String userId = request.getHeaders().getFirst("userId");
        System.out.println("User with ID '{}' opened the page "+ randomId);
       Principal principal =  new UserPrincipal(randomId);
        principalList.add(principal);
        return principal;
    }
}
