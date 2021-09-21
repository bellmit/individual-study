package cn.gyw.thirdpart.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 文件操作
 * Created by guanyw on 2019/1/8.
 */
public class FileUtil {

    /**
     * 列出一个目录下所有文件(NIO)
     *
     * @param dirPath 目录
     */
    public static void printDirPathNIO(String dirPath) throws IOException {
        Path initPath = Paths.get(dirPath);
        Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 列出一个目录下所有文件
     *
     * @param dirPath 目录
     */
    public static void printDirPath(String dirPath) {
        File file = new File(dirPath);
        workDirectory(file, 0);
    }

    private static void workDirectory(File file, int level) {
        if (file.isDirectory()) {
            for (File tmp : file.listFiles()) {
                workDirectory(tmp, level + 1);
            }
        } else {
            for (int i = 0 ; i < level - 1 ; i ++) {
                System.out.print("\t");
            }
            System.out.println(file.getName());
        }
    }

    /**
     * 统计给定文件中给定字符串的出现次数
     * @param filePath 文件路径
     * @param word 字符串
     * @return 字符串在文件中出现的次数
     */
    public static int countWordInFile(String filePath, String word) {
        int counter = 0;
        try (FileReader fr = new FileReader(filePath)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    int index = -1;
                    while (line.length() >= word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }

    /**
     * 文件拷贝
     * @param source 源文件路径
     * @param target 目标文件路径
     * @throws IOException
     */
    public static void fileCopy(String source, String target) throws IOException {
        try (InputStream inputStream = new FileInputStream(source)) {
            try (OutputStream outputStream = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while ((bytesToRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesToRead);
                }
            }
        }
    }

    /**
     * 文件拷贝 （NIO）
     * @param source 源文件路径
     * @param target 目标文件路径
     * @throws IOException
     */
    public static void fileCopyNIO(String source, String target) throws IOException {
        try (FileInputStream in = new FileInputStream(source)) {
            try (FileOutputStream out = new FileOutputStream(target)) {
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                while (inChannel.read(byteBuffer) != -1) {
                    byteBuffer.flip();
                    outChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
        }
    }

    /*
    工具类，私有化构造器
     */
    private FileUtil() {
        throw new AssertionError();
    }
}
