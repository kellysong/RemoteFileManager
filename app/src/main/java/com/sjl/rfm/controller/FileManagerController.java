
package com.sjl.rfm.controller;

import android.os.Environment;
import android.text.TextUtils;

import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.framework.body.FileBody;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.ResponseBody;
import com.yanzhenjie.andserver.http.cookie.Cookie;
import com.yanzhenjie.andserver.http.session.Session;
import com.sjl.rfm.component.LoginInterceptor;
import com.sjl.rfm.model.FileInfo;
import com.sjl.rfm.util.FileUtils;
import com.sjl.rfm.util.Logger;
import com.sjl.rfm.util.TimeUtils;
import com.yanzhenjie.andserver.util.MediaType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理控制层
 *
 * @author Kelly
 * @version 1.0.0
 * @filename SyncActivity.java
 * @time 2019/12/19 15:36
 * @copyright(C) 2019 song
 */
@RestController
@RequestMapping(path = "/file")
class FileManagerController {


    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String login(HttpRequest request, HttpResponse response, @RequestParam(name = "account") String account,
                 @RequestParam(name = "password") String password) {
        Session session = request.getValidSession();
        session.setAttribute(LoginInterceptor.LOGIN_ATTRIBUTE, true);

        Cookie cookie = new Cookie("account", account + "=" + password);
        response.addCookie(cookie);
        return "Login successful.";
    }


    /**
     * 返回文件列表
     *
     * @return
     */
    @GetMapping(path = "/list")
    List<FileInfo> getFileList(@RequestParam(name = "rootPath", required = false) String rootPath) {
        File file;
        if (TextUtils.isEmpty(rootPath)) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            file = new File(rootPath);
        }
        List<FileInfo> fileInfoList = new ArrayList<>();
        if (!file.isDirectory()) {
            return fileInfoList;//不能返回空，否则前端收不到数据
        }
        File[] list = file.listFiles();
        if (list == null || list.length == 0) {
            return fileInfoList;
        }
        FileInfo fileInfo;
        for (File f : list) {
            String name = f.getName();
            fileInfo = new FileInfo();
            fileInfo.setName(name);
            fileInfo.setUrl(f.getAbsolutePath());
            fileInfo.setDateModified(f.lastModified());
            fileInfo.setDateModifiedString(TimeUtils.formatDateToStr(f.lastModified(), "yyyy/MM/dd aHH:mm:ss"));
            if (f.isFile()) {
                fileInfo.setIsDir(0);
                fileInfo.setSize(f.length());
                fileInfo.setSizeString(FileUtils.formatFileSize(f.length()));
            } else {
                fileInfo.setIsDir(1);
            }
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }


    @PostMapping(path = "/download")
    ResponseBody download(HttpResponse response, @RequestParam(name = "rootPath", required = false) String rootPath) {

        if (TextUtils.isEmpty(rootPath)) {
            return new StringBody("文件路径为空");
        }
        try {
            File file = new File(rootPath);
            if (file.isFile() && file.exists()) {

                FileBody fileBody = new FileBody(file);
                response.setStatus(200);
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
                return fileBody;
            } else {
                return new StringBody("文件不存在或已经删除");
            }
        } catch (Exception e) {
            Logger.e("下载文件异常：" + e.getMessage());
            return new StringBody("下载文件异常：" + e.getMessage());
        }

    }
}