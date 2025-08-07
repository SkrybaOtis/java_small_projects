package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RefreshPanelExample {
    private JFrame frame;
    private JPanel chooseGroup;
    private JScrollPane scrollChooser;
    private List<JButton> groupsButton;
    private List<String> groupNames;

    public RefreshPanelExample() {
        frame = new JFrame("Refresh JPanel Example");
        chooseGroup = new JPanel();
        scrollChooser = new JScrollPane(chooseGroup);
        groupsButton = new ArrayList<>();
        groupNames = new ArrayList<>();
    }

    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Initial setup for chooseGroup JPanel
        chooseGroup.setLayout(new BoxLayout(chooseGroup, BoxLayout.Y_AXIS));
        chooseGroup.setBounds(110, 100, 180, 240);

        // Sample group names
        groupNames.add("Group 1");
        groupNames.add("Group 2");

        updateGroupButtons();

        JButton refreshButton = new JButton("Refresh Panel");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simulate adding a new group and updating the panel
                groupNames.add("Group " + (groupNames.size() + 1));
                updateGroupButtons();
            }
        });

        frame.add(scrollChooser, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateGroupButtons() {
        chooseGroup.removeAll();
        groupsButton.clear();

        for (String groupName : groupNames) {
            JButton button = new JButton(groupName);
            groupsButton.add(button);
            chooseGroup.add(button);
        }

        chooseGroup.revalidate();
        chooseGroup.repaint();

        scrollChooser.revalidate();
        scrollChooser.repaint();
    }


}
