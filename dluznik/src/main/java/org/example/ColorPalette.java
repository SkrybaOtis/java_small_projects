package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorPalette {
    List<Color> colors = new ArrayList<>();
//    ColorPalette(){
//        colors.add(new Color(221, 72, 20));
//        colors.add(new Color(119, 33, 111));
//        colors.add(new Color(94, 39, 80));
//        colors.add(new Color(44, 0, 30));
//        colors.add(new Color(174, 167, 159));
//        colors.add(new Color(121, 42, 15));
//        colors.add(new Color(51, 51, 51));
//        colors.add(new Color(255, 255, 255));
//    }
    ColorPalette(){
        colors.add(new Color(100, 100, 100));
        colors.add(new Color(75, 75, 75));
        colors.add(new Color(50, 50, 50));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(174, 167, 159));
        colors.add(new Color(121, 42, 15));
        colors.add(new Color(51, 51, 51));
        colors.add(new Color(255, 255, 255));
    }

    void changeColor(int i, Color color){
        colors.set(i, color);
    }

}
