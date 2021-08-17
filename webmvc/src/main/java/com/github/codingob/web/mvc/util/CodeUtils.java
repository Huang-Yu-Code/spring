package com.github.codingob.web.mvc.util;

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
    private final static char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    /**
     * 生成验证码
     *
     * @return 验证码字符串
     */
    public static String createCodeString(int len) {

        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(CHARS.length);
            code.append(CHARS[index]);
        }
        return code.toString().toLowerCase();

    }

    /**
     * 生成验证码图像
     *
     * @param weight 宽度
     * @param height 高度
     * @param code   验证码字符串
     * @return 验证码图像数组
     */
    public static byte[] createCodeImage(int weight, int height, String code) {
        char[] chars = code.toCharArray();
        byte[] bytes = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(new Color(100, 230, 200));
        graphics.fillRect(0, 0, weight, height);
        graphics.setFont(new Font(null, Font.BOLD, height));
        Random random = new Random();
        for (int i = 0; i < code.length(); i++) {
            graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
            graphics.drawString(chars[i] + "", (i * (weight / 4)), height - 5);
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
