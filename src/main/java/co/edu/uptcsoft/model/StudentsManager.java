package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptcsoft.test.*;

public class StudentsManager {

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student s) throws DuplicateStudentException {
        for (Student st : students) {
            if (st.getId() == s.getId()) {
                throw new DuplicateStudentException("El estudiante con id " + s.getId() + " ya existe");
            }
        }
        students.add(s);

    }

    public void updateStudent(Student s) throws StudentNotFoundException {
        Student existing = getStudentById(s.getId());
        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
    }

    public void deleteStudent(int id) throws StudentNotFoundException {
        Student s = getStudentById(id);
        students.remove(s);
    }

    public Student getStudentById(int id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s; 
            }
        }
        throw new StudentNotFoundException("Estudiante no encontrado: " + id);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
