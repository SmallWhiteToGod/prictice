package com.prictice.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil extends FileUtils {

    public static int BUFFER_SIZE = 2048;

    private static List<String> unTar(InputStream inputStream, String destDir) throws Exception {
        List<String> fileNames = new ArrayList<String>();
        TarArchiveInputStream tarIn = new TarArchiveInputStream(inputStream, BUFFER_SIZE);
        TarArchiveEntry entry = null;
        try {
            while ((entry = tarIn.getNextTarEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {//是目录
                    createDirectory(destDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(destDir + File.separator + entry.getName());
                    createDirectory(tmpFile.getParent() + File.separator, null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } finally {
                        out.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            tarIn.close();
        }

        return fileNames;
    }

    public static List<String> unTar(String tarFile, String destDir) throws Exception {
        File file = new File(tarFile);
        return unTar(file, destDir);
    }

    public static List<String> unTar(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new FileInputStream(tarFile), destDir);
    }

    public static List<String> unTarBZip2(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new BZip2CompressorInputStream(new FileInputStream(tarFile)), destDir);
    }

    public static List<String> unTarBZip2(String file, String destDir) throws Exception {
        File tarFile = new File(file);
        return unTarBZip2(tarFile, destDir);
    }

    public static List<String> unBZip2(String bzip2File, String destDir) throws IOException {
        File file = new File(bzip2File);
        return unBZip2(file, destDir);
    }

    public static List<String> unBZip2(File srcFile, String destDir) throws IOException {
        if (StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<String>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            os.close();
            is.close();
        }
        return fileNames;
    }

    public static List<String> unGZ(String gzFile, String destDir) throws IOException {
        if (StringUtils.isNotEmpty(destDir)) {
            File destFile = new File(destDir);
            if (!destFile.exists()) {
                destFile.mkdir();
            }
        }

        File file = new File(gzFile);
        return unGZ(file, destDir);
    }

    public static List<String> unGZ(File srcFile, String destDir) throws IOException {
        if (StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<String>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            os.close();
            is.close();
        }
        return fileNames;
    }

    public static List<String> unTarGZ(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new GzipCompressorInputStream(new FileInputStream(tarFile)), destDir);
    }

    public static List<String> unTarGZ(String file, String destDir) throws Exception {
        File tarFile = new File(file);
        return unTarGZ(tarFile, destDir);
    }

    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
            file = new File(outputDir + File.separator + subDir);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static List<String> unZip(File zipfile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = zipfile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();

        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);
                        IOUtils.copy(is, os);
                    } finally {
                        os.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            is.close();
        }

        return fileNames;
    }

    public static List<String> unZip(String zipfile, String destDir) throws Exception {
        File zipFile = new File(zipfile);
        return unZip(zipFile, destDir);
    }

    /**
     * 文件解压
     *
     * @param compressFileDir 需要解压的文件完整目录
     * @param destDir         解压后的文件目录
     * @return
     * @throws Exception
     */
    public static List<String> unCompress(String compressFileDir, String destDir) throws Exception {
        String upperName = compressFileDir.toUpperCase();
        List<String> ret = null;
        if (StringUtils.isNotEmpty(destDir)) {
            File file = new File(destDir);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        if (upperName.endsWith(".ZIP")) {
            ret = unZip(compressFileDir, destDir);
        } else if (upperName.endsWith(".TAR")) {
            ret = unTar(compressFileDir, destDir);
        } else if (upperName.endsWith(".TAR.BZ2")) {
            ret = unTarBZip2(compressFileDir, destDir);
        } else if (upperName.endsWith(".BZ2")) {
            ret = unBZip2(compressFileDir, destDir);
        } else if (upperName.endsWith(".TAR.GZ")) {
            ret = unTarGZ(compressFileDir, destDir);
        } else if (upperName.endsWith(".GZ")) {
            ret = unGZ(compressFileDir, destDir);
        }
        return ret;
    }

    /**
     * 文件内容合并
     *
     * @param outFile 合并后的文件
     * @param files   需要合并的所有文件
     * @param BufSize 每次合并的处理大小
     */
    public static void mergeFiles(String outFile, String[] files, int BufSize) {

        FileChannel outChannel = null;
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for (String f : files) {
                if (!f.endsWith(File.separator)) {
                    f = f + File.separator;
                }
                Charset charset = Charset.forName("utf-8");
                CharsetDecoder charsetDecoder = charset.newDecoder();
                CharsetEncoder charsetEncoder = charset.newEncoder();
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BufSize);
                CharBuffer charBuffer = charsetDecoder.decode(bb);
                ByteBuffer nbuBuffer = charsetEncoder.encode(charBuffer);
                while (fc.read(nbuBuffer) != -1) {
                    bb.flip();
                    nbuBuffer.flip();
                    outChannel.write(nbuBuffer);
                    bb.clear();
                    nbuBuffer.clear();
                }
                fc.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * @param srcDir 压缩文件夹路径
     * @param out 压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,
     * 			true:保留目录结构;
     *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String[] srcDir, String outDir,
                             boolean KeepDirStructure) throws RuntimeException, Exception {

        OutputStream out = new FileOutputStream(new File(outDir));

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            List<File> sourceFileList = new ArrayList<File>();
            for (String dir : srcDir) {
                File sourceFile = new File(dir);
                sourceFileList.add(sourceFile);
            }
            compress(sourceFileList, zos, KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,
     * 			true:保留目录结构;
     *			false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos,
                                 String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(),
                                KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

    private static void compress(List<File> sourceFileList,
                                 ZipOutputStream zos, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        for (File sourceFile : sourceFileList) {
            String name = sourceFile.getName();
            if (sourceFile.isFile()) {
                zos.putNextEntry(new ZipEntry(name));
                int len;
                FileInputStream in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            } else {
                File[] listFiles = sourceFile.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    if (KeepDirStructure) {
                        zos.putNextEntry(new ZipEntry(name + "/"));
                        zos.closeEntry();
                    }

                } else {
                    for (File file : listFiles) {
                        if (KeepDirStructure) {
                            compress(file, zos, name + "/" + file.getName(),
                                    KeepDirStructure);
                        } else {
                            compress(file, zos, file.getName(),
                                    KeepDirStructure);
                        }

                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        int BUFSIZE = 1024 * 8;
//        System.out.println(unCompress("D:\\mysql\\20211128\\input\\tm_app_main_VC1_F10001_20211128.tar.gz", ""));

        //System.out.println(unBZip2("D:\\mysql\\20180722.xml.bz2", "F:\\mysql\\"));
        //System.out.println(unTarBZip2("D:\\mysql\\20180722.tar.bz2", "F:\\mysql\\"));

        //System.out.println(unGZ("D:\\mysql\\20180722.xml.gz", "F:\\mysql\\"));
        //System.out.println(unTarGZ("D:\\mysql\\20180722.tar.gz", "F:\\mysql\\"));
//        String[] files = new String[]{"D:\\mysql\\20211128\\test\\aa.txt","D:\\mysql\\20211128\\test\\bb.txt"};
//        mergeFiles("D:\\mysql\\20211128\\test\\cc.txt",files,BUFSIZE);

        File file = new File("D:\\mysql\\test");
        if (!file.exists()) {
            file.mkdir();
        }

        //解压
        FileUtil.unCompress("D:\\mysql\\test\\20170728.tar.gz", "D:\\mysql\\test\\ungz");
        FileUtil.unCompress("D:\\mysql\\test\\20170728.tar.bz2", "D:\\mysql\\test\\unbz2");
        FileUtil.unCompress("D:\\mysql\\test\\20170728.zip", "D:\\mysql\\test\\unzip");

        //压缩
        String[] srcDir = { "D:\\mysql\\test\\20170728",
                "D:\\mysql\\test\\ungz",
                "D:\\mysql\\test\\unzip" };
        String outDir = "D:\\mysql\\test\\test.zip";
        FileUtil.toZip(srcDir, outDir, true);

        //合并
        String[] files = new String[]{"D:\\mysql\\test\\aaa.txt","D:\\mysql\\test\\bbb.txt"};
        mergeFiles("D:\\mysql\\test\\ccc.txt",files,BUFSIZE);
    }
}
