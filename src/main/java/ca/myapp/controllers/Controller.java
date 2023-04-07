package ca.myapp.controllers;


import model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
