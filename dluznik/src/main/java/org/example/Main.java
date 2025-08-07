package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RefreshPanelExample example = new RefreshPanelExample();
            example.createAndShowGUI();
            Launcher.launch();
        });
    }

}
// to do:
// custom to
// usuwanie grupy
// platnosc jednej osoby
// wszyscy po rowno
// instrukcja