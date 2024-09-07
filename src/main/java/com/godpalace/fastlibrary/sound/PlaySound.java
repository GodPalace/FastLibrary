package com.godpalace.fastlibrary.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PlaySound {
    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(5, 15, 1, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

    public static void play(String soundFilePath) {
        play(new File(soundFilePath));
    }

    public static void play(URL soundUrl) {
        play(soundUrl.getFile());
    }

    public static void play(File soundFile) {
        executor.execute(() -> {
            try (AudioInputStream in = AudioSystem.getAudioInputStream(soundFile)) {
                AudioFormat format = in.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    line.write(buffer, 0, len);
                }

                line.drain();
                line.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
