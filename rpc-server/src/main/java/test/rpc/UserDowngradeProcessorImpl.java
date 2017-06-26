package test.rpc;

public class UserDowngradeProcessorImpl {


	public String getName(){
		return "降级处理";
	}
	
	public User getUser(int id,User users){
		User user = new User();
		user.setAddress("降级处理");
		return user;
	}
}
