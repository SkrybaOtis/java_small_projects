package org.example;

import java.util.List;

public class Launcher {

    static AccountFileManager accountFileManager = new AccountFileManager();
    static List<Account> accounts;
    static LoginRegisterPanel lr = new LoginRegisterPanel();


    public static void launch() {
        System.out.println("No siema");
        accountFileManager.importData("src/main/resources/accounts.csv");
        accounts = accountFileManager.getAccounts();

        lr.createLRPanel(accountFileManager);
    }
}
