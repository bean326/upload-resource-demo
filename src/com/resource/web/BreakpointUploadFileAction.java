package com.resource.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.resource.bean.BaseResult;
import com.resource.config.CommonConfig;
import com.resource.constant.ResultValueEnum;
import com.resource.httpclient.HttpRequester;
import com.resource.httpclient.HttpRespons;

public class BreakpointUploadFileAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5656394965373493196L;
	
	private Map responseJson;   
    public Map getResponseJson() {   
        return responseJson;   
    }   
    public void setResponseJson(Map responseJson) {   
        this.responseJson = responseJson;   
    } 
    //用户id
	private long guid;
	//块文件名
	private String blockId;
	//块数据
    private File blockData;
    private String fileType;
    private String blockType;
    private String blockTime;
    
    private String themeStr;
	private String themeNum;
	private String startTime;
	private String endTime;
	private String trystType;
	private String moneyCount;
    
    
	
	
	
	
	/**
	 * 上传块文件
	 * @return
	 */
	public String breakpontUpload(){
//		Map<String,Object> map = new HashMap<>();
//		map.put("method", "verifyTryst");
//		map.put("guid", guid);
//		map.put("themeStr", themeStr);
//		map.put("themeNum", themeNum);
//		map.put("startTime", startTime);
//		map.put("endTime", endTime);
//		map.put("trystType", trystType);
//		map.put("moneyCount", moneyCount);
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		this.setResponseJson(resultMap);
		resultMap.put("result", 400);
		resultMap.put("message", "系统错误");
		
		if (guid <= 0) {
			resultMap.put("message", "用户ID不能为空");
			return "success";
		}
		if (blockId == null || blockId.equals("")) {
			resultMap.put("message", "块ID不能为空");
			return "success";
		}
		if (this.blockData == null) {
			resultMap.put("message", "块不能为空");
			return "success";
		}
//		BaseResult<?> baseResult = verify(map);
//		if(baseResult.getStatusCode() == ResultValueEnum.SYS_PARAMETER_ERROR.getKey()){
//			resultMap.put("result", 400);
//			resultMap.put("message", baseResult.getMessage());
//			return "success";
//		}
    	@SuppressWarnings("deprecation")
		String temp = ServletActionContext.getRequest().getRealPath("/") + "temp/videoTryst/" + guid + "/";
    	resultMap.put("blockId", blockId);
    	 try {  
			 File currFile = new File(temp + blockId);  
			 System.out.println("上传++++"+currFile.getAbsolutePath());
			 FileUtils.copyFile(this.blockData, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里  
			 resultMap.put("result", 200);
			 resultMap.put("message", "OK");
			 this.setResponseJson(resultMap);
         } catch (IOException e) {  
        	 resultMap.put("result", 400);
             e.printStackTrace();  
             return "success";
         }  
 		return "success";
	}

	
	/**
	 * 将所有块文件合并
	 * @return
	 */
	public String breakpontUploadFinish(){
		String path = ServletActionContext.getRequest().getRealPath("/");
		String temp = path + "temp/videoTryst/" + guid + "/" + blockTime +"/";
		long currentTIme = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>(); 
		 // 合并成新文件
		try {
			String videoPath = "video/" + guid + "/";
			String url = videoPath + currentTIme + fileType;
			
			File file = new File(path + videoPath);
			if(!file.exists())
				file.mkdirs();
			
			new FileUtil().mergePartFiles(temp, blockType, path + url);
			
			
			map.put("url", url);
			
			map.put("result", 200);
			
		} catch (IOException e) {
			map.put("url", "");
			map.put("result", 400);
			e.printStackTrace();
		}
		this.setResponseJson(map);
		return "success";
	}
	
	/**
	 * 删除目录(包括：目录里的所有文件)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String path) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!path.equals("")) {

			File newPath = new File(path);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	private BaseResult<?> verify(Map<String,Object> map){
		String url = CommonConfig.yuepai_server_mina_http_host_and_port + CommonConfig.yuepai_server_mina_http_context;
		HttpRequester request = new HttpRequester();
		BaseResult<?> baseResult = null;
		try {
			HttpRespons respons = request.send(url, "POST", map, null);
			String content = respons.getContent();
			baseResult = JSONObject.parseObject(content, BaseResult.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baseResult;
	}
	
	
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public File getBlockData() {
		return blockData;
	}
	public void setBlockData(File blockData) {
		this.blockData = blockData;
	}
	public long getGuid() {
		return guid;
	}
	public void setGuid(long guid) {
		this.guid = guid;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getBlockType() {
		return blockType;
	}
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	public String getBlockTime() {
		return blockTime;
	}
	public void setBlockTime(String blockTime) {
		this.blockTime = blockTime;
	}
	public String getThemeStr() {
		return themeStr;
	}
	public void setThemeStr(String themeStr) {
		this.themeStr = themeStr;
	}
	public String getThemeNum() {
		return themeNum;
	}
	public void setThemeNum(String themeNum) {
		this.themeNum = themeNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTrystType() {
		return trystType;
	}
	public void setTrystType(String trystType) {
		this.trystType = trystType;
	}
	public String getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(String moneyCount) {
		this.moneyCount = moneyCount;
	}
	
}
