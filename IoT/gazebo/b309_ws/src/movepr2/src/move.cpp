#include "ros/ros.h"
#include "geometry_msgs/Twist.h"
#include "tf/transform_listener.h"

#include "iostream"
#include "std_msgs/Int32.h"

void callback(const std_msgs::Int32::ConstPtr& msg) {
ros::NodeHandle n1;
	ros::Publisher cmd_pub = n1.advertise<geometry_msgs::Twist>("/base_controller/command", 1);
	printf("%d\n", msg->data);

	if(msg->data == 0){
		printf("hi");
		}
	else if(msg->data==1){
		geometry_msgs::Twist base_cmd;

	tf::TransformListener listener_;
	
	ros::Duration(0.5).sleep();

	listener_.waitForTransform("base_footprint", "odom_combined", ros::Time(0), ros::Duration(1.0));

	tf::StampedTransform start_transform;
	tf::StampedTransform current_transform;

	listener_.lookupTransform("base_footprint", "odom_combined", ros::Time(0), start_transform);

	base_cmd.linear.y = base_cmd.angular.z = 0;
	base_cmd.linear.x = 1.0;

	ros::Rate loop_rate(100);
	bool done = false;
	while(!done && ros::ok()) {

		cmd_pub.publish(base_cmd);
		loop_rate.sleep();
		
	    	try{
			listener_.lookupTransform("base_footprint", "odom_combined", ros::Time(0), current_transform);
		}
		catch(tf::TransformException ex){
			ROS_ERROR("%s", ex.what());
			break;
		}

		tf::Transform relative_transform = start_transform.inverse() * current_transform;
		double dist_moved = relative_transform.getOrigin().length();

		if(dist_moved > 13) done = true;
	}
if(done) return;}

		
}

int main(int argc, char **argv) {
	ros::init(argc, argv, "robot_driver");
	ros::NodeHandle n;
	ros::Subscriber sub = n.subscribe<std_msgs::Int32>("step", 1, callback);
	
	
	return 0;
}
