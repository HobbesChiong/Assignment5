package model;

public class Section {
    private int sectionId;
    private String componentCode;
    private int enrollmentTotal;
    private int enrollmentCap;

    public Section(String componentCode, String enrollmentTotal, String enrollmentCap) {
        this.componentCode = componentCode;
        this.enrollmentTotal = Integer.parseInt(enrollmentTotal);
        this.enrollmentCap = Integer.parseInt(enrollmentCap);
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public boolean equals(Section otherSection) {
        return this.componentCode.equals(otherSection.componentCode)
                && this.enrollmentTotal == otherSection.enrollmentTotal
                && this.enrollmentCap == otherSection.enrollmentCap;
    }

    public void increaseEnrollmentCap(int val){
        enrollmentCap += val;
    }
    public void increaseEnrollmentTotal(int val) {
        enrollmentTotal += val;
    }
    @Override
    public String toString() {
        return "Section{" +
                "componentCode=" + componentCode +
                ", enrollmentTotal='" + enrollmentTotal +
                ", enrollmentCap=" + enrollmentCap +
                '}';
    }
}

