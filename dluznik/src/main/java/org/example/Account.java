package org.example;

import java.util.ArrayList;
import java.util.List;

public class Account {
    static int count = 0;
    final int id;
    String name;
    String password;
    List<Integer> accessedGroups;
    List<Integer> groupsMemberIds;

    public void setAccessedGroups(String stringOfGroups) {
        List<Integer> groups = new ArrayList<>();
        stringOfGroups = stringOfGroups.trim();
        if(!stringOfGroups.isEmpty()){
            String splitBy = ":";
            String[] stringGroups = stringOfGroups.split(splitBy);
            for (String groupId : stringGroups) {
                groups.add(Integer.valueOf(groupId));
            }
        }
        this.accessedGroups = groups;
    }


    public void setGroupsMemberIds(String stringMembersIds) {
        List<Integer> ids = new ArrayList<>();
        stringMembersIds = stringMembersIds.trim();
        if(!stringMembersIds.isEmpty()){
            String splitBy = ":";
            String[] stringGroupsMemberIds = stringMembersIds.split(splitBy);
            for (String groupMemberId : stringGroupsMemberIds){
                ids.add(Integer.valueOf(groupMemberId));
            }
        }
        this.groupsMemberIds = ids;
    }


    Account(String name, String password, String groups, String groupsMember ){
        id = count++;
        this.name = name;
        this.password = password;
        setAccessedGroups(groups);
        setGroupsMemberIds(groupsMember);
    }

    Account(String idd, String name, String password, String groups, String groupsMember ){
        id = Integer.parseInt(idd);
        this.name = name;
        this.password = password;
        setAccessedGroups(groups);
        setGroupsMemberIds(groupsMember);
    }
}
