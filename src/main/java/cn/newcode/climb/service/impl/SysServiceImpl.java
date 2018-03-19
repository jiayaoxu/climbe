package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.UserMapper;
import cn.newcode.climb.po.Version;
import cn.newcode.climb.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/1 0001
 * \* Time: 19:48
 * \* Description:
 * \
 */
@Service("sysService")
public class SysServiceImpl implements SysService {

    @Autowired
    private UserMapper userMapper;

    private String uploadPth = "/opt/pic/app/";
//            "/opt/pic/app/";

    @Override
    public Version getVersion() {
        return userMapper.selectVersion();
    }

    @Override
    public void updateVersion(String version) {
        userMapper.updateVersion(version);
    }

    @Override
    public String upload(MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("chunk") == null) {

            String realPath = uploadPth;
//                    "/opt/pic/app/";
            String fileName = file.getOriginalFilename();

            File targetFile = new File(realPath, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(targetFile); // 小文件，直接拷贝

            return "";
        } else {
            int chunk = Integer.parseInt(request.getParameter("chunk")); // 当前分片
            int chunks = Integer.parseInt(request.getParameter("chunks")); // 分片总计

            String realPath = uploadPth;
//                    "/opt/pic/app/";

            String Ogfilename = file.getOriginalFilename();

            File tempFile = new File(realPath, Ogfilename);
            OutputStream outputStream = new FileOutputStream(tempFile, true);
            InputStream inputStream = file.getInputStream();

            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();

            return "";
        }

    }

    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     * @return string
     */
    private String getFileNameNew() {
        return "climbe.apk";
    }
}