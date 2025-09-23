package co.edu.uptcsoft.test;

public class DuplicateCourseException extends Exception {
    public DuplicateCourseException(String id) {
        super("There is already a course with the ID: " + id);
    }
}
