package com.godpalace.fastlibrary.gui.swing.drawpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

public class JDrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final Set<Pixel> pixels;
    private Color currentColor;
    private int currentWidth, currentHeight;

    public JDrawPanel() {
        pixels = new HashSet<>();
        currentColor = Color.BLACK;
        currentWidth = 1;
        currentHeight = 1;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setDrawColor(Color color) {
        currentColor = color;
    }

    public void setDrawSize(int width, int height) {
        currentWidth = width;
        currentHeight = height;
    }

    public void clear() {
        pixels.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (Pixel pixel : pixels) {
            g.setColor(pixel.color);
            g.fillOval(pixel.x, pixel.y, pixel.w, pixel.h);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pixels.add(new Pixel(e.getX(), e.getY(), currentWidth, currentHeight, currentColor));
        repaint();
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
        pixels.add(new Pixel(e.getX(), e.getY(), currentWidth, currentHeight, currentColor));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
