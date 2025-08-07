package org.example;

import javax.swing.*;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {

    CustomScrollPane() {
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
    }

    CustomScrollPane(JComponent view){
        super(view);
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
    }

    CustomScrollPane(JComponent view, int width, int height, CustomBorder border, Color backColor){
        super(view);
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(border);
        this.setBackground(backColor);
    }
    CustomScrollPane(JComponent view, int widthMin, int heightMin, int widthMax, int heightMax, CustomBorder border, Color backColor){
        super(view);
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.setMinimumSize(new Dimension(widthMin, heightMin));
        this.setMaximumSize(new Dimension(widthMax, heightMax));
        this.setBorder(border);
        this.setBackground(backColor);
    }
}
