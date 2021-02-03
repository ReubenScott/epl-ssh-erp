package com.kindustry.erp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;

/**
 * 类功能说明 TODO:IP工具类
 */
public class IpUtil {

  /**
   * 获取登录用户IP地址
   * 
   * @param request
   * @return
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip.indexOf("0:") != -1) {
      ip = "本地";
    }
    return ip;
  }

  // 获取MAC地址的方法
  public static String getMACAddress(InetAddress ia) throws Exception {
    byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

    // 下面代码是把mac地址拼装成String
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < mac.length; i++) {
      if (i != 0) {
        sb.append("-");
      }
      // mac[i] & 0xFF 是为了把byte转化为正整数
      String s = Integer.toHexString(mac[i] & 0xFF);
      sb.append(s.length() == 1 ? 0 + s : s);
    }

    // 把字符串所有小写字母改为大写成为正规的mac地址并返回
    return sb.toString().toUpperCase();
  }

  public static String getMAC() {
    String mac = null;
    try {
      Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");

      InputStream is = pro.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String message = br.readLine();

      int index = -1;
      while (message != null) {
        if ((index = message.indexOf("Physical Address")) > 0) {
          mac = message.substring(index + 36).trim();
          break;
        }
        message = br.readLine();
      }
      System.out.println(mac);
      br.close();
      pro.destroy();
    } catch (IOException e) {
      System.out.println("Can't get mac address!");
      return null;
    }
    return mac;
  }

}
