package co.edu.uptcsoft.test;

public class ModuleNotFoundException extends Exception {
    public ModuleNotFoundException(String id) {
        super("Module with ID '" + id + "' was not found.");
    }
}
