package co.edu.uptcsoft.test;

public class DuplicateLessonException extends Exception{
    public DuplicateLessonException(String id) {
        super("There is already a lesson in this module with ID " + id);
    }
}
