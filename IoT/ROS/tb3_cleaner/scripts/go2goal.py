#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist
# from nav_msgs.msg import Odometry
from std_msgs.msg import Int32
from std_msgs.msg import Float32
from std_msgs.msg import String
#import sys, select, termios, tty
#from sensor_msgs.msg import LaserScan
#import time
from math import pow, sqrt, atan, pi#, atan2

"""
   TurtleBot3(buger) MAX SPEED
-----------------------------------
MAX Linear  Speed: 0.22(meter /sec)
MAX Angular Speed: 2.82(radian/sec)
"""
MAX_LIN_X = 0.22
MAX_ANG_Z = 2.82
global goalX, goalY, stop
goalX = 0
goalY = 0
stop = False
class TurtleBot3:
    
    def __init__(self):
        rospy.init_node('turtlebot_controller', anonymous=True)
	    # self.sub  = rospy.Subscriber('/odom', Odometry, self.get_odom  )
        self.pub = rospy.Publisher('/cmd_vel', Twist, queue_size=10)
        # print(self.pub)
        self.rate = rospy.Rate(10)
        # rospy.Subscriber('/x', Int32, self.goal)
        # self.sub = rospy.Subscriber('/x', Int32, self.move2goal)
        
        self.lin_x = MAX_LIN_X / 2
        self.ang_z = MAX_ANG_Z / 2
        
        self.wise  = 1
        
        """        x
                   |
             ------+------
             |\    |    /|                     /|
       case1 |0\   |   /0| case2              / |         1 radian = 57.2958 degree
             |  \  |  /  |                   /30|         1 degree = 0.0174533 radian
       +x,+y |   \0|0/   | +x,-y            /   |
             |    \|/    |               2 /    | sqrt(3)
       y-----+-----+-----+-----           /     |
             |    /|\    |               /      |
       -x,+y |   /0|0\   | -x,-y        /       |
             |  /  |  \  |             /60    90|
       case4 |0/   |   \0| case3      ----------+
             |/    |    \|                 1
             ------+------                            
       
        d = dist  = sqrt(pow(abs(x),2) + pow(abs(y),2))
        
        0 = angle = math.atan(abs(y) / abs(x))
        
        case 1:  0 =  math.atan(abs(y) / abs(x))
        case 2: -0 = -math.atan(abs(y) / abs(x))
        case 3: -(180 * 0.0174533 - 0) = -(pi - 0) = -(pi - math.atan(abs(y) / abs(x)))
        case 4:   180 * 0.0174533 - 0  =   pi - 0  =   pi - math.atan(abs(y) / abs(x))
        """ 

#    def get_odom(self, msg):
#         pos_x, pos_y, theta = self.get_pose(msg)
        
#         self.pos_x_2d = pos_x
#         self.pos_y_2d = pos_y
#         self.theta_2d = theta
        
#         if   (self.theta_2d - self.prev_theta_2d) > 5.:
#             d_theta = (self.theta_2d - self.prev_theta_2d) - 2 * pi            
#         elif (self.theta_2d - self.prev_theta_2d) < -5.:
#             d_theta = (self.theta_2d - self.prev_theta_2d) + 2 * pi
#         else:
#             d_theta = (self.theta_2d - self.prev_theta_2d)

#         self.theta_2d_sum  = self.theta_2d_sum + d_theta
#         self.prev_theta_2d = self.theta_2d
        
#         self.theta_2d = self.theta_2d_sum
    def testx(self, msg):
        print(msg.data)
        global goalX
        goalX = msg.data
    def testy(self, msg):
        global goalY
        print(msg.data)
        goalY = msg.data
        self.move2goal()

    def stop(self, msg):
        global stop
        print("stop" , msg.data)
        if (msg.data == 0):
            twist = Twist()
            self.pub.publish(twist)
    def get_dist(self, x, y):
        return sqrt(pow(abs(x), 2) + pow(abs(y), 2))
        
        
    def get_angle(self, x, y):
    
        if  (x >= 0 and y >= 0): # case 1: +0
            return  atan(abs(y) / abs(x))
            
        elif(x >= 0 and y <  0): # case 2: -0
            return -atan(abs(y) / abs(x))
            
        elif(x <  0 and y <  0): # case 3: -(pi-0)
            return -(pi - atan(abs(y) / abs(x)))
            
        elif(x <  0 and y >= 0): # case 4:  (pi-0)
            return   pi - atan(abs(y) / abs(x))

