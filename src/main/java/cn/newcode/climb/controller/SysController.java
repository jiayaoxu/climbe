package cn.newcode.climb.controller;

import cn.newcode.climb.po.Version;
import cn.newcode.climb.service.SysService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/1 0001
 * \* Time: 19:43
 * \* Description:
 * \ 系统设置
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private SysService sysService;

    @Value("/opt/pic/app")
    private String path;

    /**
     * 获取apk版本号
     * @return
     */
    @RequestMapping("/getVersion")
    public @ResponseBody Version getVersion(){
        Version v = null;
        try{
            v = sysService.getVersion();
        } catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }

    /**
     * 下载apk
     * @param filename
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("filename") String filename)throws Exception{
        File file = new File(path+filename);
        HttpHeaders headers = new HttpHeaders();
        String downloadName = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
        //通知浏览器以下载的方式打开
        headers.setContentDispositionFormData("attachment", downloadName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    /**
     * 上传apk
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request,
                         HttpServletResponse response,@Param("version")String version)throws Exception{
//        , @Param("version") String version, @Param("file") MultipartFile file
        sysService.upload(file, request, response);
        System.out.println(version);
        sysService.updateVersion(version);
        return "success";

        /*if(file.isEmpty()){
            return "error";
        }
        String filename = "climbe.apk";
        File filepath = new File(path,filename);
        if(!filepath.exists()){
            filepath.getParentFile().mkdirs();
        }
        file.transferTo(filepath);
        sysService.updateVersion(version);
        return "success";*/
    }

    /**
     * 修改版本号
     * @param version
     * @return
     */
    @RequestMapping(value = "/updateVersion")
    public @ResponseBody  String updateVersion(String version){
        try{
            sysService.updateVersion(version);
            return "success";
        } catch (Exception e){
            return "false";
        }
    }
}