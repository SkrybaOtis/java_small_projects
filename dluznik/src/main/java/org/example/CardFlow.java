package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CardFlow extends JPanel {

    ColorPalette palette = new ColorPalette();

    String flowTitleString;
    String flowAmountString;
    String flowTypeString;
    String flowDataTimeString;
    List<String> flowLenders = new ArrayList<>();
    List<String> flowLendersIds = new ArrayList<>();
    List<String> flowLendersProps = new ArrayList<>();
    List<String> flowBorrowers = new ArrayList<>();
    List<String> flowBorrowersIds = new ArrayList<>();
    List<String> flowBorrowersProps = new ArrayList<>();

    int memberIndex;

    CustomPanel cashFlowsListInPanel = new CustomPanel();
    CustomScrollPane cashFlowsListInRoll;
    CustomPanel cashFlowsListOutPanel = new CustomPanel();
    CustomScrollPane cashFlowsListOutRoll;
    List<MmbrAmntPanel> cashFlowsMmbrInList = new ArrayList<>();
    List<MmbrAmntPanel> cashFlowsMmbrOutList = new ArrayList<>();

    JLabel flowTitleLabel = new JLabel("Wpisz nazwe płatności");
    JTextField flowTitle = new JTextField(20);
    JLabel flowAmountLabel = new JLabel("Wpisz kwotę");
    JTextField flowAmount = new JTextField(20);
    JLabel flowTypeLabel = new JLabel("Wybierz typ płatności");
    Vector<String> flowTps = new Vector<>();
    JComboBox<String> flowType = new JComboBox<>(flowTps);
    JLabel flowDateLabel = new JLabel("Data");
    JLabel flowTimeLabel = new JLabel("Godzina");
    JSpinner flowDate = createDateSpinner();
    JSpinner flowTime = createTimeSpinner();
    JLabel amountIn = new JLabel("Kwota");
    JLabel amountOut = new JLabel("Robota");

    CustomButton toBackOfFlow = new CustomButton("Wróć", 100, 50, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), palette.colors.getFirst());
    CustomButton actionFlow = new CustomButton(new GridBagLayout(), 100, 50, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), palette.colors.getFirst());
    CustomButton delFlow = new CustomButton("Usuń", 100, 50, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), palette.colors.getFirst());



    // =================================================================================================================
    // ----------------------------------------------- Methods ---------------------------------------------------------
    // =================================================================================================================

    void setFlowTypes(){
        flowTps.add("wydatek");
        flowTps.add("przychód");
        flowTps.add("przelew");
    }


    JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy");
        spinner.setEditor(editor);

        return spinner;
    }


    JSpinner createTimeSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        spinner.setEditor(editor);

        return spinner;
    }



    void setDefaultFlowValues(){

        for(MmbrAmntPanel member : cashFlowsMmbrInList){
            member.memberAmount.setText("0.0");
        }
        for(MmbrAmntPanel member : cashFlowsMmbrOutList){
            member.memberAmount.setText("0.0");
        }

        flowTitle.setText("");
        flowAmount.setText("");
        flowType.setSelectedIndex(-1);
        amountIn.setText("");
        amountOut.setText("");
    }

    void setSpecificFlowValues(Group group, int indx){
        CashFlow cshflw = group.financeDataImporter.cashFlows.get(indx);

        for(MmbrAmntPanel member : cashFlowsMmbrInList){
            member.memberAmount.setText("0.0");
        }
        for(MmbrAmntPanel member : cashFlowsMmbrOutList){
            member.memberAmount.setText("0.0");
        }

        for(int i=0; i < cshflw.lendersGroup.size(); i++){
            String amnt = String.valueOf(cshflw.lendersProperties.get(i));
            cashFlowsMmbrInList.get(cshflw.lendersGroup.get(i)).memberAmount.setText(amnt);
        }
        for(int i=0; i < cshflw.borrowersGroup.size(); i++){
            String amnt = String.valueOf(cshflw.borrowersProperties.get(i));
            cashFlowsMmbrOutList.get(cshflw.borrowersGroup.get(i)).memberAmount.setText(amnt);
        }
        flowTitle.setText(cshflw.name);
        flowAmount.setText(String.valueOf(cshflw.amount));
        flowType.setSelectedItem(cshflw.cashFlowType);
        amountIn.setText(String.valueOf(cshflw.amount));
        amountOut.setText(String.valueOf(cshflw.amount));
    }

    void checkValues(String flowAmountString, String valueAmountInString, String valueAmountOutString){

        if( !flowAmountString.isEmpty() && !valueAmountInString.isEmpty() && !valueAmountOutString.isEmpty()){
            try {
                float mainAmount = Float.parseFloat(flowAmountString);
                float inA = Float.parseFloat(valueAmountInString);
                float inO = Float.parseFloat(valueAmountOutString);
                actionFlow.setVisible(mainAmount == inA && mainAmount == inO);
            } catch (NumberFormatException e) {
                System.out.println("Wpisana kwota jest nieprawidlowa");
            }
        } else {
            actionFlow.setVisible(false);
        }
    }


