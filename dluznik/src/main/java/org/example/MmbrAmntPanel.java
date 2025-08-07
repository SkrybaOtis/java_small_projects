package org.example;

import javax.swing.*;
import java.awt.*;

public class MmbrAmntPanel extends CustomPanel {
    ColorPalette palette = new ColorPalette();
    JLabel memberName = new JLabel();
    JTextField memberAmount = new JTextField(5);
//    JLabel memberAmountLabel = new JLabel();

    Float getAmount(){
        String amountValueString = memberAmount.getText();
        if(!amountValueString.isEmpty()){
            try {
                Float.parseFloat(amountValueString);
                return Float.parseFloat(amountValueString);
            } catch (NumberFormatException e) {
                return 0f;
            }
        }
        return 0f;
    }

    void setMemberName(String name){
        memberName.setPreferredSize(new Dimension(75, 30));
        this.memberName.setText(name);
        this.add(memberName);
    }
//    void setMemberAmount(String amount){
//        memberAmount.setPreferredSize(new Dimension(75, 30));
//        memberAmount.setHorizontalAlignment(SwingConstants.RIGHT);
//        this.memberAmount.setText(amount);
//        this.add(memberAmount);
//    }
//    void setMemberAmountLabel(int amount){
//        memberAmountLabel.setPreferredSize(new Dimension(75, 30));
//        memberAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        this.memberAmountLabel.setText(String.valueOf(amount));
//        this.add(memberAmountLabel);
//    }

//    MmbrAmntPanel() {
//        this.setLayout(new FlowLayout());
//        this.add(memberName);
//        this.add(memberAmount);
//    }

    MmbrAmntPanel(String name){
        this.setLayout(new GridLayout());
        this.setMinimumSize(new Dimension(225, 35));
        this.setMaximumSize(new Dimension(225, 35));
        this.setBorder(new CustomBorder(2, palette.colors.get(1), 1));
        this.setBackground(palette.colors.get(3));
        setMemberName(name);
        memberAmount.setPreferredSize(new Dimension(75, 30));
        //memberAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(memberAmount);
        setTextColor(this, Color.white);
    }
//    MmbrAmntPanel(String name, String amount) {
//        this.setLayout(new FlowLayout());
//        setMemberName(name);
//        setMemberAmount(amount);
//    }
//    public MmbrAmntPanel(String name, int amount){
//        this.setLayout(new GridLayout());
//        this.setMinimumSize(new Dimension(200, 35));
//        this.setMaximumSize(new Dimension(200, 35));
//        this.setBorder(new CustomBorder(1, Color.cyan, 1));
//        setMemberName(name);
//        setMemberAmountLabel(amount);
//    }
}
