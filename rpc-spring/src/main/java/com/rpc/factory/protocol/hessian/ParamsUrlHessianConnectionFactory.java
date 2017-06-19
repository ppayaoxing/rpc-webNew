package com.rpc.factory.protocol.hessian;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianURLConnection;
import com.caucho.hessian.client.HessianURLConnectionFactory;

/**
 * 继承 HessianURLConnectionFactory 重写相应方法
 * @author Administrator
 */
public class ParamsUrlHessianConnectionFactory extends HessianURLConnectionFactory {
	
	private Map<String,String> headerParams = null;

	public ParamsUrlHessianConnectionFactory(Map<String, String> headerParams) {
		super();
		this.headerParams = headerParams;
	}
	
	@Override  
    public HessianConnection open(URL url) throws IOException {  
        HessianURLConnection hessianURLConnection = (HessianURLConnection) super  
                .open(url);  
        if (null != headerParams && !headerParams.isEmpty()) {  
            Set<String> keySet = headerParams.keySet();  
            for (String key : keySet) {  
                // 向报文头中添加参数  
                hessianURLConnection.addHeader(key, headerParams.get(key));  
            }  
        }  
        return hessianURLConnection;  
    }  

}
