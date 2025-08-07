package org.example;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    CustomBorder customBorder;
    CustomButton(String text, int width, int height, CustomBorder border){
        this.setText(text);
        customBorder = border;
        this.setBorder(customBorder);
        this.setPreferredSize(new Dimension(width, height));
    }
    CustomButton(String text, int width, int height, CustomBorder border, Color backColor, Color textColor){
        this.setText(text);
        customBorder = border;
        this.setBorder(customBorder);
        this.setBackground(backColor);
        this.setForeground(textColor);
        this.setPreferredSize(new Dimension(width, height));
    }
    CustomButton(LayoutManager lyt, int width, int height, CustomBorder border){
        this.setLayout(lyt);
        customBorder = border;
        this.setBorder(customBorder);
        this.setPreferredSize(new Dimension(width, height));
    }
    CustomButton(LayoutManager lyt, int width, int height, CustomBorder border, Color backColor, Color textColor){
        this.setLayout(lyt);
        customBorder = border;
        this.setBorder(customBorder);
        this.setBackground(backColor);
        this.setForeground(textColor);
        this.setPreferredSize(new Dimension(width, height));
    }
}
