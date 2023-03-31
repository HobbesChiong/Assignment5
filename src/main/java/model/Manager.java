package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/*
    Manages the data by storing all the departments into a list which contains further information
 */
public class Manager {

    List<Department> departmentList;

    public Manager() {
        CreateList newDepartmentList = new CreateList();
        departmentList = newDepartmentList.getDepartmentList();
        sortAllLists();
    }
    // TODO: having this here is probably fine but consider moving it
    public void dumpModel() {
        for(Department department : departmentList) {
            List<Course> courseList = department.getCourseList();
            for(Course course : courseList){
                List<Offering> offeringList = course.getOfferingList();
                String departmentName = department.getName();
                System.out.println(departmentName + " " + course.getCatalogNumber());
                for(Offering offering : offeringList) {
                    List<Section> sectionList = offering.getSectionList();
                    List<Section> aggregatedSectionList = new ArrayList<>();
                    int semesterCode = offering.getSemesterCode();
                    String location = offering.getLocation();
                    String instructor = offering.getInstructor().replaceAll("\\s+", " ");
                    System.out.println("\t" + semesterCode + " in " + location + " by " + instructor);
                    for(Section section : sectionList) {
                        String componentCode = section.getComponentCode();
                        if(aggregatedSectionList.isEmpty()) {
                            aggregatedSectionList.add(section);
                        }
                        else {
                            boolean isInside = false;
                            for(Section section1 : aggregatedSectionList) {
                                if (section1.getComponentCode().equals(componentCode)) {
                                    section1.increaseEnrollmentCap(section.getEnrollmentCap());
                                    section1.increaseEnrollmentTotal(section.getEnrollmentTotal());
                                    isInside = true;
                                    break;
                                }
                            }
                            if(!isInside) {
                                aggregatedSectionList.add(section);
                            }
                        }


                    }
                    for(Section section: aggregatedSectionList) {
                        String componentCode = section.getComponentCode();
                        int enrollmentTotal = section.getEnrollmentTotal();
                        int enrollmentCap = section.getEnrollmentCap();
                        System.out.println("\t\t Type=" + componentCode + ", Enrollment=" + enrollmentTotal + "/" + enrollmentCap);

                    }
                }
            }
        }
    }



    // TODO: Consider aggregating the sections during sorting here instead of dump model
    private void sortAllLists() {
        departmentList.sort((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()));
        int i = 0;
        for(Department department : departmentList) {
            department.setDeptId(i);
            i++;
        }
        sortCourseList();
    }

    private void sortCourseList() {
        for(Department department : departmentList) {
            sortOfferingList(department.getCourseList());
            department.sortCourseList();
        }
    }

    private void sortOfferingList(List<Course> courseList) {
        for(Course course : courseList) {
            sortSectionList(course.getOfferingList());
            course.sortOfferingList();
        }

    }

    private void sortSectionList(List<Offering> offeringList) {
        for(Offering offering : offeringList) {
            offering.sortSectionList();
        }
    }
    // only for bug testing
    public void printeverything() {
        // department
        for (int i = 0; i < departmentList.size(); i++) {
            System.out.println(departmentList.get(i).toString());

            // courses
            for(int j = 0; j < departmentList.get(i).getCourseList().size(); j++) {
                System.out.println("   " + departmentList.get(i).getCourseList().get(j).toString());
                // offerings
                for(int k = 0; k < departmentList.get(i).getCourseList().get(j).getOfferingList().size(); k++) {
                    System.out.println("      " + departmentList.get(i).getCourseList().get(j).getOfferingList().get(k).toString());
                    // sections
                    for(int l = 0; l < departmentList.get(i).getCourseList().get(j).getOfferingList().get(k).getSectionList().size(); l++) {
                        System.out.println("         " + departmentList.get(i).getCourseList().get(j).getOfferingList().get(k).getSectionList().get(l).toString());
                    }
                }

            }
        }
    }


}
