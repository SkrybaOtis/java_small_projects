package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupFileManager implements FileManager {
    private static final Logger logger = LoggerFactory.getLogger(GroupFileManager.class);

    String line;
    String splitBy = ", ";
    String[] values;

    Group group;
    List<Group> groups = new ArrayList<>();

    @Override
    public void importData(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    //System.out.println(line);
                    values = line.split(splitBy);
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].substring(1, values[i].length() - 1);
                    }
//                    System.out.println(values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4]);
                    group = new Group(values[1], values[2], values[3], values[4], values[5]);
                    groups.add(group);

                }
            }
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
        }

    }

    public List<Group> getGroups() {
        return groups;
    }

    public void exportData(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("File has been overwritten successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file: " + e.getMessage());
        }
    }


    String changeGroupsInString(List<Group> gropus) {
        StringBuilder all = new StringBuilder();
        String quoteMark = String.valueOf('"');
        String between = '"'+", "+'"';
        String values;
        StringBuilder valuesGroup = new StringBuilder();
        for (Group group : gropus) {
            values = quoteMark + group.id;
            values = values + between + group.name;
            values = values + between + group.pass;

            for (Integer v : group.adminsId) {
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            for (Integer v : group.permissedId) {
                valuesGroup.append(v).append(":");
            }
            if(!valuesGroup.isEmpty()) valuesGroup.deleteCharAt(valuesGroup.length() - 1);
            values = values + between + valuesGroup;
            valuesGroup = new StringBuilder();

            for (String v : group.membersNames) {
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