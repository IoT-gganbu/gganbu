package com.ssafy.gganbu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class GganbuApplication {
public static List<Principal> principalList = new ArrayList<>();
public static Map<String, Principal> principalMap = new HashMap<>();
	public static void main(String[] args) {
		SpringApplication.run(GganbuApplication.class, args);
	}

}
