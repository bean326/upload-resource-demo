package com.resource.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片无损保真压缩
 * @author FXW
 * 2015年10月22日
 */
public class NarrowImage {

	
	public static BufferedImage zoomImage(File srcfile) {
		BufferedImage result = null;
		try {
			BufferedImage im = ImageIO.read(srcfile);
			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();
			//压缩计算
			float resizeTimes = 0.3f;  /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/
			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * resizeTimes);
			int toHeight = (int) (height * resizeTimes);
			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight,BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight,java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		} catch (Exception e) {
			System.out.println("NarrowImage.zoomImage -> exception" );
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	private static BufferedImage zoomImage(String src,float resizeTimes) {
		BufferedImage result = null;
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				System.out.println("文件不存在");
				
			}
			BufferedImage im = ImageIO.read(srcfile);
			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();
			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * resizeTimes);
			int toHeight = (int) (height * resizeTimes);
//			toWidth = toWidth < 200 ? 200 : toWidth;
//			toHeight = toHeight < 200 ? 200 : toHeight;
			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight,BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight,java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		} catch (Exception e) {
			System.out.println("NarrowImage.zoomImage" + e.getMessage());
		}
		return result;
	}
	
	 public static boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		 FileOutputStream newimage = null;
	        try {
	            /*输出到文件流*/
//	            newimage = new FileOutputStream(fileFullPath);
//	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
//	            System.out.println("NarrowImage.writeHighQuality -> encoder1:"+encoder);
//	            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
//	            System.out.println("NarrowImage.writeHighQuality -> jep1:"+jep);
//	            /* 压缩质量 */
//	            jep.setQuality(0.9f, true);
//	            System.out.println("NarrowImage.writeHighQuality -> jep2:"+jep);
//	            encoder.encode(im, jep);
//	            System.out.println("NarrowImage.writeHighQuality -> encoder2:"+encoder);
	           /*近JPEG编码*/
	            
	            
//	            FileOutputStream out = new FileOutputStream(targetImg);   
//	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
//	            encoder.encode(image);   
//	            out.close();

	            ImageIO.write(im,  "jpeg" , new File(fileFullPath)); 
	            im.flush();
	            
	            return true;
	        } catch (Exception e) {
	        	System.out.println("NarrowImage.writeHighQuality" + e.getMessage());
	        	e.printStackTrace();
	            return false;
	        }finally{
        		try {
        			if(newimage != null){
        				newimage.close();
        			}
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	 
	 
	 public static void main(String[] args) {
		 
		 String inputFoler = "E:/1.jpg" ; 
         /*这儿填写你存放要缩小图片的文件夹全地址*/
        String outputFolder = "E:/s_small.jpg" ; 
        /*这儿填写你转化后的图片存放的文件夹*/
        
        int last = inputFoler.lastIndexOf(".");
        String s = inputFoler.substring(0,last);
        String f = inputFoler.substring(last);
        System.out.println(s+"_small"+f);
		 
//		 NarrowImage.writeHighQuality(NarrowImage.zoomImage(inputFoler,0.3f), outputFolder);
		
	}

}

