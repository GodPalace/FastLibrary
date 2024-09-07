package com.godpalace.fastlibrary.gui.swing.filelist;

import com.godpalace.fastlibrary.image.ImageConversion;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShellAPI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class JFileList extends JComponent implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final Vector<FileItem> files, isSelected;
    private final Vector<DoubleClickListener> actions;
    private FileSorter sorter;

    private int notches = 0;
    private final int notchesPerUnit = 35;
    private int overIndex = -1;

    private static final Color FILE_SELECTED_COLOR = new Color(204, 232, 254);
    private static final Color FILE_SELECTED_BORDER_COLOR = new Color(180, 203, 221);
    private static final Color OVER_COLOR = new Color(229, 243, 255);
    private static final HashMap<String, BufferedImage> icons = new HashMap<>();

    private final FileSystemView view;
    private boolean isAutoLoadIcon;

    static {
        try {
            URL url = JFileList.class.getResource("/image/DefaultFileIcon.png");

            if (url != null) {
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    icons.put("/DefaultFileIcon/", image);
                } else {
                    throw new IOException("res load fail");
                }
            } else {
                throw new IOException("Not find res");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL url = JFileList.class.getResource("/image/DefaultMkdirIcon.png");

            if (url != null) {
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    icons.put("/DefaultMkdirIcon/", image);
                } else {
                    throw new IOException("res load fail");
                }
            } else {
                throw new IOException("Not find res");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JFileList() {
        this(new Vector<>());
    }

    public JFileList(Vector<FileItem> files) {
        this.files = files;
        isSelected = new Vector<>();
        actions = new Vector<>();
        sorter = new DefaultFileSorter();

        view = FileSystemView.getFileSystemView();
        isAutoLoadIcon = false;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    public JFileList(FileItem[] files) {
        this(new Vector<>(List.of(files)));
    }

    public void addDoubleClickAction(DoubleClickListener c) {
        actions.addElement(c);
    }

    public void removeDoubleClickAction(DoubleClickListener c) {
        actions.removeElement(c);
    }

    public Vector<FileItem> getSelectedItems() {
        Vector<FileItem> selected = new Vector<>();

        for (FileItem item : files) {
            if (isSelected.contains(item)) {
                selected.addElement(item);
            }
        }

        return selected;
    }

    public void addItem(FileItem item) {
        notches = 0;

        if (sorter == null) {
            files.addElement(item);
        } else {
            if (!files.isEmpty()) {
                boolean isInsert = false;

                for (int i = 0; i < files.size(); i++) {
                    FileItem f = files.get(i);

                    if (sorter.sort(f, item)) {
                        files.insertElementAt(item, i);
                        isInsert = true;
                        break;
                    }
                }

                if (!isInsert) {
                    files.addElement(item);
                }
            } else {
                files.addElement(item);
            }
        }

        repaint();
    }

    public void addFile(File file) {
        addItem(FileItem.createItem(file));
    }

    public void removeItem(FileItem item) {
        files.removeElement(item);
        repaint();
    }

    public void removeItems(FileItem[] items) {
        for (FileItem item : items) {
            files.removeElement(item);
        }

        repaint();
    }

    public void clear() {
        files.removeAllElements();
        repaint();
    }

    public void setFileSorter(FileSorter sorter) {
        this.sorter = sorter;
    }

    public void registerIcon(String extension, BufferedImage icon) {
        icons.put(extension, icon);
    }

    public void unregisterIcon(String extension) {
        icons.remove(extension);
    }

    public void updateItemFromDisk(File file) {
        File[] fileList = file.listFiles();
        this.clear();

        if (fileList != null) {
            for (File f : fileList) {
                FileItem item = FileItem.createItem(f);
                if (isAutoLoadIcon && !item.isDirectory) putIcon(f);

                this.addItem(item);
            }
        }
    }

    public void updateItemFromDisk(String path) {
        this.updateItemFromDisk(new File(path));
    }

    private void putIcon(File file) {
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        if (!icons.containsKey(extension)) {
            ImageIcon icon = (ImageIcon) view.getSystemIcon(file);
            BufferedImage image = ImageConversion.toBufferedImage(icon);
            icons.put(extension, image);

            try {
                //
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setAutoLoadIcon(boolean isAutoLoadIcon) {
        this.isAutoLoadIcon = isAutoLoadIcon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < files.size(); i++) {
            FileItem item = files.get(i);

            if (isSelected.contains(item)) {
                g.setColor(FILE_SELECTED_COLOR);
                g.fillRect(0, 35 * i - notches * notchesPerUnit, this.getWidth(), 35);

                g.setColor(FILE_SELECTED_BORDER_COLOR);
                g.drawRect(0, 35 * i - notches * notchesPerUnit,
                        this.getWidth() - 1, 35 - 1);

            } else if (overIndex == i) {
                g.setColor(OVER_COLOR);
                g.fillRect(0, 35 * i - notches * notchesPerUnit, this.getWidth(), 35);
            }

            g.setColor(Color.BLACK);
            g.drawString(item.name, 35, 5 * (i + 1) + i * 25 + 5 * i + 17 - notches * notchesPerUnit);

            BufferedImage icon;
            if (!item.isDirectory) {
                if (icons.containsKey(item.extension)) {
                    icon = icons.get(item.extension);
                } else {
                    icon = icons.get("/DefaultFileIcon/");
                }
            } else {
                icon = icons.get("/DefaultMkdirIcon/");
            }

            g.drawImage(icon, 5, 5 * (i + 1) + i * 25 + 5 * i - notches * notchesPerUnit,
                    25, 25, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int y = e.getY() + notches * notchesPerUnit;
        int index = y / 35;

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (e.getClickCount() == 1) {
                if (index < files.size()) {
                    if (!e.isControlDown()) {
                        isSelected.removeAllElements();
                        isSelected.addElement(files.get(index));
                    } else {
                        if (isSelected.contains(files.get(index))) {
                            isSelected.removeElement(files.get(index));
                        } else {
                            isSelected.addElement(files.get(index));
                        }
                    }

                    repaint();
                }
            } else if (e.getClickCount() >= 2) {
                if (index < files.size()) {
                    isSelected.removeAllElements();
                    isSelected.addElement(files.get(index));

                    if (!actions.isEmpty()) {
                        for (DoubleClickListener c : actions) {
                            c.click(files.get(index));
                        }

                        repaint();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int y = e.getY() + notches * notchesPerUnit;
        int index = y / 35;

        if (index < files.size()) {
            overIndex = index;

            repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();

        if (notches > 0 && this.notches + notches >= files.size() - this.getHeight() / 35) {

            this.notches = files.size() - this.getHeight() / 35;
        }else if (notches < 0 && this.notches + notches < 0) {

            this.notches = 0;
        } else {

            overIndex += notches;
            this.notches += notches;
        }

        repaint();
    }
}
