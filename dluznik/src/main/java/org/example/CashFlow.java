package org.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CashFlow {
    static int count = 0;
    final int id;
    String name;
    float amount;
    String cashFlowType;
    GregorianCalendar dateAndTime;
    List<Integer> lendersGroup;
    List<Float> lendersProperties;
    List<Integer> borrowersGroup;
    List<Float> borrowersProperties;


    public void setLendersGroup(String lendersGroupData) {
        lendersGroupData = lendersGroupData.trim();
        String splitBy = ":";
        String[] lenders = lendersGroupData.split(splitBy);
        List<Integer> lendersList = new ArrayList<>();
        for (String lender : lenders){
            lendersList.add(Integer.valueOf(lender));
        }
        this.lendersGroup = lendersList;
    }
    public void setLendersProperties(String lendersPropertiesData) {
        lendersPropertiesData = lendersPropertiesData.trim();
        String splitBy = ":";
        String[] lendersProps = lendersPropertiesData.split(splitBy);
        List<Float> lendersPropertiesList = new ArrayList<>();
        for (String props : lendersProps){
            lendersPropertiesList.add(Float.valueOf(props));
        }
        this.lendersProperties = lendersPropertiesList;
    }



    public void setBorrowersGroup(String borrowersGroupData) {
        borrowersGroupData = borrowersGroupData.trim();
        String splitBy = ":";
        String[] borrowers = borrowersGroupData.split(splitBy);
        List<Integer> borrowersList = new ArrayList<>();
        for (String borrower : borrowers){
            borrowersList.add(Integer.valueOf(borrower));
        }
        this.borrowersGroup = borrowersList;
    }

    public void setBorrowersProperties(String borrowersPropertiesData) {
        borrowersPropertiesData = borrowersPropertiesData.trim();
        String splitBy = ":";
        String[] borrowersProps = borrowersPropertiesData.split(splitBy);
        List<Float> borrowersPropertiesList = new ArrayList<>();
        for (String props : borrowersProps){
            borrowersPropertiesList.add(Float.valueOf(props));
        }
        this.borrowersProperties = borrowersPropertiesList;
    }

    public void setDateAndTime(String dateAndTimeData) {
        dateAndTimeData = dateAndTimeData.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateAndTimeData, formatter);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        this.dateAndTime = new GregorianCalendar();
        this.dateAndTime.setTime(date);
    }

     CashFlow(String title, String amount, String type, String dateTime, String lenders, String lendersProperties, String borrowers, String borrowerProperties) {
        id = count++;
        name = title;
        this.amount = Float.parseFloat(amount);
        cashFlowType = type;
        setDateAndTime(dateTime);
        setLendersGroup(lenders);
        setLendersProperties(lendersProperties);
        setBorrowersGroup(borrowers);
        setBorrowersProperties(borrowerProperties);
    }

    CashFlow(int ids, String title, String amount, String type, String dateTime, String lenders, String lendersProperties, String borrowers, String borrowerProperties) {
        id = ids;
        name = title;
        this.amount = Float.parseFloat(amount);
        cashFlowType = type;
        setDateAndTime(dateTime);
        setLendersGroup(lenders);
        setLendersProperties(lendersProperties);
        setBorrowersGroup(borrowers);
        setBorrowersProperties(borrowerProperties);
    }
}
