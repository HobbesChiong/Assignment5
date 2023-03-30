package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    // index in the array not sure what this value is for yet?
    private int courseId;
    private String catalogNumber;
    private List<Offering> offeringList;

    public Course (String catalogNumber) {
        this.catalogNumber = catalogNumber;
        this.offeringList = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public List<Offering> getOfferingList() {
        return offeringList;
    }

    public void setOfferingList(List<Offering> offeringList) {
        this.offeringList = offeringList;
    }

    public void addToOfferingList(Offering offering, Section section) {
        if (isInOfferingList(offering)) {
            for (Offering off : offeringList) {
                if (off.equals(offering)) {
                    off.addToSectionList(section);
                }
            }
        } else {
            offering.setCourseOfferingId(offeringList.size());
            offeringList.add(offering);
            offering.addToSectionList(section);
        }
    }

    private boolean isInOfferingList(Offering offering) {
        for (Offering off : offeringList) {
            if (off.equals(offering)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", offeringList=" + offeringList +
                '}';
    }
}
