package co.edu.uptcsoft.controller;
import java.util.List;
import java.util.Optional;

import co.edu.uptcsoft.model.Student;
import co.edu.uptcsoft.persistence.StudentDAO;
public class StudentController {

    private final StudentDAO dao = new StudentDAO();

    // Create / Update
    public void addStudent(int id, String name, String email) {
        // validaciones simples
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nombre vacío");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email vacío");
        dao.upsert(new Student(id, name, email));
        System.out.println("✔ Estudiante guardado: " + id);
    }

    public void updateStudent(Student s) {
        dao.upsert(s);
        System.out.println("✔ Estudiante actualizado: " + s.getId());
    }

    // Read
    public List<Student> listStudents() {
        return dao.findAll();
    }

    public Optional<Student> getStudent(int id) {
        return dao.findById(id);
    }

    // Delete
    public boolean deleteStudent(int id) {
        boolean ok = dao.delete(id);
        System.out.println(ok ? "✔ Estudiante eliminado: " + id : "⚠ No existe id: " + id);
        return ok;
    }
    
}
