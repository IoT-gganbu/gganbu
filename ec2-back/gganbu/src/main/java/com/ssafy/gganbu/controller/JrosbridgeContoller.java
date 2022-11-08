//package com.ssafy.gganbu.controller;
//
//import edu.wpi.rail.jrosbridge.Ros;
//import edu.wpi.rail.jrosbridge.Service;
//import edu.wpi.rail.jrosbridge.Topic;
//import edu.wpi.rail.jrosbridge.messages.Message;
//import edu.wpi.rail.jrosbridge.services.ServiceRequest;
//import edu.wpi.rail.jrosbridge.services.ServiceResponse;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class JrosbridgeContoller {
//    Ros ros = new Ros("localhost");
//	ros.connect();
//
//    Topic echo = new Topic(ros, "/echo", "std_msgs/String");
//    Message toSend = new Message("{\"data\": \"hello, world!\"}");
//	echo.publish(toSend);
//
//    Topic echoBack = new Topic(ros, "/echo_back", "std_msgs/String");
//	echoBack.subscribe(new TopicCallback() {
//        @Override
//        public void handleMessage(Message message) {
//            System.out.println("From ROS: " + message.toString());
//        }
//    });
//
//    Service addTwoInts = new Service(ros, "/add_two_ints", "rospy_tutorials/AddTwoInts");
//
//    ServiceRequest request = new ServiceRequest("{\"a\": 10, \"b\": 20}");
//    ServiceResponse response = addTwoInts.callServiceAndWait(request);
//	System.out.println(response.toString());
//
//	ros.disconnect();
//}
