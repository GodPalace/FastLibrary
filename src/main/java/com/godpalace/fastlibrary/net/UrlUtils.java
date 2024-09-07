package com.godpalace.fastlibrary.net;

import com.godpalace.fastlibrary.io.FilePath;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtils {
    public static long getUrlFileSize(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            long fileSize = connection.getContentLength();
            connection.disconnect();

            return fileSize;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static long getUrlFileSize(String url) {
        try {
            return getUrlFileSize(new URL(url));
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static void download(URL url, @FilePath File savePath) {
        try {
            long fileSize = getUrlFileSize(url);
            if (fileSize == -1L) {
                System.out.println("Failed to get file size.");
                return;
            }

            BufferedInputStream in = new BufferedInputStream(url.openStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(savePath));

            byte[] data = new byte[1024];
            int len;
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(String url, @FilePath String savePath) {
        try {
            download(new URL(url), new File(savePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
