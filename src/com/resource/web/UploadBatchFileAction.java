package com.resource.web;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadBatchFileAction extends ActionSupport {

	// 上传文件  
    private File[] fileDatas;
    private String[] filePaths;
    private Map responseJson;   
    public Map getResponseJson() {   
        return responseJson;   
    }   
    public void setResponseJson(Map responseJson) {   
        this.responseJson = responseJson;   
    }
	public File[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(File[] fileDatas) {
		this.fileDatas = fileDatas;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String[] filePaths) {
		this.filePaths = filePaths;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String uploadBatchDate(){
		Map<String, Object> map = new HashMap<String, Object>();
		if (fileDatas != null && fileDatas.length == 0) {
//			return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
		if (filePaths != null && filePaths.length == 0) {
//			return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
		
    	@SuppressWarnings("deprecation")
		String temp = ServletActionContext.getRequest().getRealPath("/");
        try { 
        	for (int i = 0; i < fileDatas.length; i++) {
				
        		File currFile = new File(temp + filePaths[i]);  
        		FileUtils.copyFile(fileDatas[i], currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里
        		
        		if (!filePaths[i].endsWith("mp4")) {
        			
//        			byte[] currFileData = Utils.fileToBetyArray(currFile);
//        			byte[] data = Utils.resizeImage(currFileData, 100);
//        			Utils.BetyToFile(data,temp + filePaths[i].replace(".", "small."));
        			
        			String outputFolder = temp + filePaths[i].replace(".", "small.");
                	NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
        		}
			}
        	map.put("result", 200);
			this.setResponseJson(map);
        } catch (IOException e) {  
            e.printStackTrace();  
//            return "error";
            map.put("result", 400);
			this.setResponseJson(map);
			return "success";
        }  
		return "success";
	}
	
	
	
}
