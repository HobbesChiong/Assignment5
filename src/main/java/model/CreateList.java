package model;

import java.util.ArrayList;
import java.util.List;

public class CreateList {

    List<Department> departmentList = new ArrayList<>();
    private final static int SEMESTER_INDEX = 0;
    private final static int DEPARTMENT_INDEX = 1;
    private final static int COURSE_INDEX = 2;
    private final static int LOCATION_INDEX = 3;
    private final static int ENROLLMENT_CAP_INDEX = 4;
    private final static int ENROLLMENT_TOTAL_INDEX = 5;
    private final static int INSTRUCTOR_INDEX = 6;
    private final static int COMPONENT_CODE_INDEX = 7;

    public void createDepartmentList() {
        CsvReader courseData = new CsvReader("data\\course_data_2018.csv");
        List<String[]> courseDataList = courseData.getListOfCsvRows();

        for (String[] index : courseDataList) {
            String curDeptName = index[DEPARTMENT_INDEX];
            Department newDepartment = new Department(curDeptName);
            Course curCourse = new Course(index[COURSE_INDEX]);
            Offering curOffering = new Offering(index[LOCATION_INDEX], index[INSTRUCTOR_INDEX], index[SEMESTER_INDEX]);
            Section curSection = new Section(index[COMPONENT_CODE_INDEX], index[ENROLLMENT_TOTAL_INDEX], index[ENROLLMENT_CAP_INDEX]);

            if (isInDepartmentList(newDepartment)) {
                for (Department dept : departmentList) {
                    if (dept.getName().equals(curDeptName)) {
                        dept.addToCourseList(curCourse, curOffering, curSection);
                    }
                }
            } else {
                newDepartment.setDeptId(departmentList.size());
                departmentList.add(newDepartment);
                newDepartment.addToCourseList(curCourse, curOffering, curSection);
            }
        }
    }

    // garbarge
    public void printeverything() {
        for (int i = 0; i < departmentList.size(); i++) {
            System.out.println(departmentList.get(i).toString());

            for(int j = 0; j < departmentList.get(i).getCourseList().size(); j++) {
                System.out.println("   " + departmentList.get(i).getCourseList().get(j).toString());

                for(int k = 0; k < departmentList.get(i).getCourseList().get(j).getOfferingList().size(); k++) {
                    System.out.println("      " + departmentList.get(i).getCourseList().get(j).getOfferingList().get(k).toString());
                }
            }
        }
    }

    private boolean isInDepartmentList(Department department) {
        for (Department dept : departmentList) {
            if (dept.getName().equals(department.getName())) {
                return true;
            }
        }
        return false;
    }
}
