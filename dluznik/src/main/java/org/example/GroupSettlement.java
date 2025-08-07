package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Calendar;
import java.util.List;

public class GroupSettlement{


    // =================================================================================================================
    //  ------------------------------------------ allCardsData --------------------------------------------------------
    // =================================================================================================================
    GroupFileManager groupFileManager;
    List<Group> groups;
    AccountFileManager accountFileManager;
    Account user;

    static Group group;
    ColorPalette palette = new ColorPalette();
    Color bckgrndclr = palette.colors.get(3);
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    CardLayout cardLyt = new CardLayout();


    JPanel mainCashFlows;
    CardFlow addCashFlow;
    CardFlow editCashFlow;

    // =================================================================================================================
    // ------------------------------------------ mainCashFlowData -----------------------------------------------------
    // =================================================================================================================

    CustomPanel finalBalance = new CustomPanel();

    CustomScrollPane cashFlowsRoll = new CustomScrollPane();
    CustomPanel cashFlowsPanel = new CustomPanel();
    List<CustomFlowButton> flows = new ArrayList<>();

    int selectedFlow;

    CustomScrollPane dutiesRoll = new CustomScrollPane();
    CustomPanel dutiesPanel = new CustomPanel();
    List<CustomPanel> duties = new ArrayList<>();

    CustomPanel filterFlowsPanel = new CustomPanel();
    JLabel startDateLabel = new JLabel("Od: ");
    JLabel endDateLabel = new JLabel("Do: ");
    JSpinner startDateSpinner = createDateSpinner();
    JSpinner endDateSpinner = createDateSpinner();
    JLabel minAmountLabel = new JLabel("Kwota min:");
    JLabel maxAmountLabel = new JLabel("Kwota max:");
    JSpinner minAmount = new JSpinner();
    JSpinner maxAmount = new JSpinner();
    JLabel titleLabel = new JLabel("Nazwa: ");
    JTextField title = new JTextField();



    CustomPanel mainPanel;
    CustomPanel mainFlowsPanel;
    CustomPanel mainSettlesPanel;
    CustomScrollPane mainScrollPane;

    // =================================================================================================================
    // ------------------------------------------- addCashFlowData -----------------------------------------------------
    // =================================================================================================================

    // =================================================================================================================
    // ------------------------------------------ allCashFlowMethods --------------------------------------------------
    // =================================================================================================================

    void importGroupList() {
        groups = new ArrayList<>();
        String link = "src/main/resources/groups_info.csv";
        groupFileManager.importData(link);
        groups = groupFileManager.groups;
    }

    void exportGroupList() {
        String link = "src/main/resources/groups_info.csv";
        groupFileManager.exportData(groupFileManager.changeGroupsInString(groups), link);
    }

    JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy");
        spinner.setEditor(editor);

