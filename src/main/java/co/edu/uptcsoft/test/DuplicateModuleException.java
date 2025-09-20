package co.edu.uptcsoft.test;

public class DuplicateModuleException extends Exception {
    public DuplicateModuleException(String id) {
        super("There is already a module with the ID: " + id);
    }
}