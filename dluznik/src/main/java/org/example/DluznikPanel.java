package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DluznikPanel {
    GroupFileManager groupFileManager = new GroupFileManager();
    AccountFileManager accountFileManager = new AccountFileManager();
    ColorPalette palette = new ColorPalette();

    static Account user;
    static List<Account> accounts = new ArrayList<>();

    JFrame frame = new JFrame("Dłużnik");
    JPanel panel = new JPanel();
    CustomPanel chooseGroup = new CustomPanel();
    CustomScrollPane scrollChooser = new CustomScrollPane();
    CardLayout cardLyt = new CardLayout();
    List<Group> groups = new ArrayList<>();
    List<Integer> groupsIds;
    List<CustomButton> groupsButton = new ArrayList<>();


    JPanel mainCard = new JPanel(new GridBagLayout());
    JPanel addGroupCard = new JPanel(new GridBagLayout());
    JPanel joinGroupCard = new JPanel(new GridBagLayout());

    int buttonCounter;

    void chooseGroupUpdater() {

        chooseGroup.removeAll();
        groupsButton.clear();

        buttonCounter = 0;
        for (Integer id : groupsIds) {
            //System.out.println(groups.get(id).name);
            CustomButton button = new CustomButton(groups.get(id).name, 200, 50, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), Color.white);
            groupsButton.add(button);
            chooseGroup.add(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMinimumSize(new Dimension(200, 50));
            button.setMaximumSize(new Dimension(200, 50));

            button.addActionListener(e -> {
                new GroupSettlement(user, accountFileManager, groups.get(id), groupFileManager);
                frame.dispose();
            });

            buttonCounter++;
        }
        chooseGroup.revalidate();
        chooseGroup.repaint();
    }

    void importGroupList() {
        String link = "src/main/resources/groups_info.csv";
        groupFileManager.importData(link);
        groups = groupFileManager.getGroups();
    }

    void exportGroupList() {
        String link = "src/main/resources/groups_info.csv";
        groupFileManager.exportData(groupFileManager.changeGroupsInString(groups), link);
    }

    void importAccountList() {
        String link = "src/main/resources/accounts.csv";
        accountFileManager.importData(link);
        accounts = accountFileManager.getAccounts();
    }

    void exportAccountList() {
        String link = "src/main/resources/accounts.csv";
        accountFileManager.exportData(accountFileManager.changeAccountsInString(accounts), link);
    }

    void createFile(String groupName) {
        String filePath = "src/main/resources/groups/" + groupName + ".csv";
        try(FileWriter ignored = new FileWriter(filePath)){
            System.out.println("File has been created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------



    void creator(String groupName, JPanel creatorPanel) {
        createFile(groupName);

        int groupsCount = groups.getLast().id+1;
        String passToGroup = groupsCount+"#"+groupName;
        Group newGroup = new Group(groupName, passToGroup, String.valueOf(user.id), String.valueOf(user.id), user.name);

        accounts.get(user.id).accessedGroups.add(newGroup.id);
        accounts.get(user.id).groupsMemberIds.add(0);


        groups.add(newGroup);
        exportGroupList();
        exportAccountList();

        groupsIds.add(newGroup.id);
        cardLyt.show(creatorPanel,  "main_card");
    }


    // -----------------------------------------------------------------------------------------------------------------

    void joiner(String link, JPanel joinerPanel){


        String [] vlink = link.split(":");
        if( !user.accessedGroups.contains(Integer.valueOf(vlink[0]))
        && (vlink.length == 3)
        && Objects.equals(groups.get(Integer.parseInt(vlink[0])).name, vlink[1])
        && Objects.equals(groups.get(Integer.parseInt(vlink[0])).pass, vlink[2])){

            user.accessedGroups.add(Integer.valueOf(vlink[0]));

            JDialog nick = new JDialog();
            JPanel nickP = new JPanel();
            JLabel nickL = new JLabel("Wybierz swoj nick, lub wpisz jesli nie znajduje sie na liscie");


            Vector<String> nicks = new Vector<>();
            List<String> nickNames = groups.get(Integer.parseInt(vlink[0])).membersNames;
            List<Integer> nickPerm = groups.get(Integer.parseInt(vlink[0])).permissedId;
            for(int i = 0; i < nickNames.size(); i++){
                if(nickPerm.get(i) == -1){
                    nicks.add(nickNames.get(i));
                }
            }

            JComboBox<String> nickC = new JComboBox<>(nicks);
            JTextField nickT = new JTextField(16);
            JButton joinGroup = new JButton("Dołącz");

            nick.setSize(300, 200);
            nickP.setBounds(0, 0, 300, 200);
            nickL.setBounds(20, 20, 240, 80);
            nickC.setBounds(20, 100, 240, 40);
            nickT.setBounds(20, 140, 240, 40);
            joinGroup.setBounds(20, 180, 240, 40);

            nickP.add(nickL);
            nickP.add(nickC);
            nickP.add(nickT);
            nickP.add(joinGroup);
            nick.add(nickP);
            nick.setVisible(true);


            nickT.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    ifFieldEmpty();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    ifFieldEmpty();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    ifFieldEmpty();
                }
                private void ifFieldEmpty() {
                    nickC.setVisible(nickT.getText().isEmpty());
                }
            });


            joinGroup.addActionListener(e1 -> {
                String inUser;
                if(!nickT.getText().isEmpty()){
                    inUser = nickT.getText();
                    groups.get(Integer.parseInt(vlink[0])).permissedId.add(user.id);
                    groups.get(Integer.parseInt(vlink[0])).membersNames.add(inUser);

                    accounts.get(user.id).accessedGroups.add(Integer.parseInt(vlink[0]));
                    accounts.get(user.id).groupsMemberIds.add(nickNames.size());

                } else {
                    inUser = (String) nickC.getSelectedItem();
                    groups.get(Integer.parseInt(vlink[0])).permissedId.set(nickNames.indexOf(inUser), user.id);

                    accounts.get(user.id).accessedGroups.add(Integer.parseInt(vlink[0]));
                    accounts.get(user.id).groupsMemberIds.add(nickNames.indexOf(inUser));
                }

                exportGroupList();
                exportAccountList();

                //groupsIds.add(Integer.parseInt(vlink[0]));
                nick.setVisible(false);
                cardLyt.show(joinerPanel,  "main_card");
            });

        } else {
            if(user.accessedGroups.contains(Integer.valueOf(vlink[0]))) {
                JOptionPane.showMessageDialog(joinerPanel, "Jesteś już w tej grupie");
            } else {
                JOptionPane.showMessageDialog(joinerPanel, "Dołączanie nie powiodło się. Sprawdź wpisany link i spróbuj ponownie.");
            }
        }

    }


    // -----------------------------------------------------------------------------------------------------------------


    void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(cardLyt);
        panel.setBounds(0, 0, 350, 500);

        // ---------------------------------------------- Main Card ----------------------------------------------------

        mainCard.setBackground(palette.colors.get(3));

        CustomPanel mainCardPanel = new CustomPanel(new FlowLayout(), 350, 500, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel mainCardUserPanel = new CustomPanel(new FlowLayout(), 300, 50, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel mainCardGroupsPanel = new CustomPanel(new FlowLayout(), 300, 300, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel mainCardButtonsPanel = new CustomPanel(new FlowLayout(), 300, 100, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));

        // ------------------- mainCardUserPanel ------------------------

        JLabel userLabel = new JLabel("Użytkownik: " + user.name);
        userLabel.setPreferredSize(new Dimension(140, 25));
        mainCardUserPanel.add(userLabel);

        CustomButton logoutButton = new CustomButton("Wyloguj", 100, 35, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), palette.colors.get(0));
        mainCardUserPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            Launcher.launch();
            frame.dispose();
        });

        // ------------------- mainCardGroupsPanel ------------------------

        chooseGroup = new CustomPanel(new FlowLayout(), new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        chooseGroup.setLayout(new BoxLayout(chooseGroup, BoxLayout.Y_AXIS));
        chooseGroupUpdater();
        scrollChooser = new CustomScrollPane(chooseGroup, 250, 240, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        mainCardGroupsPanel.add(scrollChooser);

        // ------------------- mainCardButtonsPanel ------------------------

        CustomButton toCreateGroup = new CustomButton("Stwórz grupę", 160, 35, new CustomBorder(2, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.get(0));
        mainCardButtonsPanel.add(toCreateGroup);

        CustomButton toJoinGroup = new CustomButton("Dołącz do grupy", 160, 35, new CustomBorder(2, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.get(0));
        mainCardButtonsPanel.add(toJoinGroup);

        // ------------------- mainCardPanel ------------------------
        mainCardPanel.add(mainCardUserPanel);
        mainCardPanel.add(mainCardGroupsPanel);
        mainCardPanel.add(mainCardButtonsPanel);

        mainCardPanel.setTextColor(mainCardPanel, Color.white);

        mainCard.add(mainCardPanel);

        // --------------------------------------------- Add Group Card ------------------------------------------------

        addGroupCard.setBackground(palette.colors.get(3));

        CustomPanel addGroupCardPanel = new CustomPanel(new FlowLayout(), 350, 220, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel addGroupCardDataPanel = new CustomPanel(new FlowLayout(), 300, 100, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel addGroupCardButtonsPanel = new CustomPanel(new FlowLayout(), 300, 100, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        JLabel addGroupLabel = new JLabel("Podaj nazwę", SwingConstants.CENTER);
        addGroupLabel.setPreferredSize(new Dimension(160, 25));
        addGroupCardDataPanel.add(addGroupLabel);

        JTextField addGroupName = new JTextField(15);
        addGroupName.setPreferredSize(new Dimension(160, 25));
        addGroupCardDataPanel.add(addGroupName);

        CustomButton createGroupButton = new CustomButton("Stwórz", 150, 35, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), palette.colors.get(0));
        createGroupButton.addActionListener(e -> {
            creator(addGroupName.getText(), panel);
            chooseGroupUpdater();
        });
        addGroupCardButtonsPanel.add(createGroupButton);

        CustomButton toMainCardAdd = new CustomButton("Wróć", 150, 35, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), palette.colors.get(0));
        toMainCardAdd.addActionListener(e -> cardLyt.show(panel, "main_card"));
        addGroupCardButtonsPanel.add(toMainCardAdd);

        addGroupCardPanel.add(addGroupCardDataPanel);
        addGroupCardPanel.add(addGroupCardButtonsPanel);

        addGroupCardPanel.setTextColor(addGroupCardPanel, Color.white);

        addGroupCard.add(addGroupCardPanel);

        // --------------------------------------------- Join Group Card -----------------------------------------------

        joinGroupCard.setBackground(palette.colors.get(3));

        CustomPanel joinGroupCardPanel = new CustomPanel(new FlowLayout(), 350, 220, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel joinGroupCardDataPanel = new CustomPanel(new FlowLayout(), 300, 100, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel joinGroupCardButtonsPanel = new CustomPanel(new FlowLayout(), 300, 100, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        JLabel joinGroupLabel = new JLabel("Podaj link", SwingConstants.CENTER);
        joinGroupLabel.setPreferredSize(new Dimension(160, 25));
        joinGroupCardDataPanel.add(joinGroupLabel);

        JTextField joinGroupLink = new JTextField(15);
        joinGroupLink.setPreferredSize(new Dimension(160, 25));
        joinGroupCardDataPanel.add(joinGroupLink);

        CustomButton joinGroupButton =  new CustomButton("Dołącz", 150, 35, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), palette.colors.get(0));
        joinGroupButton.addActionListener(e -> {
            joiner(joinGroupLink.getText(), panel);
            chooseGroupUpdater();
        });
        joinGroupCardButtonsPanel.add(joinGroupButton);

        CustomButton toMainCardJoin = new CustomButton("Wróć", 150, 35, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3), palette.colors.get(0));
        toMainCardJoin.addActionListener(e -> cardLyt.show(panel, "main_card"));
        joinGroupCardButtonsPanel.add(toMainCardJoin);

        joinGroupCardPanel.add(joinGroupCardDataPanel);
        joinGroupCardPanel.add(joinGroupCardButtonsPanel);

        joinGroupCardPanel.setTextColor(joinGroupCardPanel, Color.white);

        joinGroupCard.add(joinGroupCardPanel);
        // -------------------------------------------- Submit & Actions -----------------------------------------------

        panel.add(mainCard, "main_card");
        panel.add(addGroupCard, "add_group_card");
        panel.add(joinGroupCard, "join_group_card");

        toCreateGroup.addActionListener(e -> cardLyt.show(panel, "add_group_card"));
        toJoinGroup.addActionListener(e -> cardLyt.show(panel, "join_group_card"));

    }


    // -----------------------------------------------------------------------------------------------------------------


    void setDluznikPanel() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(panel);
        placeComponents(panel, frame);
        frame.setVisible(true);
    }

    DluznikPanel(Account loggedUser) {
        user = loggedUser;
        groupsIds = user.accessedGroups;

        importGroupList();
        importAccountList();
        setDluznikPanel();
    }
}