        return spinner;
    }

    // =================================================================================================================
    // ------------------------------------------ mainCashFlowMethods --------------------------------------------------
    // =================================================================================================================


    void updateCashFlows(){
        cashFlowsPanel.removeAll();
        flows.clear();

//        int flowsCounter = 0;
//        for(CashFlow flow : group.financeDataImporter.cashFlows){
//            int finalFlowsCounter = flowsCounter;
//            CustomFlowButton flw = new CustomFlowButton(flow);
        for(int itr = group.financeDataImporter.cashFlows.size()-1; itr >= 0; itr--){

            int finalFlowsCounter = itr;
            CustomFlowButton flw = new CustomFlowButton(group.financeDataImporter.cashFlows.get(itr), new CustomButton(new GridLayout(), 250, 50, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst()));
            flows.add(flw);
            cashFlowsPanel.add(flw);

            flw.addActionListener(e -> {
                selectedFlow = finalFlowsCounter;
                editCashFlow.changeButton(selectedFlow);
                editCashFlow.setSpecificFlowValues(group, finalFlowsCounter);
                cardLyt.show(panel, "edit_flow_card");
                frame.setSize(600, 950);
            });
//            flowsCounter++;
        }
        cashFlowsPanel.revalidate();
        cashFlowsPanel.repaint();
    }


    void updateDuties(){
        dutiesPanel.removeAll();
        duties.clear();

        for(Settle stl : group.settles){
            CustomPanel nstl = new CustomPanel(new GridLayout(), 350, 50, 350, 50, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3));
            JLabel borrower = new JLabel(group.membersNames.get(stl.borrowerId));
            JLabel axe = new JLabel("==>");
            JLabel lender = new JLabel(group.membersNames.get(stl.lenderId));
            JLabel amount = new JLabel(String.valueOf(stl.amount));
            nstl.add(borrower);
            nstl.add(axe);
            nstl.add(lender);
            nstl.add(amount);
            duties.add(nstl);
            dutiesPanel.add(nstl);


        }
        dutiesPanel.revalidate();
        dutiesPanel.repaint();
    }

    void filterCashFlow(){
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();
        int amountMin = Integer.parseInt(minAmount.getValue().toString());
        int amountMax = Integer.parseInt(maxAmount.getValue().toString());
        String titleVal = title.getText();



        if(startDate.after(endDate) || amountMin > amountMax){
            JOptionPane.showMessageDialog(panel, "Wpisano niepoprawnie daty lub kwoty");
        } else {

            cashFlowsPanel.removeAll();
            flows.clear();

            for(int itr = group.financeDataImporter.cashFlows.size()-1; itr >= 0; itr--){
                CashFlow currFlow = group.financeDataImporter.cashFlows.get(itr);
                Date dt = currFlow.dateAndTime.getTime();
                if(!dt.before(startDate) && !dt.after(endDate)) {
                    if ( !(currFlow.amount < amountMin) && !(currFlow.amount > amountMax) ) {

                        if (!titleVal.isEmpty()) {
                            if (Objects.equals(currFlow.name, titleVal)) {
                                CustomFlowButton flw = new CustomFlowButton(group.financeDataImporter.cashFlows.get(itr), new CustomButton(new GridLayout(), 250, 50, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst()));
                                flows.add(flw);
                                cashFlowsPanel.add(flw);

                                int finalItr = itr;
                                flw.addActionListener(e -> {
                                    selectedFlow = finalItr;
                                    editCashFlow.changeButton(selectedFlow);
                                    editCashFlow.setSpecificFlowValues(group, finalItr);
                                    cardLyt.show(panel, "edit_flow_card");
                                    frame.setSize(600, 950);
                                });
                            }
                        } else {
                            CustomFlowButton flw = new CustomFlowButton(group.financeDataImporter.cashFlows.get(itr), new CustomButton(new GridLayout(), 250, 50, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3), palette.colors.getFirst()));
                            flows.add(flw);
                            cashFlowsPanel.add(flw);

                            int finalItr1 = itr;
                            flw.addActionListener(e -> {
                                selectedFlow = finalItr1;
                                editCashFlow.changeButton(selectedFlow);
                                editCashFlow.setSpecificFlowValues(group, finalItr1);
                                cardLyt.show(panel, "edit_flow_card");
                                frame.setSize(600, 950);
                            });
                        }
                    }
                }
            }
            cashFlowsPanel.revalidate();
            cashFlowsPanel.repaint();
        }
    }


    // =================================================================================================================
    // ------------------------------------------ addCashFlowMethods ---------------------------------------------------
    // =================================================================================================================



    // =================================================================================================================
    // ------------------------------------------ place components -----------------------------------------------------
    // =================================================================================================================


    void placeComponents(JPanel panel){
        mainCashFlows = new JPanel();
        addCashFlow = new CardFlow(group);
        editCashFlow = new CardFlow(group);

        panel.setLayout(cardLyt);
        panel.setMinimumSize(new Dimension(550, 700));
        panel.setMaximumSize(new Dimension(1000, 700));

        mainCashFlows.setBackground(bckgrndclr);
        addCashFlow.setBackground(bckgrndclr);
        editCashFlow.setBackground(bckgrndclr);


    // -------------------------------------------------------------------------------------------------------------

    // -------------------------------------------- mainCashFlows --------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------

        mainCashFlows.setLayout(new GridBagLayout());
        mainCashFlows.setBackground(palette.colors.get(3));


        mainPanel  = new CustomPanel(new GridBagLayout(), 525, 550, 800, 700, new CustomBorder(5, palette.colors.get(1), 0),  palette.colors.get(3));
        mainFlowsPanel = new CustomPanel(new FlowLayout(),  500, 600,  new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3));
        mainSettlesPanel = new CustomPanel(new FlowLayout(), 500, 600,  new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3));


        // ----------------------------------------------------------------------
        filterFlowsPanel = new CustomPanel(new GridLayout(3, 4, 2, 2), 450, 100, new CustomBorder(5, palette.colors.get(1), 2),  palette.colors.get(3));


        startDateSpinner.setValue(new GregorianCalendar(1970, Calendar.FEBRUARY, 2).getTime());
        endDateSpinner.setValue(new GregorianCalendar().getTime());
        minAmount.setValue(0);
        maxAmount.setValue(Integer.MAX_VALUE);

        filterFlowsPanel.add(startDateLabel);
        filterFlowsPanel.add(startDateSpinner);

        filterFlowsPanel.add(endDateLabel);
        filterFlowsPanel.add(endDateSpinner);

        filterFlowsPanel.add(minAmountLabel);
        filterFlowsPanel.add(minAmount);

        filterFlowsPanel.add(maxAmountLabel);
        filterFlowsPanel.add(maxAmount);

        filterFlowsPanel.add(titleLabel);
        filterFlowsPanel.add(title);
        filterFlowsPanel.add(new JLabel());


        CustomButton filterButton = new CustomButton("Filtruj", 75, 25, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);
        filterFlowsPanel.add(filterButton);

        filterButton.addActionListener(e -> filterCashFlow());

        mainFlowsPanel.add(filterFlowsPanel);
        // ----------------------------------------------------------------------


        cashFlowsPanel = new CustomPanel(new FlowLayout(), 350, 350, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        cashFlowsPanel.setLayout(new BoxLayout(cashFlowsPanel, BoxLayout.Y_AXIS));

        cashFlowsRoll = new CustomScrollPane(cashFlowsPanel, 400, 350, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));
        mainFlowsPanel.add(cashFlowsRoll);

        CustomButton toAddFlow = new CustomButton("Dodaj", 150, 35, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);
        mainFlowsPanel.add(toAddFlow);

        CustomButton toStartPanel = new CustomButton("Wróć", 150, 35, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);
        CustomButton linkGroup = new CustomButton("Zobacz link", 150, 35, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);
        CustomButton outGroup = new CustomButton("Opuść", 150, 35, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);
        CustomButton delGroup = new CustomButton("Usuń", 150, 35, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3), Color.white);

        CustomPanel bottomButtons = new CustomPanel(new GridLayout(), 500, 50, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));

        bottomButtons.add(toStartPanel);
        bottomButtons.add(linkGroup);
        bottomButtons.add(delGroup);

        mainFlowsPanel.add(bottomButtons);

        updateCashFlows();

        // -------------------------------------------------------------------------

        int memberSqrt = (int) (Math.sqrt(group.membersNames.size())%1==0?Math.sqrt(group.membersNames.size()):Math.floor(Math.sqrt(group.membersNames.size()))+1);
        finalBalance = new CustomPanel(new GridLayout(memberSqrt, memberSqrt, 2 ,2), 200, 200, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));


        for(int i = 0; i < group.membersNames.size(); i++){
            CustomPanel mmbrDebt = getCustomPanel(i);
            finalBalance.add(mmbrDebt);
        }
        mainSettlesPanel.add(finalBalance);

        // -------------------------------------------------------------------------



        dutiesPanel = new CustomPanel(new FlowLayout(), new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));
        dutiesPanel.setLayout(new BoxLayout(dutiesPanel, BoxLayout.Y_AXIS));
        dutiesRoll = new CustomScrollPane(dutiesPanel, 400, 300, new CustomBorder(2, palette.colors.get(1), 1), palette.colors.get(3));
        mainSettlesPanel.add(dutiesRoll);

        updateDuties();

        mainPanel.add(mainFlowsPanel);
        mainPanel.add(mainSettlesPanel);


        mainScrollPane = new CustomScrollPane(mainPanel, 525, 650, 800, 700, new CustomBorder(2, palette.colors.get(1), 0), palette.colors.get(3));

        mainCashFlows.add(mainScrollPane);

        mainPanel.setTextColor(mainPanel, Color.white);
    // -------------------------------------------------------------------------------------------------------------





    // -------------------------------------------------------------------------------------------------------------

    // ------------------------------------------ addCashFlow ------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------

        addCashFlow.actionFlow.addActionListener(e->{
            addCashFlow.actionFlowEffects(group, selectedFlow);
            updateCashFlows();
            cardLyt.show(panel, "main_card");
            frame.setSize(1100, 700);
        });
        addCashFlow.toBackOfFlow.addActionListener(e->{
            cardLyt.show(panel, "main_card");
            frame.setSize(1100, 700);
        });

    // -------------------------------------------------------------------------------------------------------------

    // ------------------------------------------ editCashFlow -----------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------


        editCashFlow.actionFlow.addActionListener(e->{
            editCashFlow.actionFlowEffects(group, selectedFlow);
            updateCashFlows();
            cardLyt.show(panel, "main_card");
            frame.setSize(1100, 700);
        });
        editCashFlow.toBackOfFlow.addActionListener(e->{
            cardLyt.show(panel, "main_card");
            frame.setSize(1100, 700);
        });

        editCashFlow.delFlow.addActionListener(e ->{
            group.financeDataImporter.cashFlows.remove(selectedFlow);
            updateCashFlows();
            cardLyt.show(panel, "main_card");
            frame.setSize(1100, 700);
        });

    // -------------------------------------------------------------------------------------------------------------

    // ------------------------------------------- back to all -----------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------


        panel.add(mainCashFlows, "main_card");
        panel.add(addCashFlow, "add_flow_card");
        panel.add(editCashFlow, "edit_flow_card");

        toAddFlow.addActionListener(e -> {
            selectedFlow = -1;
            addCashFlow.changeButton(selectedFlow);
            cardLyt.show(panel, "add_flow_card");
            frame.setSize(600, 950);
        });

        toStartPanel.addActionListener(e -> {
            new DluznikPanel(user);
            frame.dispose();
        });

        linkGroup.addActionListener(e -> JOptionPane.showMessageDialog(panel, group.id+":"+group.name+":"+group.pass));

        delGroup.addActionListener(e -> {
            //importGroupList();
            String content = groupFileManager.changeGroupsInString(groups);
            System.out.println(content);
            groups.remove(group);
            content = groupFileManager.changeGroupsInString(groups);
            System.out.println(content);
            //exportGroupList();
            new DluznikPanel(user);
            frame.dispose();
        });
    }

    CustomPanel getCustomPanel(int i) {
        CustomPanel mmbrDebt = new CustomPanel(new GridBagLayout(), 75, 75, new CustomBorder(2, palette.colors.get(1), 1),  palette.colors.get(3));

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.fill = GridBagConstraints.CENTER;
        gbc1.weightx = 1;
        gbc1.weighty = 0.5;
        gbc1.insets = new Insets(2, 10, 2, 10);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.fill = GridBagConstraints.CENTER;
        gbc2.weightx = 1;
        gbc2.weighty = 0.5;
        gbc2.insets = new Insets(2, 10, 2, 10);

        JLabel memberName = new JLabel(group.membersNames.get(i));
        JLabel memberSaldo = new JLabel(""+group.saldo.get(i).get(i));


        mmbrDebt.add(memberName, gbc1);
        mmbrDebt.add(memberSaldo, gbc2);
        return mmbrDebt;
    }



    void setSettlement() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1100, 700));
        frame.setMinimumSize(new Dimension(550, 700));
        frame.setMaximumSize(new Dimension(1000, 700));
        frame.setBackground(bckgrndclr);
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    GroupSettlement(Account usr, AccountFileManager accntFlMngr, Group grp, GroupFileManager grpFlMngr){
        this.groupFileManager = grpFlMngr;
        this.accountFileManager = accntFlMngr;
        groups = groupFileManager.getGroups();
        group = grp;
        user = usr;
        setSettlement();
    }

}
