package model;

import java.util.List;

public class Offering {
    // Assume there is at most one offering of each course at each campus during a single
    //semester. Therefore aggregate all lectures, all tutorials, etc for a single course/campus
    //pair.
    private int courseOfferingId;
    // i.e SURREY
    private String location;
    private String instructor;
    private int year;
    private int semesterCode;
    private String term;
    private List<Section> sectionList;
}
