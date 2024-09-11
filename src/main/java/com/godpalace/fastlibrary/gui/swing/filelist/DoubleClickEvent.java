package com.godpalace.fastlibrary.gui.swing.filelist;

import java.util.EventObject;

public class DoubleClickEvent extends EventObject {
    private final FileItem item;

    public DoubleClickEvent(FileItem item) {
        super(item);
        this.item = item;
    }

    public FileItem getFileItem() {
        return item;
    }
}
