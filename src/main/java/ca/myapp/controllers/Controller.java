package ca.myapp.controllers;


import model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private final Manager manager = new Manager();

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public String getAbout(){
        return ("Course Planner is a web application where users can plan their SFU courses by looking at previous " +
                "course offerings and enrollments from previous years" +
                "By: Gahee Kim & Hobbes Chiong");
    }

    @GetMapping("/api/dump-model")
    @ResponseStatus(HttpStatus.OK)
    public void dumpModel() {
        manager.dumpModel();
    }

    @GetMapping("/api/departments")
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getDepartments() {
        return manager.getDepartmentList();
    }

    @GetMapping("/api/departments/{deptId}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCourses(@PathVariable String deptId) {
        isValidDeptOrThrow404(Integer.parseInt(deptId));
        int deptIndex = Integer.parseInt(deptId);

        return manager.getDepartmentList().get(deptIndex).getCourseList();
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public List<Offering> getOfferings(@PathVariable String deptId, @PathVariable String courseId) {
        isValidDeptOrThrow404(Integer.parseInt(deptId));
        int deptIndex = Integer.parseInt(deptId);

        isValidCourseOrThrow404(Integer.parseInt(courseId), deptIndex);
        int courseIndex = Integer.parseInt(courseId);

        return manager.getDepartmentList().get(deptIndex).getCourseList().get(courseIndex).getOfferingList();
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Section> getSections(@PathVariable String deptId, @PathVariable String courseId, @PathVariable String courseOfferingId) {
        isValidDeptOrThrow404(Integer.parseInt(deptId));
        int deptIndex = Integer.parseInt(deptId);

        isValidCourseOrThrow404(Integer.parseInt(courseId), deptIndex);
        int courseIndex = Integer.parseInt(courseId);

        isValidCourseOfferingOrThrow404(Integer.parseInt(courseOfferingId), courseIndex, deptIndex);
        int courseOfferingIndex = Integer.parseInt(courseOfferingId);

        return manager.getDepartmentList().get(deptIndex).getCourseList().get(courseIndex).getOfferingList()
                .get(courseOfferingIndex).getAggregatedSectionList();
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody OfferingInput offeringInput) {
        manager.addOffering(String.valueOf(offeringInput.getSemester()),
                offeringInput.getSubjectName(),
                offeringInput.getCatalogNumber(),
                offeringInput.getLocation(),
                String.valueOf(offeringInput.getEnrollmentCap()),
                offeringInput.getComponent(),
                String.valueOf(offeringInput.getEnrollmentTotal()),
                offeringInput.getInstructor());
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWatcher(@RequestBody CourseWatcher courseWatcher) {
        // use the getters to get the deptID and courseID NOTE these are ints (im not sure what brian is gonna insert them as so theyre ints for now)
    }

    private boolean isValidDeptOrThrow404(int id) {
        for (Department dept : manager.getDepartmentList()) {
            if (id == dept.getDeptId()) {
                return true;
            }
        }
        throw new FileNotFoundException("Requested Department ID does not exist");
    }

    private boolean isValidCourseOrThrow404(int courseId, int deptId) {
        Department curDept = manager.getDepartment(deptId);

        for (Course course : curDept.getCourseList()) {
            if (courseId == course.getCourseId()) {
                return true;
            }
        }
        throw new FileNotFoundException("Requested Course ID does not exist");
    }

    private boolean isValidCourseOfferingOrThrow404(int offeringId, int courseId, int deptId) {
        Department curDept = manager.getDepartment(deptId);
        Course curCourse = curDept.getCourse(courseId);

        for (Offering offering : curCourse.getOfferingList()) {
            if (offeringId == offering.getCourseOfferingId()) {
                return true;
            }
        }
        throw new FileNotFoundException("Requested Course Offering ID does not exist");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class FileNotFoundException extends RuntimeException {
        private FileNotFoundException() {
            super();
        }
        private FileNotFoundException(String msg) {
            super(msg);
        }
    }
}
