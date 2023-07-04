package com.ys.oa.jwt;


import java.io.IOException;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class GetFileZIP {

    public static void printZipTxt(String zipPath) throws IOException {


        ZipFile zipFile = new ZipFile(zipPath);
        for (Enumeration<? extends ZipEntry> e = zipFile.getEntries(); e.hasMoreElements();) {
            ZipEntry entry = e.nextElement();
            // 表示是一个文件
            if (!entry.getName().endsWith("/")){
                System.out.println("文件名:" + entry.getName() );
            }else {
                System.out.println("目录名:" + entry.getName());
            }


        }

    }

    public static void main(String[] args) {
        try {
            printZipTxt("E:\\新建文件夹.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
