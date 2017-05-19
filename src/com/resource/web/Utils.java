package com.resource.web;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.raster.JimiRasterImage;

public class Utils {
	
	public static byte[] resizeImage(byte[] in,int maxDim)  
	{  
	    try  
	    {  
	        Image inImage=Toolkit.getDefaultToolkit().createImage(in);  
	        ImageIcon inImageIcon = new ImageIcon(in);   
	  
	        int imh = inImageIcon.getIconHeight();  
	        int imw = inImageIcon.getIconWidth();  
	        double scale;  
	        if( imh <= maxDim && imw <= maxDim )  
	            scale = 1;  
	        else if( imh > imw )  
	           scale = (double) maxDim / (double) imh;  
	        else  
	           scale = (double) maxDim / (double) imw;   
	  
	        int scaledW = (int) (scale * imw);  
	        int scaledH = (int) (scale * imh);   
	  
	        Image img = inImage.getScaledInstance(scaledW, scaledH, Image.SCALE_FAST);  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        JimiRasterImage raster = Jimi.createRasterImage(img.getSource());  
	        // --java.io.ByteArrayOutputStream  
	        Jimi.putImage("image/jpeg", raster, outStream);  
	        outStream.flush();  
	        outStream.close();  
	        return outStream.toByteArray();   
	  
	    }  
	    catch(Exception ex)  
	    {  
	        ex.printStackTrace();  
	        return null;  
	    }  
	}  
	
	public static byte[] fileToBetyArray(File file)  
    {  
        FileInputStream fileInputStream = null;  
        byte[] bFile = null;  
        try {  
            bFile = new byte[(int) file.length()];  
            fileInputStream = new FileInputStream(file);  
            fileInputStream.read(bFile);  
            fileInputStream.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                fileInputStream.close();  
                bFile.clone();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bFile;  
    }
	
	public static void BetyToFile(byte[] data,String filePath)  
    {  
        File file = new File(filePath);  
        BufferedOutputStream stream = null;  
        FileOutputStream fstream = null;  
        try {  
            fstream = new FileOutputStream(file);  
            stream = new BufferedOutputStream(fstream);  
            stream.write(data);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (stream != null) {  
                    stream.close();  
                }  
                if (null != fstream) {  
                    fstream.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
        }  
    }  

}
