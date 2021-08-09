package com.github.codingob.webmvc.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件下载工具类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class DownloadUtils {

    /**
     * 从Resources下载文件
     * @param file 文件名(相对路径)
     * @return 文件字节数组
     */
    public static byte[] downloadForClassResource(String file) {
        ClassPathResource resource = new ClassPathResource(file);
        return getByteArray(resource);
    }

    /**
     * 从System下载文件
     * @param file 文件名(绝对路径)
     * @return 文件字节数组
     */
    public static byte[] downloadForSystemResource(String file) {
        FileSystemResource resource = new FileSystemResource(file);
        return getByteArray(resource);
    }

    /**
     * 转换字节数组
     * @param resource 资源
     * @return 字节数组
     */
    private static byte[] getByteArray(Resource resource) {
        byte[] byteFile = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = resource.getInputStream();
            outputStream = new ByteArrayOutputStream();
            int len;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            byteFile = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteFile;
    }
}
