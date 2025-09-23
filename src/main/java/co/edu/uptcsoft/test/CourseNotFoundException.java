package co.edu.uptcsoft.test;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String id) {
        super("Course with ID '" + id + "' was not found.");
    }
}
