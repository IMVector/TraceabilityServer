package com.ecnu.traceability.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Vector
 */
public class UploadUtil {

    public static String uploadFile(HttpServletRequest request,String macAddress) throws IOException {
        String realPath = "";
        // 将当前上下文初始化给CommonsMultipartResolver
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 检查form中是否有enctype＝"multipart／form－data"
        if (resolver.isMultipart(request)) {
            // 强制转化request
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            // 从表单获取input名称
            Iterator<String> iterable = req.getFileNames();
            // 存在文件
            if (iterable.hasNext()) {
                String inputName = iterable.next();
                // 获得文件
                MultipartFile mf = req.getFile(inputName);
                byte[] mfs = mf.getBytes();
                // 获得原始文件名
                String oriFileName = mf.getOriginalFilename();
                // String suffix = oriFileName.substring(oriFileName.lastIndexOf("."));
                // 上传图片到本地
                realPath = req.getServletContext().getRealPath(File.separator) + File.separator + "clients_model" + File.separator + macAddress+oriFileName;
                mf.transferTo(new File(realPath));

                System.out.println(realPath);

            }
        }
        return realPath;
    }
}
