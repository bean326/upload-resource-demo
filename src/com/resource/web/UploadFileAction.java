package com.resource.web;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadFileAction extends ActionSupport {

	// 上传文件  
    private File fileData;// 拦截器会为你在缓冲区创建临时文件，这是临时文件对象  
    private String filePath;
    private Map responseJson;   
    public Map getResponseJson() {   
        return responseJson;   
    }   
    public void setResponseJson(Map responseJson) {   
        this.responseJson = responseJson;   
    }
	public File getFileData() {
		return fileData;
	}

	public void setFileData(File fileData) {
		this.fileData = fileData;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String uploadDate(){
		Map<String, Object> map = new HashMap<String, Object>();
		if (filePath == null || filePath.equals("")) {
			//return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
		if (this.fileData == null) {
			//return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
    	@SuppressWarnings("deprecation")
		String temp = ServletActionContext.getRequest().getRealPath("/");
        try {  
            File currFile = new File(temp + filePath);  
            
            FileUtils.copyFile(this.fileData, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里  
            
            if (!filePath.endsWith("mp4")) {
				
//            	byte[] currFileData = Utils.fileToBetyArray(currFile);
//            	byte[] data = Utils.resizeImage(currFileData, 100);
//            	Utils.BetyToFile(data,temp + filePath.replace(".", "small."));
            	String outputFolder = temp + filePath.replace(".", "small.");
            	System.out.println("[outputFolder]:" + outputFolder);
            	NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
			}
            map.put("result", 200);
            this.setResponseJson(map);
        } catch (IOException e) {  
            //return "error";
            map.put("result", 400);
			this.setResponseJson(map);
			System.out.println("[exception]:" + e.getMessage());
			e.printStackTrace();  
        }  
		return "success";
	}
	
	public String deleteFile(){
		Map<String, Object> map = new HashMap<String, Object>();
		if (filePath == null || filePath.equals("")) {
			//return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
    	@SuppressWarnings("deprecation")
		String temp = ServletActionContext.getRequest().getRealPath("/");
		File dirFile = new File(temp + filePath);
		boolean bFile = dirFile.exists();
		
		if (!filePath.endsWith("mp4")) {
			
			File dirSmallFile = new File(temp + filePath.replace(".", "small."));
			dirSmallFile.exists();
		}
		if (!bFile) {
			System.out.println("The file is not exists.");
			//return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}

		boolean result = dirFile.delete();
		if (result) {
			map.put("result", 200);
			this.setResponseJson(map);
			return "success";
		} else {
			//return "error";
			map.put("result", 400);
			this.setResponseJson(map);
			return "success";
		}
	}
	
	
}
