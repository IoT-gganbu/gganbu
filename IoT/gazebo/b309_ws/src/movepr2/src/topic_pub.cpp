#include "ros/ros.h"
#include "std_msgs/Int32.h"

int main(int argc, char **argv) {
	ros::init(argc, argv, "topic_pub");
	ros::NodeHandle n;
	ros::Publisher pub = n.advertise<std_msgs::Int32>("step", 1000);
	
	ros::Rate loop_rate(1);

	int count = 1;
	
	while(ros::ok()) {
		std_msgs::Int32 msg;
		msg.data = count;
		
		pub.publish(msg);
		
		ros::spinOnce();
		loop_rate.sleep();
	}
	
	return 0;
}
