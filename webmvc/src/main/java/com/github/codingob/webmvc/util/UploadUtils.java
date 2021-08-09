package com.github.codingob.webmvc.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class UploadUtils {

    /**
     * 重命名文件并保存
     *
     * @param file 文件
     * @param path 保存路径
     */
    public static void save(CommonsMultipartFile file, String path) {
        String filename = file.getOriginalFilename();
        String[] strings = StringUtils.split(filename, ".");
        assert strings != null;
        String suffix = strings[strings.length - 1];
        String newFileName = UUID.randomUUID() + "." + suffix;
        try {
            file.transferTo(new File(path + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
