package model;

import java.util.ArrayList;
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

    public Offering(String location, String instructor, String semesterCode) {
        this.location = location;
        if (instructor == null) {
            this.instructor = "";
        } else {
            this.instructor = instructor;
        }
        this.semesterCode = Integer.valueOf(semesterCode);
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

    public void addToSectionList(Section section) {
        if (isInSectionList(section)) {
            section.setSectionId(sectionList.size());
            sectionList.add(section);
        }
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
                && this.location.equals(otherOffering.location)
                && this.instructor.equals(otherOffering.instructor);
    }
}