//    String flowLendersString(List<String> lenders){
//        StringBuilder lndrs = new StringBuilder();
//        for(String lender : lenders){
//            lndrs.append(':').append(lender);
//        }
//        return lndrs.substring(1, lndrs.length());
//    }

    String flowLendersIdsString(List<String> lendersIds){
        StringBuilder lIds = new StringBuilder();
        for(String lId : lendersIds){
            lIds.append(':').append(lId);
        }
        return lIds.substring(1, lIds.length());
    }

    String flowLendersPropsString(List<String> lendersProps){
        StringBuilder lndrsPrps = new StringBuilder();
        for(String lenderProp : lendersProps){
            lndrsPrps.append(':').append(lenderProp);
        }
        return lndrsPrps.substring(1, lndrsPrps.length());
    }

//    String flowBorrowersString(List<String> borrowers){
//        StringBuilder brrwr = new StringBuilder();
//        for(String borrower : borrowers){
//            brrwr.append(':').append(borrower);
//        }
//        return brrwr.substring(1, brrwr.length());
//    }

    String flowBorrowersIdsString(List<String> borrowersIds){
        StringBuilder bIds = new StringBuilder();
        for(String bId : borrowersIds){
            bIds.append(':').append(bId);
        }
        return bIds.substring(1, bIds.length());
    }

    String flowBorrowersPropsString(List<String> borrowersProps){
        StringBuilder brrwrPrps = new StringBuilder();
        for(String borrowerProp : borrowersProps){
            brrwrPrps.append(':').append(borrowerProp);
        }
        return brrwrPrps.substring(1, brrwrPrps.length());
    }

    void changeButton(int selectedFlow){
        if(selectedFlow == -1){
            actionFlow.setText("Dodaj");
            delFlow.setVisible(false);
        } else {
            actionFlow.setText("Edytuj");
            delFlow.setVisible(true);
        }
    }


    void actionFlowEffects(Group group, int selectedFlow){
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");

        flowTitleString = flowTitle.getText();
        flowAmountString = flowAmount.getText();
        flowTypeString = String.valueOf(flowType.getSelectedItem());
        flowDataTimeString = outputFormat.format(flowDate.getValue());

        memberIndex = 0;
        flowLenders.clear();
        flowLendersIds.clear();
        flowLendersProps.clear();
        for(MmbrAmntPanel mmbrin : cashFlowsMmbrInList){
            if(mmbrin.getAmount() > 0.0f){
                flowLenders.add(mmbrin.memberName.getText());
                flowLendersIds.add(String.valueOf(memberIndex));
                flowLendersProps.add(mmbrin.getAmount().toString());
            }
            memberIndex++;
        }

        memberIndex = 0;
        flowBorrowers.clear();
        flowBorrowersIds.clear();
        flowBorrowersProps.clear();
        for(MmbrAmntPanel mmbrout : cashFlowsMmbrOutList){
            if(mmbrout.getAmount() > 0f){
                flowBorrowers.add(mmbrout.memberName.getText());
                flowBorrowersIds.add(String.valueOf(memberIndex));
                flowBorrowersProps.add(mmbrout.getAmount().toString());
            }
            memberIndex++;
        }
        memberIndex = 0;


        //System.out.println(flowTitleString + ", " + flowAmountString + ", " + flowTypeString + ", " + flowLendersString(flowLenders) + ", " + flowLendersIdsString(flowLendersIds) + ", " + flowLendersPropsString(flowLendersProps) + ", " + flowBorrowersString(flowBorrowers) + ", " + flowBorrowersIdsString(flowBorrowersIds) + ", " + flowBorrowersPropsString(flowBorrowersProps));

        if(selectedFlow == -1) {
            CashFlow newCashFlow = new CashFlow(flowTitleString, flowAmountString, flowTypeString, flowDataTimeString, flowLendersIdsString(flowLendersIds), flowLendersPropsString(flowLendersProps), flowBorrowersIdsString(flowBorrowersIds), flowBorrowersPropsString(flowBorrowersProps) );
            group.financeDataImporter.cashFlows.add(newCashFlow);
        } else {
            CashFlow newCashFlow = new CashFlow(selectedFlow, flowTitleString, flowAmountString, flowTypeString, flowDataTimeString, flowLendersIdsString(flowLendersIds), flowLendersPropsString(flowLendersProps), flowBorrowersIdsString(flowBorrowersIds), flowBorrowersPropsString(flowBorrowersProps) );
            group.financeDataImporter.cashFlows.set(selectedFlow, newCashFlow);
        }

        String flowContentString = group.financeDataImporter.changeFinancesInString( group.financeDataImporter.cashFlows );

        String link = "src/main/resources/groups/"+group.name+".csv";
        group.financeDataImporter.exportData(flowContentString, link);


        setDefaultFlowValues();
    }

    // =================================================================================================================
    // ------------------------------------------ place components -----------------------------------------------------
    // =================================================================================================================

    CardFlow(Group group){
        setFlowTypes();
        this.setLayout(new GridBagLayout());
        this.setBackground(palette.colors.get(3));
        this.setPreferredSize(new Dimension(600, 900));




// ---------------------------------------------------------------------------------------------------------------------
        flowTitle.setHorizontalAlignment(JTextField.CENTER);
        flowAmount.setHorizontalAlignment(JTextField.CENTER);


        CustomPanel flowTitlePanel = new CustomPanel(new FlowLayout(), 200, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel flowAmountPanel = new CustomPanel(new FlowLayout(), 200, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel flowTypePanel = new CustomPanel(new FlowLayout(), 200, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        CustomPanel flowDatePanel = new CustomPanel(new FlowLayout(), 130, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel flowTimePanel = new CustomPanel(new FlowLayout(), 130, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        CustomPanel amountInPanel = new CustomPanel(new FlowLayout(), 150, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel amountOutPanel = new CustomPanel(new FlowLayout(), 150, 60,  new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        CustomPanel flowBasicDataPanel = new CustomPanel(new FlowLayout(), 220, 200, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel flowDateTimePanel = new CustomPanel(new GridLayout(1,2), 500, 50, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        CustomPanel flowAmountEqualsPanel = new CustomPanel(new GridLayout(1,2), 500, 50, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(2));

        CustomPanel flowButtonsPanel = new CustomPanel(new FlowLayout(), 450, 60, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));



        cashFlowsListInPanel = new CustomPanel(new FlowLayout(), new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        cashFlowsListInPanel.setLayout(new BoxLayout(cashFlowsListInPanel, BoxLayout.Y_AXIS));

        cashFlowsListInRoll = new CustomScrollPane(cashFlowsListInPanel, 275, 250, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));


        cashFlowsListOutPanel = new CustomPanel(new FlowLayout(), new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        cashFlowsListOutPanel.setLayout(new BoxLayout(cashFlowsListOutPanel, BoxLayout.Y_AXIS));

        cashFlowsListOutRoll = new CustomScrollPane(cashFlowsListOutPanel, 275, 250, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));

        CustomPanel cashFlowListsPanel = new CustomPanel(new FlowLayout(), 590, 400, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));


        CustomPanel flowAllPanel = new CustomPanel(new FlowLayout(), 600, 900, 700, 1000, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));


        flowTitlePanel.add(flowTitleLabel);
        flowTitlePanel.add(flowTitle);
        flowAmountPanel.add(flowAmountLabel);
        flowAmountPanel.add(flowAmount);
        flowTypePanel.add(flowTypeLabel);
        flowTypePanel.add(flowType);


        flowDatePanel.add(flowDateLabel);
        flowDatePanel.add(flowDate);
        flowTimePanel.add(flowTimeLabel);
        flowTimePanel.add(flowTime);

        amountInPanel.add(amountIn);
        amountOutPanel.add(amountOut);

        flowBasicDataPanel.add(flowTitlePanel);
        flowBasicDataPanel.add(flowAmountPanel);
        flowBasicDataPanel.add(flowTypePanel);

        flowDateTimePanel.add(flowDatePanel);
        flowDateTimePanel.add(flowTimePanel);

        flowAmountEqualsPanel.add(amountInPanel);
        flowAmountEqualsPanel.add(amountOutPanel);

        cashFlowListsPanel.add(cashFlowsListInRoll);
        cashFlowListsPanel.add(cashFlowsListOutRoll);

        flowButtonsPanel.add(toBackOfFlow);
        flowButtonsPanel.add(actionFlow);
        flowButtonsPanel.add(delFlow);

        flowAllPanel.add(flowBasicDataPanel);
        flowAllPanel.add(flowDateTimePanel);
        flowAllPanel.add(flowAmountEqualsPanel);
        flowAllPanel.add(cashFlowListsPanel);
        flowAllPanel.add(flowButtonsPanel);

        flowAllPanel.setTextColor(flowAllPanel, Color.white);

        this.add(flowAllPanel);


        // ------------------------------------------- scrollPanels --------------------------------------------------------


        for(String memberIn : group.membersNames){
            MmbrAmntPanel mmbrAmntIn = new MmbrAmntPanel(memberIn);
            cashFlowsListInPanel.add(mmbrAmntIn);
            cashFlowsMmbrInList.add(mmbrAmntIn);

            mmbrAmntIn.memberAmount.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    ifFieldChangeIn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    ifFieldChangeIn();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    ifFieldChangeIn();
                }
                private void ifFieldChangeIn() {
                    float inAmount = 0;
                    for(MmbrAmntPanel amntI : cashFlowsMmbrInList){
                        inAmount = inAmount + amntI.getAmount();
                    }
                    amountIn.setText(String.valueOf(inAmount));
                    checkValues(flowAmount.getText(), amountIn.getText(), amountOut.getText());
                }
            });

        }

        for(String memberOut : group.membersNames){
            MmbrAmntPanel mmbrAmntOut = new MmbrAmntPanel(memberOut);
            cashFlowsListOutPanel.add(mmbrAmntOut);
            cashFlowsMmbrOutList.add(mmbrAmntOut);

            mmbrAmntOut.memberAmount.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    ifFieldChangeOut();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    ifFieldChangeOut();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    ifFieldChangeOut();
                }
                private void ifFieldChangeOut() {
                    float outAmount = 0;
                    for(MmbrAmntPanel amntO : cashFlowsMmbrOutList){
                        outAmount = outAmount + amntO.getAmount();
                    }
                    amountOut.setText(String.valueOf(outAmount));
                    checkValues(flowAmount.getText(), amountIn.getText(), amountOut.getText());
                }
            });
        }


// ---------------------------------------------------------------------------------------------------------------------

        flowAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }
            private void ifAmountsEquals() {
                checkValues(flowAmount.getText(), amountIn.getText(), amountOut.getText());
            }
        });

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Sprawdzanie, czy wartości są równe

        flowAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ifAmountsEquals();
            }
            private void ifAmountsEquals() {
                checkValues(flowAmount.getText(), amountIn.getText(), amountOut.getText());
            }
        });

// ---------------------------------------------------------------------------------------------------------------------
        setDefaultFlowValues();

    }




}
