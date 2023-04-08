package ca.myapp.controllers;


import model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private final Manager manager = new Manager();
    private final List<CourseWatcher> courseWatcherList = new ArrayList<>();

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

        Section newSection = new Section(offeringInput.getComponent(),String.valueOf(offeringInput.getEnrollmentTotal())
                ,String.valueOf(offeringInput.getEnrollmentCap()));
        Offering newOffering = new Offering(offeringInput.getLocation(),offeringInput.getInstructor(),String.valueOf(offeringInput.getSemester()));
        notifyWatchers(offeringInput.getSubjectName(),offeringInput.getCatalogNumber(), newSection, newOffering);
    }

    @GetMapping("/api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseWatcher> getCourseWatcherList() {
        return courseWatcherList;
    }
    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWatcher(@RequestBody CourseWatcherRequestBody courseWatcherRequestBody) {
        // use the getters to get the deptID and courseID NOTE these are ints (im not sure what brian is gonna insert them as so theyre ints for now)
        int deptId = courseWatcherRequestBody.getDeptId();
        int courseId = courseWatcherRequestBody.getCourseId();
        Department department = manager.getDepartmentList().get(deptId);
        Course course = manager.getDepartmentList().get(deptId).getCourseList().get(courseId);
        CourseWatcher courseWatcher = new CourseWatcher(department,course);
        courseWatcherList.add(courseWatcher);
        sortWatcherList();

    }

    @GetMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<String> getEventsList(@PathVariable int id) {
        return courseWatcherList.get(id).getEvents();
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeWatcher(@PathVariable int id) {
        courseWatcherList.remove(id);
        sortWatcherList();
    }

    private void sortWatcherList() {
        int i = 0;
        for (CourseWatcher courseWatcher : courseWatcherList) {
            courseWatcher.setWatcherId(i);
            i++;
        }
    }
    private void notifyWatchers(String departmentName, String catalogNumber , Section newSection, Offering newOffering) {
        for (CourseWatcher courseWatcher : courseWatcherList) {
            if(courseWatcher.getDepartment().getName().equals(departmentName) &&
                    courseWatcher.getCourse().getCatalogNumber().equals(catalogNumber)) {
                courseWatcher.updateEvents(newSection,newOffering);
            }
        }
    }
}
