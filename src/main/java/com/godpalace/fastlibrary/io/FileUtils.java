package com.godpalace.fastlibrary.io;

import java.io.*;

public class FileUtils {
    public static byte[] readAllBytes(String path, int bufferSize) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            int len;
            byte[] buffer = new byte[bufferSize];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readAllBytes(String path) {
        return readAllBytes(path, 1024);
    }

    public static void writeBytes(String path, byte[] bytes, boolean append) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path, append))) {
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeBytes(String path, byte[] bytes) {
        writeBytes(path, bytes, false);
    }

    public static void moveTo(@FilePath String src, @FilePath String dst) {
        try (FileOutputStream out = new FileOutputStream(dst);
             FileInputStream in = new FileInputStream(src)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            boolean success = new File(src).delete();
            if (!success) {
                System.err.println("Failed to delete " + src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
