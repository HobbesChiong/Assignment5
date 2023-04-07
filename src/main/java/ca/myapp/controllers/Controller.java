package ca.myapp.controllers;


import model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        int deptIndex = Integer.parseInt(deptId);
        return manager.getDepartmentList().get(deptIndex).getCourseList();
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public List<Offering> getOfferings(@PathVariable String deptId, @PathVariable String courseId) {
        int deptIndex = Integer.parseInt(deptId);
        int courseIndex = Integer.parseInt(courseId);
        return manager.getDepartmentList().get(deptIndex).getCourseList().get(courseIndex).getOfferingList();
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Section> getSections(@PathVariable String deptId, @PathVariable String courseId, @PathVariable String courseOfferingId) {
        int deptIndex = Integer.parseInt(deptId);
        int courseIndex = Integer.parseInt(courseId);
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
}
