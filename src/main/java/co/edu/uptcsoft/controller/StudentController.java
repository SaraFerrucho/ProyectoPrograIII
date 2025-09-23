package co.edu.uptcsoft.controller;

import co.edu.uptcsoft.model.Student;
import co.edu.uptcsoft.model.StudentsManager;
import co.edu.uptcsoft.persistence.StudentDAO;
import co.edu.uptcsoft.test.*;
import java.util.List;

public class StudentController {

    private final StudentsManager studentManager;
    private final StudentDAO studentDAO;

    public StudentController() {
        this.studentManager = new StudentsManager();
        this.studentDAO = new StudentDAO();
    }

    private void validateId(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("ID del estudiante no puede ser menor o igual a 0");
    }

    private void validateName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre del estudiante no puede estar vacío");
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email del estudiante no puede estar vacío");
    }

    // CRUD estudiantes

    public void addStudent(int id, String name, String email) throws DuplicateStudentException {
        validateId(id);
        validateName(name);
        validateEmail(email);

        Student s = new Student(id, name, email);
        studentManager.addStudent(s);
        saveData();
    }

    public void updateStudent(int id, String name, String email) throws StudentNotFoundException {
        validateId(id);
        validateName(name);
        validateEmail(email);

        Student updated = new Student(id, name, email);
        studentManager.updateStudent(updated);
        saveData();
    }

    public void deleteStudent(int id) throws StudentNotFoundException {
        validateId(id);
        studentManager.deleteStudent(id);
        saveData();
    }

    public List<Student> listStudents() {
        return studentManager.getStudents();
    }

    public Student getStudentById(int id) throws StudentNotFoundException {
        validateId(id);
        return studentManager.getStudentById(id);
    }

    public void saveData() {
        studentDAO.save(studentManager.getStudents());
    }

    public void loadData() {
        var loaded = studentDAO.load();
        if (loaded != null)
            studentManager.setStudents(loaded);
    }
}
