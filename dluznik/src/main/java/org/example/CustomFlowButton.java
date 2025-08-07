package org.example;

import javax.swing.*;
import java.awt.*;

public class CustomFlowButton extends JButton {
    JLabel title = new JLabel();
    JLabel amount = new JLabel();

    void setTextColor(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(color);
            } else if (component instanceof JButton) {
                component.setForeground(color);
            } else if (component instanceof JTextField) {
                component.setForeground(color);
            } else if (component instanceof Container) {
                setTextColor((Container) component, color);
            }
        }
    }

    CustomFlowButton(CashFlow flow, CustomButton customButton){
        this.setLayout(customButton.getLayout());
        this.setPreferredSize(customButton.getPreferredSize());
        this.setBorder(customButton.getBorder());
        this.setBackground(customButton.getBackground());
        this.setForeground(customButton.getForeground());

        title.setPreferredSize(new Dimension(150, 30));
        title.setText(flow.name);
        this.add(title);

        amount.setPreferredSize(new Dimension(100, 30));
        amount.setText(String.valueOf(flow.amount));
        amount.setHorizontalTextPosition(SwingConstants.CENTER);
        this.add(amount);

        this.setTextColor(this, customButton.getForeground());
    }
}
