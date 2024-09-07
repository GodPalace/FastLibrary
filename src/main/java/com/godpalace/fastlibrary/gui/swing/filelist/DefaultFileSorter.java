package com.godpalace.fastlibrary.gui.swing.filelist;

public class DefaultFileSorter implements FileSorter {
    @Override
    public boolean sort(FileItem oldItem, FileItem newItem) {
        String oldName = oldItem.name;
        String newName = newItem.name;

        if (newItem.isDirectory && !oldItem.isDirectory) {
            return true;
        } else if (oldItem.isDirectory && !newItem.isDirectory) {
            return false;
        } else {
            return oldName.toLowerCase().compareTo(newName.toLowerCase()) > 0;
        }
    }
}
