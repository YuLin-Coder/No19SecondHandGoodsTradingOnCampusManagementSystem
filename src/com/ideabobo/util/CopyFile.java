package com.ideabobo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CopyFile {
	private static final int BUFFER_SIZE = 16 * 1024;

	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(File sourceFile, File targetFile)  {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // �½��ļ���������������л���
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // �½��ļ��������������л���
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // ��������
            byte[] b = new byte[1024 * 1024];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // ˢ�´˻���������
            outBuff.flush();
        }catch (Exception e) {
			// TODO: handle exception
		} finally {
            // �ر���
            if (inBuff != null)
				try {
					inBuff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            if (outBuff != null)
				try {
					outBuff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }

	  public static String generateFileName(String fileName)
	    {
	        String formatDate = new SimpleDateFormat("yyMMddHHmmss")
	                .format(new Date());
	        int random = new Random().nextInt(10000);
	        int position = fileName.lastIndexOf(".");
	        String extension = fileName.substring(position);
	        return formatDate + random + extension;
	    }

}
