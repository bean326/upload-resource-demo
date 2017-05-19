package com.resource.web;

import java.io.IOException;


public class FileTest {

public static void main(String[] args) {
	try {
		new FileTest().writeFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    public void writeFile() throws IOException, InterruptedException {

        System.out.println(FileUtil.currentWorkDir);

        StringBuilder sb = new StringBuilder();

        long originFileSize = 1024 * 1024 * 100;// 100M
        int blockFileSize = 1024 * 1024 * 15;// 15M

        // 生成一个大文件
        for (int i = 0; i < originFileSize; i++) {
            sb.append("A");
        }

        String fileName = FileUtil.currentWorkDir + "origin.myfile";
        System.out.println(fileName);
        System.out.println(FileUtil.write(fileName, sb.toString()));

        // 追加内容
        sb.setLength(0);
        sb.append("0123456789");
        FileUtil.append(fileName, sb.toString());

        FileUtil fileUtil = new FileUtil();

        // 将origin.myfile拆分
        fileUtil.splitBySize(fileName, blockFileSize);

        Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完

        // 合并成新文件
        fileUtil.mergePartFiles(FileUtil.currentWorkDir, ".part",
                FileUtil.currentWorkDir + "new.myfile");

    }
}
