package co.edu.uptcsoft.model;

public class CourseManager {
    private TreeNode<Course> rootCourse;

    public CourseManager(Course course) {
        this.rootCourse = new TreeNode<>(course);
    }

    public TreeNode<Course> getRootCourse() {
        return rootCourse;
    }
}