package org.example;

import model.CsvReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            CsvReader fuck = new CsvReader("data/course_data_2018.csv");
            List<String[]> list = fuck.getListOfCsvRows();

            for (int i = 0; i < list.size(); i++) {
                for (String index : list.get(i)) {
                    System.out.print(index + ", ");
                }
                System.out.println();
            }
        } catch (RuntimeException e) {
            System.out.println("badcsf");
        }
    }
}