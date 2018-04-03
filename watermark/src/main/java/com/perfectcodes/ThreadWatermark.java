package com.perfectcodes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.*;

/**
 * 线程
 *
 * @author xpf
 * @date 2018/4/3 下午3:20
 */
public class ThreadWatermark implements Runnable {

    private static final String PREFIX = "TRAN_";
    private String originalFile;
    private String text;
    private Color textColor;
    private Position position;

    public ThreadWatermark(String originalFile, String text, Color textColor, Position position) {
        this.originalFile = originalFile;
        this.text = text;
        this.textColor = textColor;
        this.position = position;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"----run---");
        File file = new File(originalFile);
        if (!file.exists()) {
            throw new IllegalArgumentException("图片文件不存在");
        }
        try {
            String filePath = file.getParent();
            String originalName = file.getName();
            if (originalName.equals(".DS_Store")){
                return;
            }
            BufferedImage originalImage = ImageIO.read(file);
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = newImage.createGraphics();
            graphics2D.drawImage(originalImage, 0, 0, width, height, null);
            graphics2D.setColor(textColor);
            graphics2D.drawString(text, position.x, position.y);
            graphics2D.dispose();
            ImageIO.write(newImage, "PNG", new File(filePath + File.separatorChar + PREFIX + originalName));
        } catch (Exception e) {
            throw new RuntimeException("图片加水印异常", e);
        }
    }

    public static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
//        ThreadWatermark threadWatermark = new ThreadWatermark("","perfect",Color.WHITE,new Position(10,10));
        File folder = new File("/Users/xpf/Documents/99other/zz_affix");
        File[] files = folder.listFiles();
        long start = System.currentTimeMillis();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
//            try {
//                origin(file.getAbsolutePath());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //ThreadPoolExecutor
            dispose(file.getAbsolutePath());
        }
        long end = System.currentTimeMillis();
        System.out.println("本次执行耗时：" + (end - start));
    }

    static ThreadFactory threadFactory = Executors.defaultThreadFactory();

    static ExecutorService executorService = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,new LinkedBlockingQueue(100), threadFactory, (Runnable r, ThreadPoolExecutor executor) -> {
        System.out.println("线程池饱和");
    });


    public static void dispose(String originalFile){
//        ThreadFactory threadFactory = new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                thread.setName("perfect");
//                return thread;
//            }
//        };
        System.out.println(originalFile);
        executorService.execute(new ThreadWatermark(originalFile,"perfect",Color.WHITE,new Position(10,10)));
    }

    public static void origin(String originalFile) {
        File file = new File(originalFile);
        if (!file.exists()) {
            throw new IllegalArgumentException("图片文件不存在");
        }
        try {
            String filePath = file.getParent();
            String originalName = file.getName();
            BufferedImage originalImage = ImageIO.read(file);
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = newImage.createGraphics();
            graphics2D.drawImage(originalImage, 0, 0, width, height, null);
            graphics2D.setColor(Color.white);
            graphics2D.drawString("perfect", 10, 10);
            graphics2D.dispose();
            ImageIO.write(newImage, "PNG", new File(filePath + File.separatorChar + PREFIX + originalName));
        } catch (Exception e) {
            throw new RuntimeException("图片加水印异常", e);
        }
    }
}
