package test.rpc;

import com.yzmy.jsonrpc4j.JsonRpcMethod;
import com.yzmy.jsonrpc4j.JsonRpcParam;

public interface UserService {
	
	@JsonRpcMethod("addUser")
	public void addUser(@JsonRpcParam("name")String name);
	
	@JsonRpcMethod("getName")
	public String getName();
	
	@JsonRpcMethod("getUser")
	public User getUser(@JsonRpcParam("id")int id,@JsonRpcParam("User")User user);
}
