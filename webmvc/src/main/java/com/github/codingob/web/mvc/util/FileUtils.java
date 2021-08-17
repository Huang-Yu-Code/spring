package com.github.codingob.web.mvc.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件上传/下载 工具类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class FileUtils {
    /**
     * 上传文件
     *
     * @param files 文件列表
     * @param path  保存路径
     */
    public static void upload(CommonsMultipartFile[] files, String path) throws IOException {
        for (CommonsMultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String[] strings = StringUtils.split(filename, ".");
            assert strings != null;
            String suffix = strings[strings.length - 1];
            String newFileName = UUID.randomUUID() + "." + suffix;
            file.transferTo(new File(path + newFileName));
        }
    }

    /**
     * 文件下载
     *
     * @param file 文件
     * @return 文件数组
     * @throws IOException 异常
     */
    public static byte[] download(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        byte[] byteArray = outputStream.toByteArray();
        outputStream.close();
        inputStream.close();
        return byteArray;
    }
}
