package org.example;

public class Person {
    static int count = 0;
    final int id;
    String name;
    int debt;
//
//    int getCount(){ return count; }
//    int getId(){ return id; }
//    String getName(){  return name; }
//    void setName(String name) { this.name = name; }
//    public int getDebt() { return debt; }
//    void addToDebt(int amount){ debt += amount; }
//    void delFromDebt(int amount){ debt -= amount; }
//    void changeName(String newName){
//        name = newName;
//    }

    Person(String name) {
        id = count++;
        this.name = name;
        debt = 0;
    }

}
