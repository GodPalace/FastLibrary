package com.godpalace.fastlibrary.gui.utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class ButtonBuilder {
    public static JButton createWindowsButton(String text) {
        JButton button = new JButton(text);

        button.setBorder(new LineBorder(new Color(89, 89, 89), 2));
        button.setBackground(new Color(225, 225, 225));
        button.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                button.setBorder(new LineBorder(new Color(0, 162, 232), 2));
                button.setBackground(new Color(200, 233, 242));
            }

            @Override
            public void focusLost(FocusEvent e) {
                button.setBorder(new LineBorder(new Color(89, 89, 89), 2));
                button.setBackground(new Color(225, 225, 225));
            }
        });

        return button;
    }

    public static JButton createWindowsButton() {
        return createWindowsButton("");
    }
}
