package com.visa.web.controller.line;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author user
 */
@Controller
@RequestMapping("/file/*")
public class FileUploadController {
    /**
     * @throws IOException
     */
    @RequestMapping
    public String upload(@RequestParam MultipartFile myfile, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath("/userUpload");
        // 设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        // 设置响应给前台内容的PrintWriter对象
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        if (myfile.isEmpty()) {
            out.print("1#请选择文件后上传");
            out.flush();
            return null;
        } else {
            originalFilename = myfile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            long dateStamp = System.currentTimeMillis();
            String extName = originalFilename.substring(index);
            originalFilename = originalFilename.substring(0, index) + dateStamp + extName;
            try {
                // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                // 此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath,
                        originalFilename));
            } catch (IOException e) {
                e.printStackTrace();
                out.flush();
                return null;
            }
        }
        out.print("0#" + request.getContextPath() + "/upload/" + originalFilename + "#"
                + originalFilename);
        out.flush();
        return null;

    }

    @RequestMapping
    public String download(@RequestParam String myfile, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        myfile = new String(myfile.getBytes("ISO-8859-1"), "utf-8");
        String fileName = myfile.substring(myfile.lastIndexOf('/') + 1);
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        response.setContentType("application/x-msdownload");
        PrintWriter out = response.getWriter();
        FileInputStream in = null;
        File f = new File(request.getSession().getServletContext().getRealPath("/userUpload/")
                + fileName);

        try {
            in = new FileInputStream(f);
            byte b[] = new byte[1024];
            while (in.read(b, 0, 1024) != -1) {
                out.write(new String(b, "ISO-8859-1"));
            }
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.flush();
        out.close();
        return null;
    }
}
