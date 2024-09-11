package com.godpalace.fastlibrary.test;

import com.godpalace.fastlibrary.gui.swing.filelist.FileItem;
import com.godpalace.fastlibrary.gui.swing.filelist.JFileList;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Test extends JFrame {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("test.Test");
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        JFileList fileList = new JFileList();
        fileList.setAutoLoadIcon(true);
        for (int i = 0; i < 10000; i++) {
            fileList.addItem(new FileItem(i + "", false));
        }

        f.getContentPane().add(fileList, BorderLayout.CENTER);
        f.setVisible(true);
    }
}
