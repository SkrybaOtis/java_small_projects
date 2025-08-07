package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountFileManager implements FileManager {
    private static final Logger logger = LoggerFactory.getLogger(AccountFileManager.class);

    String line;
    String splitBy = ", ";
    String[] values;

    Account account;
    List<Account> accounts = new ArrayList<>();

    @Override
    public void importData(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    //System.out.println(line);
                    //line = line.substring(1, line.length() - 1);
                    values = line.split(splitBy);
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].substring(1, values[i].length() - 1);
                    }

                    //System.out.println(values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4]);
                    account = new Account(values[0], values[1], values[2], values[3], values[4]);
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
        }

    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void exportData(String content, String filePath){
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("File has been overwritten successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file: " + e.getMessage());
        }
    }

    String changeAccountsInString(List<Account> accounts){
        StringBuilder all = new StringBuilder();
        String quoteMark = String.valueOf('"');
        String between = '"'+", "+'"';
        String values;
        StringBuilder valuesGroup = new StringBuilder();
        for( Account accnt : accounts){
            values = quoteMark + accnt.id;
            values = values + between + accnt.name;
            values = values + between + accnt.password;

            for(Integer v : accnt.accessedGroups){
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();


            for(Integer v : accnt.groupsMemberIds){
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            values = values + quoteMark + "\n";
            all.append(values);
        }
        //System.out.print(values);
        return all.toString();
    }






}