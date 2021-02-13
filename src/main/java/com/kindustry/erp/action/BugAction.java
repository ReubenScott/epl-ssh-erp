package com.kindustry.erp.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.kindustry.erp.model.Bug;
import com.kindustry.erp.service.BugService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.util.ResourceUtil;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;

@Namespace("/bug")
@Action("bugAction")
public class BugAction extends BaseAction<Bug> {
  private static final long serialVersionUID = 1130360187782527019L;

  private File filedata;
  private String filedataFileName;
  private String filedataContentType;

  @Autowired
  private BugService bugService;

  /**
   * 查询所有bug
   */
  public void findBugList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, "%" + searchValue.trim() + "%");
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(bugService.findBugList(map, pageUtil));
    gridModel.setTotal(bugService.getCount(map, pageUtil));
    OutputJson(gridModel);
  }

  /**
   * 添加bug
   */
  public void persistenceBug() {
    OutputJson(getMessage(bugService.persistenceBug(super.sample)), Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 删除bug
   */
  public void delBug() {
    OutputJson(getMessage(bugService.delBug(super.sample.getBugId())));
  }

  /**
   * 附件上传
   * 
   * @throws UnsupportedEncodingException
   */
  public void upload() throws UnsupportedEncodingException {
    Map<String, Object> map = new HashMap<String, Object>();
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    String contextPath = request.getContextPath();
    // 文件保存目录路径
    String savePath = session.getServletContext().getRealPath(File.separator) + ResourceUtil.getUploadDirectory() + File.separator;
    // 要返回给xhEditor的文件保存目录URL
    String saveUrl = contextPath + File.separator + ResourceUtil.getUploadDirectory() + File.separator;
    SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthDf = new SimpleDateFormat("MM");
    SimpleDateFormat dateDf = new SimpleDateFormat("dd");
    Date date = new Date();
    String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
    savePath += ymd;
    saveUrl += ymd;
    System.out.println("savePath==========>" + savePath);
    // D:\apache-tomcat-7.0.57\webapps\MyErp\attached\2015/11/12/
    System.out.println("saveUrl==========>" + saveUrl);
    // /MyErp\attached\2015/11/12/
    File uploadDir = new File(savePath);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    // 如果是HTML5上传文件，那么这里有相应头的
    String contentDisposition = request.getHeader("Content-Disposition");
    Long fileSize = Long.valueOf(request.getHeader("Content-Length"));
    if (contentDisposition != null) {// HTML5拖拽上传文件
                                     // 文件名称从request的header头文件获取
      String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));
      fileName = fileName.substring(fileName.indexOf("\"") + 1);
      fileName = fileName.substring(0, fileName.indexOf("\""));
      fileName = URLDecoder.decode(fileName, "utf-8");
      ServletInputStream inputStream = null;
      try {
        // 文件流从request中获取
        inputStream = request.getInputStream();
      } catch (IOException e) {
        map.put("err", "上传文件出错！");
        e.printStackTrace();
      }

      if (inputStream == null) {
        map.put("err", "您没有上传任何文件！");
      }
      if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
        map.put("err", "上传文件超出限制大小！");
        map.put("msg", fileName);
      } else {
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        // 新的文件名称
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        File uploadedFile = new File(savePath, newFileName);
        try {
          FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
        } catch (FileNotFoundException e) {
          map.put("err", "上传文件出错！");
          e.printStackTrace();
        } catch (IOException e) {
          map.put("err", "上传文件出错！");
          e.printStackTrace();
        }
        map.put("err", "");
        Map<String, Object> nm = new HashMap<String, Object>();
        nm.put("url", saveUrl + newFileName);
        nm.put("localfile", fileName);
        nm.put("id", 0);
        map.put("msg", nm);
      }
    } else {// 不是HTML5拖拽上传,普通上传
      if (ServletFileUpload.isMultipartContent(request)) {// 判断表单是否存在enctype="multipart/form-data"
        if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
          map.put("err", "上传文件超出限制大小！");
          map.put("msg", filedataFileName);
        } else {
          System.out.println("filedataFileName=" + filedataFileName);
          String fileExt = filedataFileName.substring(filedataFileName.lastIndexOf(".") + 1).toLowerCase();
          String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
          Constants.copy(filedata, savePath + newFileName);
          Map<String, Object> nm = new HashMap<String, Object>();
          map.put("err", "");
          nm.put("url", saveUrl + newFileName);
          nm.put("localfile", filedataFileName);
          nm.put("id", 0);
          map.put("msg", nm);
        }
      } else {
        // 不是multipart/form-data表单
        map.put("err", "上传文件出错！");
      }
    }
    System.out.println(request.getHeader("Content-Length"));
    System.out.println("filedataContentType=>>" + filedataContentType);
    System.out.println("filedata=>>" + filedata);
    System.out.println("filedataFileName==>>" + filedataFileName);
    System.out.println("savePath==>>" + savePath);
    System.out.println("saveUrl==>>" + saveUrl);
    OutputJson(map, Constants.TEXT_TYPE_PLAIN);
    // savePath==========>D:\apache-tomcat-7.0.57\webapps\MyErp\attached\2015/11/12/
    // saveUrl==========>/MyErp\attached\2015/11/12/
    // 13992
    // filedataContentType=>>null
    // filedata=>>null
    // filedataFileName==>>null
    // savePath==>>D:\apache-tomcat-7.0.57\webapps\MyErp\attached\2015/11/12/
    // saveUrl==>>/MyErp\attached\2015/11/12/
  }

  public File getFiledata() {
    return filedata;
  }

  public void setFiledata(File filedata) {
    this.filedata = filedata;
  }

  public String getFiledataFileName() {
    return filedataFileName;
  }

  public void setFiledataFileName(String filedataFileName) {
    this.filedataFileName = filedataFileName;
  }

  public String getFiledataContentType() {
    return filedataContentType;
  }

  public void setFiledataContentType(String filedataContentType) {
    this.filedataContentType = filedataContentType;
  }

}
