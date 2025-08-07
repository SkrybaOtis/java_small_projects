package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinanceFileManager implements FileManager {
    private static final Logger logger = LoggerFactory.getLogger(AccountFileManager.class);

    String line;
    String splitBy = ", ";
    String[] values;
    CashFlow cashFlow;
    List<CashFlow> cashFlows = new ArrayList<>();

    @Override
    public void importData(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    values = line.split(splitBy);
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].substring(1, values[i].length() - 1);
                    }
                    //System.out.println(values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4] + ", " + values[5] + ", " + values[6] + ", " + values[7]);
                    cashFlow = new CashFlow(values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
                    cashFlows.add(cashFlow);
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
        }

    }

    public List<CashFlow> getCashFlows() {
        return cashFlows;
    }

    public void exportData(String content, String filePath){
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("File has been overwritten successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file: " + e.getMessage());
        }
    }


    String changeFinancesInString(List<CashFlow> cashFlows){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
        StringBuilder all = new StringBuilder();
        String quoteMark = String.valueOf('"');
        String between = '"'+", "+'"';
        String values;
        StringBuilder valuesGroup = new StringBuilder();
        for( CashFlow flow : cashFlows){
            values = quoteMark + flow.id;
            values = values + between + flow.name;
            values = values + between + flow.amount;
            values = values + between + flow.cashFlowType;

            Date date = flow.dateAndTime.getTime();
            values = values + between + dateTimeFormat.format(date);

            for(Integer v : flow.lendersGroup){
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            for(Float v : flow.lendersProperties){
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            for(Integer v : flow.borrowersGroup){
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            for(Float v : flow.borrowersProperties){
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