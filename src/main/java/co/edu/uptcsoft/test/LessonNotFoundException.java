package co.edu.uptcsoft.test;

public class LessonNotFoundException extends Exception {
    public LessonNotFoundException(String id) {
        super("Lesson with ID '" + id + "' was not found.");
    }
}