#    def scan_cb(msg):
#		global range_ahead
#		range_ahead = msg.ranges[0]
#		print "range ahead: %0.1f" %	range_ahead
		
#		range_ahead = 0;
#		rospy.init_node('go_scan')
#		cmd_pub = rospy.Publisher('cmd_vel', Twist, queue_size = 1)
#		scan_sub = rospy.Subscriber('scan', LaserScan, scan_cb)
#		
#		rate = rospy.Rate(10)
#		cmd = Twist()
#		
 #   while not rospy.is_shutdown():
#	if range_ahead < 0.8:
#		cmd.linear.x = 0
#		cmd.angular.z = 0.2
#	else:
#		cmd.linear.x = 0.2
#		cmd.angular.z = 0
#	cmd_pub.publish(cmd)
#	rate.sleep()    
#


    def move2goal(self):
	
        
        global goalX, goalY, stop
        goal_x    = input()
        goal_y    = input()
        # goal_x    = goalX
        # goal_y    = goalY
        print(goal_x)
        print(goal_y)
        print(1)
        if (goal_x == 0 and goal_y == 0) :
            goal_x = 0.1
            goal_y = 0.1
        # dist      = self.get_dist( goal_x, goal_y)
        angle     = self.get_angle(goal_x, goal_y)
        print(2)
        if(angle < 0):
            angle = -angle
            wise  = -1
            print(3)
        else:
            print(4)
            wise  =  1
                
        time2turn = angle / self.ang_z
        time2go   = goal_x  / self.lin_x
        time2goy = goal_y / self.lin_x

        print(5)
        twist = Twist()
        self.pub.publish(twist)
        twist.linear.x = self.lin_x
        time2end = rospy.Time.now() + rospy.Duration(time2go)
        # twist.linear.x = self.lin_x
        self.pub.publish(twist)
        print(twist) 
        rospy.sleep(0.001)
        
        while(rospy.Time.now() < time2end): pass
        print(7)
        twist.linear.x = 0

        twist.angular.z = self.ang_z * wise
        print(twist.angular.z)
        
        time2end = rospy.Time.now() + rospy.Duration(time2turn)*2
        
        self.pub.publish(twist)
        rospy.sleep(0.001)
        
        while(rospy.Time.now() < time2end): pass
        print(6)
        twist.angular.z = 0
        self.pub.publish(twist)
        
        twist.linear.x = self.lin_x
        time2end = rospy.Time.now() + rospy.Duration(time2goy)
        self.pub.publish(twist)
        rospy.sleep(0.001)
        print(twist)

        while(rospy.Time.now() < time2end): pass
        print(111)
        twist.linear.y = 0
        self.pub.publish(twist)

        # twist.linear.x = self.lin_x
        # time2end = rospy.Time.now() + rospy.Duration(time2go)
        # print(6.5)
        # self.pub.publish(twist)
        # rospy.sleep(0.001)
        
        # while(rospy.Time.now() < time2end):   pass
        # print(7)
        # twist.linear.x = 0
        # self.pub.publish(twist)
        
        wise = -wise
        
        twist.angular.z = self.ang_z * wise
        time2end = rospy.Time.now() + rospy.Duration(time2turn)*2
        
        self.pub.publish(twist)
        rospy.sleep(0.001)
        
        while(rospy.Time.now() < time2end): pass
        print(8)
        twist.angular.z = 0
        self.pub.publish(twist)
        listener = rospy.Publisher("/listener", String, queue_size=100)
        str = "SUCCESS"
        listener.publish(String(str))

        print("Robot is arrived at goal position!")
        twist = Twist()
        self.pub.publish(twist)
        #rospy.spin()


if __name__ == '__main__':
    try:
        x = TurtleBot3()
        # rospy.Subscriber('/x', Int32, x.move2goal())
        # x.move2goal()
        rospy.Subscriber('/x', Float32, x.testx)
        rospy.Subscriber('/y', Float32, x.testy)
        rospy.Subscriber('/stop', Int32, x.stop)

        x.move2goal()
        rospy.spin()
    except rospy.ROSInterruptException:   pass
