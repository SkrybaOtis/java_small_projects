package org.example;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    ColorPalette palette = new ColorPalette();
    CustomBorder customBorder;
    void setTextColor(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(palette.colors.getLast());
            } else if (component instanceof JButton) {
                component.setForeground(palette.colors.getFirst());
            } else if (component instanceof Container) {
                setTextColor((Container) component, color);
            }
        }
    }
    CustomPanel(){}
    CustomPanel(LayoutManager lyt, CustomBorder border, Color backColor){
        this.setLayout(lyt);
        customBorder = border;
        this.setBorder(customBorder);
        this.setBackground(backColor);
    }

    CustomPanel(LayoutManager lyt, int width, int height, CustomBorder border, Color backColor){
        this.setLayout(lyt);
        this.setPreferredSize(new Dimension(width, height));
        customBorder = border;
        this.setBorder(customBorder);
        this.setBackground(backColor);
    }

    CustomPanel(LayoutManager lyt, int widthMin, int heightMin, int widthMax, int heightMax, CustomBorder border, Color backColor){
        this.setLayout(lyt);
        this.setMinimumSize(new Dimension(widthMin, heightMin));
        this.setMaximumSize(new Dimension(widthMax, heightMax));
        customBorder = border;
        this.setBorder(customBorder);
        this.setBackground(backColor);
    }

}
