package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/*
    A specific offering of a class in a given semester
 */
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

    public Offering(String location, String instructor, String semesterCode) {
        this.location = location;
        if (instructor.equals("(null)") || instructor.equals("<null>")) {
            this.instructor = "";
        } else {
            this.instructor = instructor;
        }
        this.semesterCode = Integer.parseInt(semesterCode);
        this.sectionList = new ArrayList<>();
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(int courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void addInstructor(String instructor) {
        this.instructor = this.instructor + ", " + instructor;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void addToSectionList(Section section) {

        section.setSectionId(sectionList.size());
        sectionList.add(section);
    }

    private boolean isInSectionList(Section section) {
        for (Section sec : sectionList) {
            if (sec.equals(section)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Offering otherOffering) {
        return this.semesterCode == otherOffering.semesterCode
                && this.location.equals(otherOffering.location);
    }

    public void sortSectionList() {
        sectionList.sort((s1, s2) -> s1.getComponentCode().compareToIgnoreCase(s2.getComponentCode()));
        int i = 0;
        for(Section section : sectionList) {
            section.setSectionId(i);
            i++;
        }
    }
    @Override
    public String toString() {
        return "Offering{" +
                "location=" + location +
                ", instructor='" + instructor  +
                ", semesterCode=" + semesterCode +
                '}';
    }
}
