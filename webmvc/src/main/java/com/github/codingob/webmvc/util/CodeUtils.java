package com.github.codingob.webmvc.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class CodeUtils {
    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456".toCharArray();
    public static final long TIMEOUT = 1000 * 60 * 3;

    /**
     * 生成指定长度验证码
     *
     * @param len 长度
     * @return 验证码字符串
     */
    public static String getCode(int len) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(CHARS.length);
            code.append(CHARS[index]);
        }
        return code.toString();
    }

    /**
     * 根据指定字符串生成验证码图片
     *
     * @param w    宽度
     * @param h    高度
     * @param code 验证码字符串
     * @return 验证码图像字节数组
     */
    public static byte[] getImage(int w, int h, String code) {
        char[] chars = code.toCharArray();
        byte[] bytes = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(new Color(100, 230, 200));
        graphics.fillRect(0, 0, w, h);
        graphics.setFont(new Font(null, Font.BOLD, 40));
        Random random = new Random();
        for (int i = 0; i < code.length(); i++) {
            graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
            graphics.drawString(chars[i] + "", (i * 40), 30);
        }
        try {
            ImageIO.write(image, "JPG", outputStream);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
