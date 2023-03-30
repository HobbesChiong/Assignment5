package org.example;

import model.CreateList;
import model.CsvReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CreateList blah = new CreateList();
        blah.createDepartmentList();
        blah.printeverything();
    }
}