package com.resource.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author FXW
 * 2015年11月16日
 */
public class BatchUploadImageAndVideoAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static Map<String,String> contentTypeMap = new HashMap<>();
	
	
	private Map responseJson;   
    public Map getResponseJson() {   
        return responseJson;   
    }   
    public void setResponseJson(Map responseJson) {   
        this.responseJson = responseJson;   
    }
	
    private List<File> files = new ArrayList<>();
    private List<String> filesFileName = new ArrayList<>();
    private List<String> filesContentType = new ArrayList<>();
    private File imgCoverFile;
    private String imgCoverFileFileName;
    private File videoCoverFile;
    private String videoCoverFileFileName;
    
    private File videoFile;
    private String videoFileFileName;
    
    private Long guid;
    
    static{
    	contentTypeMap.put("image/jpeg", ".jpg");
    	contentTypeMap.put("image/png", ".png");
    	contentTypeMap.put("image/gif", ".gif");
    	contentTypeMap.put("bmp/dib", ".bmp");
    	contentTypeMap.put("audio/mpeg", ".mp3");
    	contentTypeMap.put("video/mp4", ".mp4");
    }
    
	public String batchUploadImageAndVideo() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		this.setResponseJson(resultMap);
		if(guid == null || guid <= 0){
			resultMap.put("result", 400);
			resultMap.put("message", "参数错误:用户ID为空");
			return "success";
		}
		if(null == files || files.size() == 0 || 
				null == filesFileName || filesFileName.size() == 0 || 
				files.size() != filesFileName.size() ){
			resultMap.put("result", 400);
			resultMap.put("message", "参数错误:没有图片数据");
			return "success";
		}
		if(null == imgCoverFile || null == videoCoverFile){
			resultMap.put("result", 400);
			resultMap.put("message", "参数错误:没有图片封面或者视频封面数据");
			return "success";
		}
		try {
			Map<String,Object> datas = new HashMap<>();
			final String rootPath = ServletActionContext.getRequest().getRealPath("/") ;
			if(null != videoFile){
				final String videoFolder = "video" + File.separator + guid + File.separator;
				/**
				 * 上传视频
				 */
				String videoExten = ".mp4";
				if(null != videoFileFileName && !"".equals(videoFileFileName) && videoFileFileName.indexOf(".") != -1){
					videoExten =  videoFileFileName.substring(videoFileFileName.lastIndexOf("."));
				}
				long videoNow = System.currentTimeMillis() + new Random().nextInt(1000);
				String saveVideoFileName = videoFolder + videoNow + videoExten;
				String fullVideoName = rootPath + saveVideoFileName;
				File currVideoFile = new File(fullVideoName);  
				FileUtils.copyFile(videoFile, currVideoFile);
				
				datas.put("videoUrl", saveVideoFileName);
			}
			
    		final String folder = "img" + File.separator + guid + File.separator;
			/**
			 * 上传图片封面
			 */
			String exten = ".jpg";
			if(null != imgCoverFileFileName && !"".equals(imgCoverFileFileName) && imgCoverFileFileName.indexOf(".") != -1){
				exten =  imgCoverFileFileName.substring(imgCoverFileFileName.lastIndexOf("."));
			}
			long now = System.currentTimeMillis() + new Random().nextInt(1000);
			String saveFileName = folder + now + exten;
			String fullName = rootPath + saveFileName;
			File currFile = new File(fullName);  
    		FileUtils.copyFile(imgCoverFile, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里
    		String saveZoomFileName = folder + now + "small" + exten;
    		String outputFolder = rootPath + saveZoomFileName;
    		NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
    		
    		datas.put("imgCoverUrl", saveFileName);
    		
    		/**
    		 * 上传视频封面图片
    		 */
    		exten = ".jpg";
			if(null != videoCoverFileFileName && !"".equals(videoCoverFileFileName) && videoCoverFileFileName.indexOf(".") != -1){
				exten =  videoCoverFileFileName.substring(videoCoverFileFileName.lastIndexOf("."));
			}
			now = System.currentTimeMillis() + new Random().nextInt(1000);
			saveFileName = folder + now + exten;
			fullName = rootPath + saveFileName;
			currFile = new File(fullName);  
    		FileUtils.copyFile(videoCoverFile, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里
    		saveZoomFileName = folder + now + "small" + exten;
    		outputFolder = rootPath + saveZoomFileName;
    		NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
    		
    		datas.put("videoCoverUrl", saveFileName);
    		
			/**
			 * 上传其他图片
			 */
			Set<String> set = new LinkedHashSet<>();
			for (int i=0;i<files.size() ;i++) {
				File file = files.get(i);
				String fileName = filesFileName.get(i);
				exten = ".jpg";
				if(null != fileName && !"".equals(fileName) && fileName.indexOf(".") != -1){
					exten =  fileName.substring(fileName.lastIndexOf("."));
				}
				String contentType = filesContentType.get(i);
				System.out.println("contentType = "+contentType);
//				String saveFileName = folder + now + contentTypeMap.get(contentType);
				now = System.currentTimeMillis() + new Random().nextInt(1000);
				saveFileName = folder + now + exten;
				fullName = rootPath + saveFileName;
				
				currFile = new File(fullName);  
	    		FileUtils.copyFile(file, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里
	    		
//	    		String saveZoomFileName = folder + now + "small" + contentTypeMap.get(contentType);
	    		saveZoomFileName = folder + now + "small" + exten;
	    		outputFolder = rootPath + saveZoomFileName;
	    		NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
	    		set.add(saveFileName);
			}
			datas.put("imgUrls", set);
			resultMap.put("result", 200);
			resultMap.put("message", "OK");
			resultMap.put("data", datas);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", 400);
			resultMap.put("message", "上传失败");
		}
		return "success";
	}
	
	/**
	 * 上传图片
	 * @return
	 * @throws Exception
	 */
	public String uploadImages() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		this.setResponseJson(resultMap);
		if(guid == null || guid <= 0){
			resultMap.put("result", 400);
			resultMap.put("message", "参数错误:用户ID为空");
			System.out.println("参数错误:用户ID为空");
			return "success";
		}
		if(null == files || files.size() == 0 || 
				null == filesFileName || filesFileName.size() == 0 || 
				files.size() != filesFileName.size() ){
			resultMap.put("result", 400);
			resultMap.put("message", "参数错误:没有图片数据");
			System.out.println("参数错误:没有图片数据-"+guid);
			return "success";
		}
		try {
			final String rootPath = ServletActionContext.getRequest().getRealPath("/") ;
			Set<String> set = new LinkedHashSet<>();
			for (int i=0;i<files.size() ;i++) {
				File file = files.get(i);
				String fileName = filesFileName.get(i);//[2014年2月1日1]
				String exten = ".jpg";
				if(null != fileName && !"".equals(fileName) && fileName.indexOf(".") != -1){
					exten =  fileName.substring(fileName.lastIndexOf("."));
				}
				String contentType = filesContentType.get(i);
				System.out.println("contentType = "+contentType);
				long now = System.currentTimeMillis() + new Random().nextInt(1000);
				final String folder = "img" + File.separator + guid + File.separator;
				String saveFileName = folder + now + exten;
				String fullName = rootPath + saveFileName;
				
				File currFile = new File(fullName);  
	    		FileUtils.copyFile(file, currFile);// struts2提供的工具类，意思是把缓存区文件放到哪里
	    		
	    		String saveZoomFileName = folder + now + "small" + exten;
	    		String outputFolder = rootPath + saveZoomFileName;
	    		NarrowImage.writeHighQuality(NarrowImage.zoomImage(currFile), outputFolder);
	    		System.out.println("upload success url:"+saveFileName);
	    		set.add(saveFileName);
			}
			Map<String,Object> datas = new HashMap<>();
			datas.put("imgUrls", set);
			resultMap.put("result", 200);
			resultMap.put("message", "OK");
			resultMap.put("data", datas);
			System.out.println(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", 400);
			resultMap.put("message", "上传失败");
		}
		return "success";
	}
	
	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public List<String> getFilesContentType() {
		return filesContentType;
	}
	public void setFilesContentType(List<String> filesContentType) {
		this.filesContentType = filesContentType;
	}
	public List<String> getFilesFileName() {
		return filesFileName;
	}
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	public Long getGuid() {
		return guid;
	}
	public void setGuid(Long guid) {
		this.guid = guid;
	}
	public File getImgCoverFile() {
		return imgCoverFile;
	}
	public void setImgCoverFile(File imgCoverFile) {
		this.imgCoverFile = imgCoverFile;
	}
	public String getImgCoverFileFileName() {
		return imgCoverFileFileName;
	}
	public void setImgCoverFileFileName(String imgCoverFileFileName) {
		this.imgCoverFileFileName = imgCoverFileFileName;
	}
	public File getVideoCoverFile() {
		return videoCoverFile;
	}
	public void setVideoCoverFile(File videoCoverFile) {
		this.videoCoverFile = videoCoverFile;
	}
	public String getVideoCoverFileFileName() {
		return videoCoverFileFileName;
	}
	public void setVideoCoverFileFileName(String videoCoverFileFileName) {
		this.videoCoverFileFileName = videoCoverFileFileName;
	}
	public File getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
	public String getVideoFileFileName() {
		return videoFileFileName;
	}
	public void setVideoFileFileName(String videoFileFileName) {
		this.videoFileFileName = videoFileFileName;
	}
	
	
	
	
}
