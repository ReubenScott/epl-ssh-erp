package com.kindustry.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件操作类
 * 
 * @author By Chason
 *
 */
public class FileUtil {

  /**
   * 下载文件
   */
  public static void downFile(String path, HttpServletResponse response, String allPath) throws IOException {
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    OutputStream fos = null;
    InputStream fis = null;
    File uploadFile = new File(allPath);
    fis = new FileInputStream(uploadFile);
    bis = new BufferedInputStream(fis);
    fos = response.getOutputStream();
    bos = new BufferedOutputStream(fos);
    // path:文件名
    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(path, "utf-8"));
    int bytesRead = 0;
    byte[] buffer = new byte[8192];
    while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
      bos.write(buffer, 0, bytesRead);
    }
    bos.close();
    fos.close();
    bis.close();
    fis.close();
  }

}
