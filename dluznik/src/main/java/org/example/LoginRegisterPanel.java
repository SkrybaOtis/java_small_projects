package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginRegisterPanel {
    static AccountFileManager accountFileManager = new AccountFileManager();
    static List<Account> accounts;
    static Integer id;
    ColorPalette palette = new ColorPalette();

    private static boolean isLoged(String nick, String password){
        for(Account account : accounts){
            if(account.name.equals(nick)){
                if(account.password.equals(password)) {
                    id = account.id;
                    return true;
                }
            }
        }
        return false;
    }

    void createLRPanel(AccountFileManager accountFileManager) {
        LoginRegisterPanel.accounts = accountFileManager.getAccounts();
        LoginRegisterPanel.accountFileManager = accountFileManager;

        JFrame frame = new JFrame("Login Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(frame, panel);
        frame.setVisible(true);
    }

    void placeComponents(JFrame frame, JPanel panel) {
        CardLayout cardLayout = new CardLayout();
        panel.setLayout(cardLayout);

        // --------------------------------------------- login panel ---------------------------------------------------
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(palette.colors.get(3));

        CustomPanel allLoginComponents = new CustomPanel(new FlowLayout(), 300, 200, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel getLoginData = new CustomPanel(new FlowLayout(), 250, 75, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel loginButtonsPanel = new CustomPanel(new FlowLayout(), 150, 75, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));


        JLabel loginUserLabel = new JLabel("Username:");
        loginUserLabel.setPreferredSize(new Dimension(80, 25));
        getLoginData.add(loginUserLabel);

        JTextField loginUserText = new JTextField(10);
        loginUserText.setPreferredSize(new Dimension(100, 25));
        getLoginData.add(loginUserText);

        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordLabel.setPreferredSize(new Dimension(80, 25));
        getLoginData.add(loginPasswordLabel);

        JPasswordField loginPasswordText = new JPasswordField(10);
        loginPasswordText.setPreferredSize(new Dimension(100, 25));
        getLoginData.add(loginPasswordText);

        getLoginData.setTextColor(getLoginData, Color.white);
        allLoginComponents.add(getLoginData);

        CustomButton loginButton = new CustomButton("Zaloguj", 120, 25, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst());
        loginButtonsPanel.add(loginButton);
        CustomButton toRegister = new CustomButton("Rejestracja", 120, 25, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst());
        loginButtonsPanel.add(toRegister);

        allLoginComponents.add(loginButtonsPanel);

        loginPanel.add(allLoginComponents);

        loginButton.addActionListener(e -> {
            String user = loginUserText.getText();
            String password = new String(loginPasswordText.getPassword());

            if (isLoged(user, password)) {
                Account account = accounts.get(id);
                new DluznikPanel(account);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(panel, "Login failed. Try again.");
            }
        });
        toRegister.addActionListener(e -> cardLayout.show(panel, "register_panel"));

        panel.add(loginPanel, "login_panel");

        // ----------------------------------------- register panel ----------------------------------------------------
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(palette.colors.get(3));

        CustomPanel allRegisterComponents = new CustomPanel(new FlowLayout(), 300, 200, new CustomBorder(2, palette.colors.get(1), 2), palette.colors.get(3));
        CustomPanel getRegisterData = new CustomPanel(new FlowLayout(), 250, 75, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel registerButtonsPanel = new CustomPanel(new FlowLayout(), 150, 75, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        JLabel registerUserLabel = new JLabel("Username:");
        registerUserLabel.setPreferredSize(new Dimension(80, 25));
        getRegisterData.add(registerUserLabel);

        JTextField registerUserText = new JTextField(10);
        registerUserText.setPreferredSize(new Dimension(100, 25));
        getRegisterData.add(registerUserText);

        JLabel registerPasswordLabel = new JLabel("Password:");
        registerPasswordLabel.setPreferredSize(new Dimension(80, 25));
        getRegisterData.add(registerPasswordLabel);

        JPasswordField registerPasswordText = new JPasswordField(10);
        registerPasswordText.setPreferredSize(new Dimension(100, 25));
        getRegisterData.add(registerPasswordText);

        getRegisterData.setTextColor(getRegisterData, Color.white);
        allRegisterComponents.add(getRegisterData);

        CustomButton registerButton = new CustomButton("Zarejestruj", 120, 25, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst());
        registerButtonsPanel.add(registerButton);
        CustomButton toLogin = new CustomButton("Logowanie", 120, 25, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst());
        registerButtonsPanel.add(toLogin);

        allRegisterComponents.add(registerButtonsPanel);

        registerPanel.add(allRegisterComponents);

        registerButton.addActionListener(e -> {
            String user = registerUserText.getText();
            String password = new String(registerPasswordText.getPassword());
            accounts.add(new Account(String.valueOf(accounts.getLast().id+1), user, password,"", ""));
            accountFileManager.exportData(accountFileManager.changeAccountsInString(accounts), "src/main/resources/accounts.csv");
            registerUserText.setText("");
            registerPasswordText.setText("");
            cardLayout.show(panel, "login_panel");
        });

        toLogin.addActionListener(e -> cardLayout.show(panel, "login_panel"));

        panel.add(registerPanel, "register_panel");


    }

}
