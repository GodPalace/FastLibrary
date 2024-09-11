package com.godpalace.fastlibrary.gui.swing.filelist;

import java.io.File;
import java.nio.file.FileSystems;

public class FileItem {
    public String name;
    public String path;
    public String extension;
    public boolean isDirectory;

    public static String getSeparator() {
        return FileSystems.getDefault().getSeparator();
    }

    public static FileItem createItem(File file) {
        return new FileItem(file.getAbsolutePath(), file.isDirectory());
    }

    public FileItem(String path, boolean isDirectory) {
        this.name = path.substring(path.lastIndexOf(getSeparator()) + 1);
        this.path = path;
        if (!isDirectory) this.extension = name.substring(name.lastIndexOf(".") + 1);
        this.isDirectory = isDirectory;
    }

    public File getFile() {
        return new File(path);
    }
}
