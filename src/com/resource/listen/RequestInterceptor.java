package com.resource.listen;

import java.io.File;
import java.util.Map;  

import javax.servlet.http.HttpSession;  
  


import com.opensymphony.xwork2.Action;  
import com.opensymphony.xwork2.ActionSupport;  
import com.opensymphony.xwork2.ActionInvocation;  
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;  

public class RequestInterceptor extends AbstractInterceptor {  
	  
    private static final long serialVersionUID = 3244973830196015811L;  
    private HttpSession session;  
  
    public void setSession(Map<String, Object> session) {  
        this.session = (HttpSession) session;  
    }  
  
    public String intercept(ActionInvocation aInvocation) throws Exception {  
          
        // 获取请求的action名称  
        String actionName = aInvocation.getInvocationContext().getName();  
          
        // 获取action后附带参数  
        Map<String,Object> parameters = aInvocation.getInvocationContext().getParameters();  
          
       System.out.println("actionName:"+actionName);
       System.out.println("参数 :");
       Object[] keys = parameters.keySet().toArray();
       for(Object o : keys)
       {
    	   System.out.println("key:"+o); 
    	   Object value = parameters.get(o.toString());
    	   System.out.println("value：");
    	   if(value instanceof Object[])
    	   {
    		   Object[] objs = (Object[]) value;
    		   int i = 0;
    		   for(Object vv : objs)
    		   {
    			   System.out.println((i++) + ":" + vv);
    		   } 
    	   }
    	   else
    	   {
    		   System.out.println("value："+value);
    	   }
       }
       
       return aInvocation.invoke();
    }
}  