package model;

public class CourseWatcher {
    private int deptId;
    private int courseId;

    public CourseWatcher(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
