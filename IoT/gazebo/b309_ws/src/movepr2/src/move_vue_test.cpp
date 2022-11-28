#include "ros/ros.h"
#include "iostream"
#include "std_msgs/Int32.h"
#include "std_msgs/String.h"
#include "geometry_msgs/Twist.h"
#include "tf/transform_listener.h"

	
bool done = false;

class SubscribeAndPublish
{
public:
	
	geometry_msgs::Twist base_cmd;
	SubscribeAndPublish(){
	    // publish
	    pub_ = n_.advertise<geometry_msgs::Twist>("/base_controller/command", 1);
	    complete_pub_ = n_.advertise<std_msgs::String>("/listener", 1);

	    //subscribe
	    sub_ = n_.subscribe("/step", 1, &SubscribeAndPublish::callback, this); 
	    stop_sub_ = stop_n_.subscribe("/stop", 1, &SubscribeAndPublish::stop, this);
  	}

	void stop(const std_msgs::Int32::ConstPtr& msg)
	{
		base_cmd.linear.x = 0.0;
		done = true;
		printf("stop: %d\n", done);
	}
                                                                   
	void callback(const std_msgs::Int32::ConstPtr& msg)
	{
		int step = msg->data;
		tf::TransformListener listener_;
		
		
		 if(step == 1){	
			done = false;		

			listener_.waitForTransform("base_footprint", "odom_combined", ros::Time(0), ros::Duration(1.0));

			tf::StampedTransform start_transform;
			tf::StampedTransform current_transform;

			listener_.lookupTransform("base_footprint", "odom_combined", ros::Time(0), start_transform);

			base_cmd.linear.y = base_cmd.angular.z = 0;
			base_cmd.linear.x = 1.0;
			
			while(!done && ros::ok()) {

				
				printf("%d\n", done);			
	
				pub_.publish(base_cmd);
				
			    	try{
					listener_.lookupTransform("base_footprint", "odom_combined", ros::Time(0), current_transform);
				}
				catch(tf::TransformException ex){
					ROS_ERROR("%s", ex.what());
					break;
				}

				tf::Transform relative_transform = start_transform.inverse() * current_transform;
				double dist_moved = relative_transform.getOrigin().length();

				if(dist_moved > 1){
					done = true;
					std_msgs::String msg;
					msg.data = "SUCCESS";
					printf("SUCCESS");
					complete_pub_.publish(msg);
				}
			}
		}else if(step == 2){
			printf("%d\n", step);
		}
	}

private:
	ros::NodeHandle n_; 
	ros::NodeHandle stop_n_; 
	ros::Publisher pub_;
	ros::Publisher complete_pub_;
	ros::Subscriber sub_;
	ros::Subscriber stop_sub_;
};

int main(int argc, char **argv) {
  ros::init(argc, argv, "subscribe_and_publish");
  SubscribeAndPublish SAPObject;
  ros::spin();
  return 0;
}
