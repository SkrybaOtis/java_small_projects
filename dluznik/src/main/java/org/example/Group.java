package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {
    FinanceFileManager financeDataImporter = new FinanceFileManager();
    List<CashFlow> cashFlowList = new ArrayList<>();

    static int count = 0;
    final int id;
    String name;
    String pass;
    List<Integer>  adminsId = new ArrayList<>();
    List<Integer>  permissedId = new ArrayList<>();
    List<String> membersNames = new ArrayList<>();
    List<List<Float>> saldo = new ArrayList<>();
    List<Float> debts = new ArrayList<>();
    List<Settle> settles = new ArrayList<>();

//
//    int getId(){ return id; }
//    String getName(){ return name; }
//    public void setName(String name) { this.name = name; }
//
//    void addPerson(String name) { membersNames.add(name); }
//    void setPersonName(int id, String newName){ membersNames.set(id, newName); }


    public void setAdminsGroup(String adminsGroupData) {
        adminsGroupData = adminsGroupData.trim();
        String splitBy = ":";
        String[] admins = adminsGroupData.split(splitBy);
        List<Integer> adminsList = new ArrayList<>();
        for (String lender : admins){
            adminsList.add(Integer.valueOf(lender));
        }
        this.adminsId = adminsList;
    }


    public void setPermissionsGroup(String permissionsGroupData) {
        permissionsGroupData = permissionsGroupData.trim();
        String splitBy = ":";
        String[] permissions = permissionsGroupData.split(splitBy);
        List<Integer> permissionsList = new ArrayList<>();
        for (String perm : permissions){
            permissionsList.add(Integer.valueOf(perm));
        }
        this.permissedId = permissionsList;
    }

    public void setMembersGroup(String membersGroupData) {
        membersGroupData = membersGroupData.trim();
        String splitBy = ":";
        String[] members = membersGroupData.split(splitBy);
        this.membersNames = new ArrayList<>(Arrays.asList(members));
    }





    void importCashFlowList(){
        String link = "src/main/resources/groups/"+name+".csv";
        financeDataImporter.importData(link);
        cashFlowList = financeDataImporter.getCashFlows();
    }

    void calculateSaldo(){
//        inicjalizacja rozmiaru macierzy
        int i;
        for (i = 0; i < membersNames.size(); i++) {
            List<Float> row = new ArrayList<>();
            for (int j = 0; j < membersNames.size(); j++) {
                row.add(0.0f);
            }
            saldo.add(row);
        }
        i = 0;
        for (CashFlow flow : cashFlowList){
            for(Integer l : flow.lendersGroup){
                saldo.get(l).set(l, saldo.get(l).get(l) + flow.lendersProperties.get(i));
                i++;
            }
                i = 0;
            for(Integer b : flow.borrowersGroup){
                saldo.get(b).set(b, saldo.get(b).get(b) - flow.borrowersProperties.get(i));
                i++;
            }
                i = 0;
        }
    }

    void showSaldo(){
        for(List<Float> x : saldo){
            System.out.println("{ ");
            for(Float xy : x){
                System.out.print(xy+", ");
            }
            System.out.print(" }");
        }
        System.out.println();
    }

    void calculateDebts(){
        int indx;
        float personalSaldoValue;

        indx = 0;
        for(String ignore : membersNames){
            debts.add(0.0f);
            indx++;
        }

        for(List<Float> row : saldo) {
            indx = 0;
            for (Float columnXrow : row) {
                personalSaldoValue = debts.get(indx) + columnXrow;
                debts.set(indx, personalSaldoValue);
                indx++;
            }
        }

//        for(Float prsnlSaldo : debts){
//            System.out.print(prsnlSaldo + ", ");
//        }
    }

    int findMin(List<Float> dbts){
        int i=0;
        int indx = 0;
        Float min = Float.MAX_VALUE;
        for(Float dbt : dbts){
            if(dbt < min){ min = dbt; indx = i; }
            i++;
        }
        return indx;
    }

    int findMax(List<Float> dbts){
        int i=0;
        int indx = 0;
        Float max = Float.MIN_VALUE;
        for(Float dbt : dbts){
            if(dbt > max){ max = dbt; indx = i; }
            i++;
        }
        return indx;
    }

    boolean settled(List<Float> dbts){
        for(Float dbt : dbts){
            if(dbt != 0){ return false; }
        }
        return true;
    }

    void settling(){
        List<Float> debtsCopy = debts;
        float min, max;
        int minIndx, maxIndx;
        while(!settled(debtsCopy)){
            minIndx = findMin(debts);
            maxIndx = findMax(debts);
            min = debtsCopy.get(minIndx);
            max = debtsCopy.get(maxIndx);
            min = min*(-1);
            if( min > max ){ min = max; }

            Settle stl = new Settle(minIndx, maxIndx, min);
            settles.add(stl);

            debtsCopy.set(minIndx, debtsCopy.get(minIndx)+min);
            debtsCopy.set(maxIndx, debtsCopy.get(maxIndx)-min);

        }

    }

    void showSettles(){
        for(Settle stl : settles){
            System.out.println(stl.borrowerId + " -> " + stl.lenderId + " : " + stl.amount);
        }
    }



    Group(String name, String password, String admins, String permissed, String members) {
        id = count++;
        this.name = name;
        pass = password;
        setAdminsGroup(admins);
        setPermissionsGroup(permissed);
        setMembersGroup(members);
        importCashFlowList();
        calculateSaldo();
//        showSaldo();
        calculateDebts();
        settling();
//        showSettles();
    }



    Group(String idd, String name, String password, String admins, String permissed, String members) {
        id = Integer.parseInt(idd);
        this.name = name;
        pass = password;
        setAdminsGroup(admins);
        setPermissionsGroup(permissed);
        setMembersGroup(members);
        importCashFlowList();
        calculateSaldo();
//        showSaldo();
        calculateDebts();
        settling();
//        showSettles();
    }

}
