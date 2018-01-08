package com.ruubypay;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

public class ImageUtils {
    /**
     * @Description:&#x622a;&#x56fe;
     * @author:liuyc
     * @time:2016&#x5e74;5&#x6708;27&#x65e5; &#x4e0a;&#x5348;10:18:23
     * @param srcFile&#x6e90;&#x56fe;&#x7247;&#x3001;targetFile&#x622a;&#x597d;&#x540e;&#x56fe;&#x7247;&#x5168;&#x540d;&#x3001;startAcross &#x5f00;&#x59cb;&#x622a;&#x53d6;&#x4f4d;&#x7f6e;&#x6a2a;&#x5750;&#x6807;&#x3001;StartEndlong&#x5f00;&#x59cb;&#x622a;&#x56fe;&#x4f4d;&#x7f6e;&#x7eb5;&#x5750;&#x6807;&#x3001;width&#x622a;&#x53d6;&#x7684;&#x957f;&#xff0c;hight&#x622a;&#x53d6;&#x7684;&#x9ad8;
     */
    public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
                                int hight) throws Exception {
        // 取得图片读入器
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = readers.next();
        // 取得图片读入流
        InputStream source = new FileInputStream(srcFile);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        // 图片参数对象
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
    }

    /**
     * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
     * @author:liuyc
     * @time:2016年5月27日 下午5:52:24
     * @param files 要拼接的文件列表
     * @param type  横向拼接， 2 纵向拼接
     */
    public static void mergeImage(String[] files, int type, String targetFile) {
        int len = files.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:小图片贴到大图片形成一张图(合成)
     * @author:liuyc
     * @time:2016年5月27日 下午5:51:20
     */
    public static final void overlapImage(String bigPath, String smallPath, String outFile) {
        try {
            BufferedImage big = ImageIO.read(new File(bigPath));
            BufferedImage small = ImageIO.read(new File(smallPath));
            int x = (big.getWidth() - small.getWidth()) / 2;
            int y = (big.getHeight() - small.getHeight()) / 2;
            big = drawString(big,"徐鹏飞",x,y-200);
            Graphics2D g = big.createGraphics();
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author xpf
     * @description 图片写文字
     * @date 2018/1/8 14:32
     */
    public static final BufferedImage drawString(BufferedImage big,String content,int x,int y) {
        try {
            Graphics2D g = big.createGraphics();
            Font font = new Font("黑体",Font.BOLD,100);
            g.setFont(font);
            g.drawString(content,x,y);
            g.dispose();
//            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
            return big;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        String bigPath = "D:\\dev\\zz_affix\\bg.png";
        String smallPath = "D:\\dev\\zz_affix\\qr.png";
        String outFile = "D:\\dev\\zz_affix\\merge.png";
        overlapImage(bigPath,smallPath,outFile);
//        drawString(bigPath,"图创万荣",outFile);
    }
}
