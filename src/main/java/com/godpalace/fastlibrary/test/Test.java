package com.godpalace.fastlibrary.test;

import com.godpalace.fastlibrary.gui.swing.filelist.JFileList;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("test.Test");
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        JFileList fileList = new JFileList();
        fileList.setAutoLoadIcon(true);
        fileList.updateItemFromDisk("D:\\");

        f.getContentPane().add(fileList, BorderLayout.CENTER);
        f.setVisible(true);
    }
}
